package com.autofix.backend.repositories;

import com.autofix.backend.dtos.CantidadMonto;
import com.autofix.backend.entities.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle, Long> {
    @Query("SELECT COUNT(d) FROM Detalle d WHERE d.id_registro IN :id_registros")
    Integer contarDetallePorId_registro(List<Long> id_registros);

    @Query("SELECT DISTINCT d.patente FROM Detalle d WHERE d.tipo_reparacion = :tipo_reparacion AND MONTH(d.fecha_reparacion) = :mes AND YEAR(d.fecha_reparacion) = :ano")
    List<String> encontrarPatentesPorTipoReparacion(String tipo_reparacion, Integer mes, Integer ano);

    @Query("SELECT SUM(d.monto_reparacion) FROM Detalle d WHERE d.patente IN :patentes AND d.tipo_reparacion = :tipo_reparacion AND MONTH(d.fecha_reparacion) = :mes AND YEAR(d.fecha_reparacion) = :ano")
    Integer sumarPorPatenteYTipoReparacion(List<String> patentes, String tipo_reparacion, Integer mes, Integer ano);

    @Query("SELECT COUNT(d) AS cantidad, SUM(d.monto_reparacion) AS monto FROM Detalle d WHERE d.tipo_reparacion = :tipo_reparacion AND MONTH(d.fecha_reparacion) = :mes")
    CantidadMonto contarDetallePorTipoReparacionYMes(String tipo_reparacion, Integer mes);

}