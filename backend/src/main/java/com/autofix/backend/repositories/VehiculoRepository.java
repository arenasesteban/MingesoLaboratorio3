package com.autofix.backend.repositories;

import com.autofix.backend.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    @Query("SELECT v FROM Vehiculo v WHERE v.patente = :patente")
    Vehiculo encontrarPorPatente(String patente);

    @Query("SELECT COUNT(v.patente) FROM Vehiculo v WHERE v.patente IN :patentes AND v.tipo = :tipo")
    Integer contarPorPatenteYTipoVehiculo(List<String> patentes, String tipo);

    @Query("SELECT v.patente FROM Vehiculo v WHERE v.patente IN :patentes AND v.tipo = :tipo")
    List<String> encontrarPatentesPorPatenteYTipoVehiculo(List<String> patentes, String tipo);
}