package com.autofix.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_registro;

    private LocalDate fecha_ingreso;
    private LocalTime hora_ingreso;

    private Integer monto_reparaciones;
    private Integer monto_recargos;
    private Integer monto_descuentos;
    private Integer monto_iva;
    private Integer costo_total;

    private LocalDate fecha_salida;
    private LocalTime hora_salida;
    private LocalDate fecha_retiro;
    private LocalTime hora_retiro;

    private String patente;
}