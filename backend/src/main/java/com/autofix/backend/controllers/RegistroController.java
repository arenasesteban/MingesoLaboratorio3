package com.autofix.backend.controllers;

import com.autofix.backend.entities.Registro;
import com.autofix.backend.services.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro")
@CrossOrigin("*")
public class RegistroController {
    @Autowired
    RegistroService registroService;

    @PostMapping("/")
    public ResponseEntity<Registro> crearRegistro(@RequestBody Registro registro) {
        return ResponseEntity.ok(registroService.crearRegistro(registro));
    }

    @GetMapping("/")
    public ResponseEntity<List<Registro>> obtenerRegistros() {
        return ResponseEntity.ok(registroService.obtenerRegistros());
    }

    @GetMapping("/{id_registro}")
    public ResponseEntity<Registro> obtenerRegistro(@PathVariable Long id_registro) {
        return ResponseEntity.ok(registroService.obtenerRegistro(id_registro));
    }

    @PutMapping("/calcular-total")
    public ResponseEntity<Registro> calcularTotal(@RequestBody Registro registro, @RequestParam Integer descuento_bono) {
        return ResponseEntity.ok(registroService.calcularTotal(registro, descuento_bono));
    }

    @PutMapping("/")
    public ResponseEntity<Registro> actualizarRegistro(@RequestBody Registro registro) {
        return ResponseEntity.ok(registroService.actualizarRegistro(registro));
    }
}