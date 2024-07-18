package com.autofix.backend.repositories;

import com.autofix.backend.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    @Query("SELECT v FROM Vehiculo v WHERE v.patente = :patente")
    Vehiculo encontrarPorPatente(String patente);
}
