package com.autofix.backend.services;

import com.autofix.backend.dtos.CantidadMonto;
import com.autofix.backend.dtos.ComparativoReparacion;
import com.autofix.backend.dtos.ResumenReparacion;
import com.autofix.backend.repositories.DetalleRepository;
import com.autofix.backend.repositories.ReparacionRepository;
import com.autofix.backend.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportesService {
    @Autowired
    ReparacionRepository reparacionRepository;

    @Autowired
    DetalleRepository detalleRepository;

    @Autowired
    VehiculoRepository vehiculoRepository;

    public List<ResumenReparacion> reporteResumenReparaciones(Integer mes, Integer ano) {
        List<String> tipo_reparaciones = reparacionRepository.obtenerTipoReparaciones();
        List<ResumenReparacion> resumenReparaciones = new ArrayList<>();

        for(String tipo_reparacion : tipo_reparaciones) {
            ResumenReparacion resumenReparacion = new ResumenReparacion();

            resumenReparacion.setTipo_reparacion(tipo_reparacion);

            List<String> patentes = detalleRepository.encontrarPatentesPorTipoReparacion(tipo_reparacion, mes, ano);

            resumenReparacion.setCantidad_sedan(vehiculoRepository.contarPorPatenteYTipoVehiculo(patentes, "Sedan"));
            List<String> patenes_sedan = vehiculoRepository.encontrarPatentesPorPatenteYTipoVehiculo(patentes, "Sedan");
            resumenReparacion.setMonto_sedan(obtenerMontoPorPatenteYTipoReparacion(patenes_sedan, tipo_reparacion, mes, ano));

            resumenReparacion.setCantidad_hatchback(vehiculoRepository.contarPorPatenteYTipoVehiculo(patentes, "Hatchback"));
            List<String> patentes_hatchback = vehiculoRepository.encontrarPatentesPorPatenteYTipoVehiculo(patentes, "Hatchback");
            resumenReparacion.setMonto_hatchback(obtenerMontoPorPatenteYTipoReparacion(patentes_hatchback, tipo_reparacion, mes, ano));

            resumenReparacion.setCantidad_suv(vehiculoRepository.contarPorPatenteYTipoVehiculo(patentes, "SUV"));
            List<String> patentes_suv = vehiculoRepository.encontrarPatentesPorPatenteYTipoVehiculo(patentes, "SUV");
            resumenReparacion.setMonto_suv(obtenerMontoPorPatenteYTipoReparacion(patentes_suv, tipo_reparacion, mes, ano));

            resumenReparacion.setCantidad_pickup(vehiculoRepository.contarPorPatenteYTipoVehiculo(patentes, "Pickup"));
            List<String> patentes_pickup = vehiculoRepository.encontrarPatentesPorPatenteYTipoVehiculo(patentes, "Pickup");
            resumenReparacion.setMonto_pickup(obtenerMontoPorPatenteYTipoReparacion(patentes_pickup, tipo_reparacion, mes, ano));

            resumenReparacion.setCantidad_furgoneta(vehiculoRepository.contarPorPatenteYTipoVehiculo(patentes, "Furgoneta"));
            List<String> patentes_furgoneta = vehiculoRepository.encontrarPatentesPorPatenteYTipoVehiculo(patentes, "Furgoneta");
            resumenReparacion.setMonto_furgoneta(obtenerMontoPorPatenteYTipoReparacion(patentes_furgoneta, tipo_reparacion, mes, ano));

            resumenReparacion.setCantidad_total(resumenReparacion.getCantidad_sedan() +
                    resumenReparacion.getCantidad_hatchback() +
                    resumenReparacion.getCantidad_suv() +
                    resumenReparacion.getCantidad_pickup() +
                    resumenReparacion.getCantidad_furgoneta());

            resumenReparacion.setMonto_total(resumenReparacion.getMonto_sedan() +
                    resumenReparacion.getMonto_hatchback() +
                    resumenReparacion.getMonto_suv() +
                    resumenReparacion.getMonto_pickup() +
                    resumenReparacion.getMonto_furgoneta());

            resumenReparaciones.add(resumenReparacion);
        }

        return resumenReparaciones;
    }

    public Integer obtenerMontoPorPatenteYTipoReparacion(List<String> patentes, String tipo_reparacion, Integer mes, Integer ano) {
        Integer monto = detalleRepository.sumarPorPatenteYTipoReparacion(patentes, tipo_reparacion, mes, ano);

        if(monto == null) {
            monto = 0;
        }

        return monto;
    }

    public List<ComparativoReparacion> reporteComparativoReparaciones(Integer mes) {
        List<String> tipo_reparaciones = reparacionRepository.obtenerTipoReparaciones();
        List<ComparativoReparacion> comparativoReparaciones = new ArrayList<>();

        for(String tipo_reparacion : tipo_reparaciones) {
            ComparativoReparacion comparativoReparacion = new ComparativoReparacion();

            comparativoReparacion.setTipo_reparacion(tipo_reparacion);

            int primer_mes = mes;
            CantidadMonto cantidad_monto_1 = calcularCantidadYMontoReparacionPorMes(tipo_reparacion, primer_mes);
            comparativoReparacion.setPrimer_mes(comparativoReparacion.obtenerMes(primer_mes));
            comparativoReparacion.setCantidad_primer_mes(cantidad_monto_1.getCantidad());
            comparativoReparacion.setMonto_primer_mes(cantidad_monto_1.getMonto());

            int segundo_mes = comparativoReparacion.calcularMesAnterior(mes, 1);
            CantidadMonto cantidad_monto_2 = calcularCantidadYMontoReparacionPorMes(tipo_reparacion, segundo_mes);
            comparativoReparacion.setSegundo_mes(comparativoReparacion.obtenerMes(segundo_mes));
            comparativoReparacion.setCantidad_segundo_mes(cantidad_monto_2.getCantidad());
            comparativoReparacion.setMonto_segundo_mes(cantidad_monto_2.getMonto());

            int tercer_mes = comparativoReparacion.calcularMesAnterior(mes, 2);
            CantidadMonto cantidad_monto_3 = calcularCantidadYMontoReparacionPorMes(tipo_reparacion, tercer_mes);
            comparativoReparacion.setTercer_mes(comparativoReparacion.obtenerMes(tercer_mes));
            comparativoReparacion.setCantidad_tercer_mes(cantidad_monto_3.getCantidad());
            comparativoReparacion.setMonto_tercer_mes(cantidad_monto_3.getMonto());

            if(comparativoReparacion.getCantidad_segundo_mes() != 0) {
                comparativoReparacion.setPrimera_variacion_cantidad((double) (((comparativoReparacion.getCantidad_primer_mes()
                        - comparativoReparacion.getCantidad_segundo_mes())
                        / comparativoReparacion.getCantidad_segundo_mes()) * 100));
            }

            if(comparativoReparacion.getMonto_segundo_mes() != 0) {
                comparativoReparacion.setPrimera_variacion_monto((double) (((comparativoReparacion.getMonto_primer_mes()
                        - comparativoReparacion.getMonto_segundo_mes())
                        / comparativoReparacion.getMonto_segundo_mes()) * 100));
            }

            if(comparativoReparacion.getCantidad_tercer_mes() != 0) {
                comparativoReparacion.setSegunda_variacion_cantidad((double) (((comparativoReparacion.getCantidad_segundo_mes()
                        - comparativoReparacion.getCantidad_tercer_mes())
                        / comparativoReparacion.getCantidad_tercer_mes()) * 100));
            }

            if(comparativoReparacion.getMonto_tercer_mes() != 0) {
                comparativoReparacion.setSegunda_variacion_monto((double) (((comparativoReparacion.getMonto_segundo_mes()
                        - comparativoReparacion.getMonto_tercer_mes())
                        / comparativoReparacion.getMonto_tercer_mes()) * 100));
            }

            comparativoReparaciones.add(comparativoReparacion);
        }

        return comparativoReparaciones;
    }

    public CantidadMonto calcularCantidadYMontoReparacionPorMes(String tipo_reparacion, Integer mes) {
        CantidadMonto cantidad_monto = detalleRepository.contarDetallePorTipoReparacionYMes(tipo_reparacion, mes);

        if (cantidad_monto.getCantidad() == 0) {
            return new CantidadMonto() {
                @Override
                public Integer getCantidad() {
                    return 0;
                }

                @Override
                public Integer getMonto() {
                    return 0;
                }
            };
        }

        return cantidad_monto;
    }

}