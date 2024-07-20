package com.autofix.backend.controllers;

import com.autofix.backend.entities.Detalle;
import com.autofix.backend.services.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detalle")
public class DetalleController {
    @Autowired
    DetalleService detalleService;

    @PostMapping("/")
    public ResponseEntity<List<Detalle>> crearDetalles(@RequestBody List<Detalle> detalles) {
        return ResponseEntity.ok(detalleService.crearDetalles(detalles));
    }
}