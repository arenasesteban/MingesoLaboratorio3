package com.autofix.backend.services;

import com.autofix.backend.entities.Reparacion;
import com.autofix.backend.repositories.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListResourceBundle;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;

    public List<Reparacion> crearReparaciones(List<Reparacion> reparaciones) {
        return reparacionRepository.saveAll(reparaciones);
    }

    public List<Reparacion> obtenerReparaciones() {
        return reparacionRepository.findAll();
    }

    public List<String> obtenerTipoReparaciones() {
        return reparacionRepository.obtenerTipoReparaciones();
    }
}
