package com.autofix.backend.services;

import com.autofix.backend.entities.Vehiculo;
import com.autofix.backend.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;

    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public List<Vehiculo> obtenerVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo actualizarVehiculo(String patente, Integer kilometraje) {
        Vehiculo vehiculo = vehiculoRepository.encontrarPorPatente(patente);
        vehiculo.setKilometraje(kilometraje);

        return vehiculoRepository.save(vehiculo);
    }
}
