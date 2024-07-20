package com.autofix.backend.controllers;

import com.autofix.backend.dtos.ComparativoReparacion;
import com.autofix.backend.dtos.ResumenReparacion;
import com.autofix.backend.services.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reporte")
@CrossOrigin("*")
public class ReportesController {
    @Autowired
    ReportesService reportesService;

    @GetMapping("/resumen-reparaciones")
    public ResponseEntity<List<ResumenReparacion>> reporteResumenReparaciones(@RequestParam Integer mes, @RequestParam Integer ano) {
        List<ResumenReparacion> reporte = reportesService.reporteResumenReparaciones(mes, ano);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/comparativo-reparaciones")
    public ResponseEntity<List<ComparativoReparacion>> reporteComparativoReparaciones(@RequestParam Integer mes) {
        List<ComparativoReparacion> reporte = reportesService.reporteComparativoReparaciones(mes);
        return ResponseEntity.ok(reporte);
    }
}