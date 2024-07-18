package com.autofix.backend.controllers;

import com.autofix.backend.entities.Vehiculo;
import com.autofix.backend.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculo")
@CrossOrigin("*")
public class VehiculoController {
    @Autowired
    VehiculoService vehiculoService;

    @PostMapping("/")
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculoService.crearVehiculo(vehiculo));
    }

    @GetMapping("/")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculos() {
        return ResponseEntity.ok(vehiculoService.obtenerVehiculos());
    }

    @GetMapping("/patente")
    public ResponseEntity<Vehiculo> obtenerVehiculo(@RequestParam String patente) {
        return ResponseEntity.ok(vehiculoService.obtenerVehiculo(patente));
    }

    @PutMapping("/")
    public ResponseEntity<Vehiculo> actualizarVehiculo(@RequestParam String patente, @RequestParam Integer kilometraje) {
        return ResponseEntity.ok(vehiculoService.actualizarVehiculo(patente, kilometraje));
    }
}