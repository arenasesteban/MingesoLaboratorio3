package com.autofix.backend.repositories;

import com.autofix.backend.entities.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReparacionRepository extends JpaRepository<Reparacion, Long> {
    @Query("SELECT r.tipo_reparacion FROM Reparacion r")
    List<String> obtenerTipoReparaciones();
}
