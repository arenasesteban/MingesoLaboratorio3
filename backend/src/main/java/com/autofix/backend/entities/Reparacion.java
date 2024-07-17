package com.autofix.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reparacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_reparacion;

    private String tipo_reparacion;
    private Integer precio_gasolina;
    private Integer precio_diesel;
    private Integer precio_hibrido;
    private Integer precio_electrico;
}