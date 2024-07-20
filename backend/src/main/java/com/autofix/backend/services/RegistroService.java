package com.autofix.backend.services;

import com.autofix.backend.entities.Registro;
import com.autofix.backend.entities.Vehiculo;
import com.autofix.backend.repositories.DetalleRepository;
import com.autofix.backend.repositories.RegistroRepository;
import com.autofix.backend.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

@Service
public class RegistroService {
    @Autowired
    RegistroRepository registroRepository;

    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    DetalleRepository detalleRepository;

    public Registro crearRegistro(Registro registro) {
        return registroRepository.save(registro);
    }

    public List<Registro> obtenerRegistros() {
        return registroRepository.findAll();
    }

    public Registro obtenerRegistro(Long id_registro) {
        return registroRepository.encontrarPorId_registro(id_registro);
    }

    public Registro calcularTotal(Registro registro, int descuento_bono) {
        Vehiculo vehiculo = vehiculoRepository.encontrarPorPatente(registro.getPatente());

        double recargo_kilometraje = registro.getMonto_reparaciones() * recargoPorKilometraje(vehiculo.getKilometraje(), vehiculo.getTipo());
        double recargo_antiguedad = registro.getMonto_reparaciones() * recargoPorAntiguedad(vehiculo.getAno_fabricacion(), vehiculo.getTipo());
        double recargo_retraso = registro.getMonto_reparaciones() * recargoPorRetrasoRecogida(registro.getFecha_salida(), registro.getFecha_retiro());

        double descuento_cantidad_reparaciones = registro.getMonto_reparaciones() * descuentoPorNumeroReparaciones(vehiculo.getPatente(), vehiculo.getMotor(), registro.getId_registro());
        double descuento_dia_atencion = registro.getMonto_reparaciones() * descuentoPorDiaAtencion(registro.getFecha_ingreso(), registro.getHora_ingreso());

        registro.setMonto_recargos((int) (recargo_kilometraje + recargo_antiguedad + recargo_retraso));
        registro.setMonto_descuentos((int) (descuento_cantidad_reparaciones + descuento_dia_atencion + descuento_bono));
        registro.setMonto_iva((int) ((registro.getMonto_reparaciones() + registro.getMonto_recargos() - registro.getMonto_descuentos()) * .19));

        registro.setCosto_total(registro.getMonto_reparaciones() + registro.getMonto_recargos() - registro.getMonto_descuentos() + registro.getMonto_iva());

        return registroRepository.save(registro);
    }

    public double recargoPorKilometraje(Integer kilometraje, String tipo) {
        double recargo = .0;

        if(kilometraje >= 5001 && kilometraje <= 12000) {
            recargo = switch (tipo) {
                case "Sedan", "Hatchback" -> .03;
                case "SUV", "Pickup", "Furgoneta" -> .05;
                default -> recargo;
            };
        }
        else if(kilometraje >= 12001 && kilometraje <= 25000) {
            recargo = switch (tipo) {
                case "Sedan", "Hatchback" -> .07;
                case "SUV", "Pickup", "Furgoneta" -> .09;
                default -> recargo;
            };
        }
        else if(kilometraje >= 25001 && kilometraje <= 40000) {
            recargo = switch (tipo) {
                case "Sedan", "Hatchback", "SUV", "Pickup", "Furgoneta" -> .12;
                default -> recargo;
            };
        }
        else if(kilometraje > 40000) {
            recargo = switch (tipo) {
                case "Sedan", "Hatchback", "SUV", "Pickup", "Furgoneta" -> .2;
                default -> recargo;
            };
        }

        return recargo;
    }

    public double recargoPorAntiguedad(int ano_fabricacion, String tipo) {
        int ano_antiguedad = 2024 - ano_fabricacion;
        double recargo = .0;

        if(ano_antiguedad >= 6 && ano_antiguedad <= 10) {
            recargo = switch (tipo) {
                case "Sedan", "Hatchback" -> .05;
                case "SUV", "Pickup", "Furgoneta" -> .07;
                default -> recargo;
            };
        }
        else if(ano_antiguedad >= 11 && ano_antiguedad <= 15) {
            recargo = switch (tipo) {
                case "Sedan", "Hatchback" -> .09;
                case "SUV", "Pickup", "Furgoneta" -> .11;
                default -> recargo;
            };
        }
        else if(ano_antiguedad >= 16) {
            recargo = switch (tipo) {
                case "Sedan", "Hatchback" -> .15;
                case "SUV", "Pickup", "Furgoneta" -> .20;
                default -> recargo;
            };
        }

        return recargo;
    }

    public double recargoPorRetrasoRecogida(LocalDate fecha_salida, LocalDate fecha_retiro) {
        int retraso = Period.between(fecha_salida, fecha_retiro).getDays();

        return retraso * .05;
    }

    public double descuentoPorNumeroReparaciones(String patente, String motor, Long id_registro) {
        int numero_reparaciones = contarReparaciones(patente, id_registro);
        double descuento = .0;

        if(numero_reparaciones >= 1 && numero_reparaciones <= 2) {
            descuento = switch (motor) {
                case "Gasolina" -> .05;
                case "Diesel" -> .07;
                case "Híbrido" -> .1;
                case "Eléctrico" -> .08;
                default -> descuento;
            };
        }
        else if(numero_reparaciones >= 3 && numero_reparaciones <= 5) {
            descuento = switch (motor) {
                case "Gasolina" -> .1;
                case "Diesel" -> .12;
                case "Híbrido" -> .15;
                case "Eléctrico" -> .13;
                default -> descuento;
            };
        }
        else if(numero_reparaciones >= 6 && numero_reparaciones <= 9) {
            descuento = switch (motor) {
                case "Gasolina" -> .15;
                case "Diesel" -> .17;
                case "Híbrido" -> .2;
                case "Eléctrico" -> .18;
                default -> descuento;
            };
        }
        else if(numero_reparaciones >= 10) {
            descuento = switch (motor) {
                case "Gasolina" -> .2;
                case "Diesel" -> .22;
                case "Híbrido" -> .25;
                case "Eléctrico" -> .23;
                default -> descuento;
            };
        }

        return descuento;
    }

    public int contarReparaciones(String patente, Long id_registro) {
        List<Long> id_registros = registroRepository.buscarPorPatenteYMenorAId_registro(patente, id_registro);
        return detalleRepository.contarDetallePorId_registro(id_registros);
    }

    public double descuentoPorDiaAtencion(LocalDate fecha_ingreso, LocalTime hora_ingreso) {
        double descuento = .0;

        if (fecha_ingreso.getDayOfWeek() == DayOfWeek.MONDAY || fecha_ingreso.getDayOfWeek() == DayOfWeek.THURSDAY) {
            if (!hora_ingreso.isBefore(LocalTime.of(9, 0)) && !hora_ingreso.isAfter(LocalTime.of(12, 0))) {
                descuento = .1;
            }
        }

        return descuento;
    }

    public Registro actualizarRegistro(Registro registro) {
        return registroRepository.save(registro);
    }
}