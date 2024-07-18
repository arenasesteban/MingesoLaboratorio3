package com.autofix.backend.controllers;

import com.autofix.backend.entities.Reparacion;
import com.autofix.backend.services.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reparacion")
@CrossOrigin("*")
public class ReparacionController {
    @Autowired
    ReparacionService reparacionService;

    @PostMapping("/")
    public ResponseEntity<List<Reparacion>> crearReparaciones(@RequestBody List<Reparacion> reparaciones) {
        return ResponseEntity.ok(reparacionService.crearReparaciones(reparaciones));
    }

    @GetMapping("/")
    public ResponseEntity<List<Reparacion>> obtenerReparaciones() {
        return ResponseEntity.ok(reparacionService.obtenerReparaciones());
    }
}