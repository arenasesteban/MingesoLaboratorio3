package com.autofix.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenReparacion {
    private String tipo_reparacion;

    private Integer cantidad_sedan;
    private Integer monto_sedan;

    private Integer cantidad_hatchback;
    private Integer monto_hatchback;

    private Integer cantidad_suv;
    private Integer monto_suv;

    private Integer cantidad_pickup;
    private Integer monto_pickup;

    private Integer cantidad_furgoneta;
    private Integer monto_furgoneta;

    private Integer cantidad_total;
    private Integer monto_total;
}