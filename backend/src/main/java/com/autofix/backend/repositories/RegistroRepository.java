package com.autofix.backend.repositories;

import com.autofix.backend.entities.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
    @Query("SELECT r FROM Registro r WHERE r.id_registro = :id_registro")
    Registro encontrarPorId_registro(Long id_registro);

    @Query("SELECT r.id_registro FROM Registro r WHERE r.patente = :patente AND r.id_registro < :id_registro")
    List<Long> buscarPorPatenteYMenorAId_registro(String patente, Long id_registro);
}