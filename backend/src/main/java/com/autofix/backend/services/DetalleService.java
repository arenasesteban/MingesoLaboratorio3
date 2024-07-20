package com.autofix.backend.services;

import com.autofix.backend.entities.Detalle;
import com.autofix.backend.repositories.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleService {
    @Autowired
    DetalleRepository detalleRepository;

    public List<Detalle> crearDetalles(List<Detalle> detalles) {
        return detalleRepository.saveAll(detalles);
    }
}