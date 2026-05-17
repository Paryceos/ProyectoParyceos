package com.paryceos.mstrabajadores.service;

import com.paryceos.mstrabajadores.model.Trabajador;
import com.paryceos.mstrabajadores.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    // Método para listar
    public List<Trabajador> listarTrabajadores() {
        return trabajadorRepository.findAll();
    }

    // Método para guardar con lógica de negocio
    public Trabajador guardarTrabajador(Trabajador trabajador) {
        
        // Regla de negocio al estilo clásico:
        if (trabajador.getTarifaPorEvento() != null && trabajador.getTarifaPorEvento() <= 0) {
            throw new RuntimeException("Error: La tarifa por evento debe ser mayor a 0.");
        }
        
        if (trabajador.getNombre() == null || trabajador.getNombre().isEmpty()) {
            throw new RuntimeException("Error: El nombre del trabajador es obligatorio.");
        }

        return trabajadorRepository.save(trabajador);
    }
}