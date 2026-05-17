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

    public List<Trabajador> listarTodos() { 
        return trabajadorRepository.findAll(); 
    }

    public Trabajador guardar(Trabajador t) {
        if (t.getTarifaPorEvento() != null && t.getTarifaPorEvento() <= 0) {
            throw new RuntimeException("Error: La tarifa por evento debe ser mayor a 0.");
        }
        return trabajadorRepository.save(t);
    }

    public Trabajador buscarPorId(Long id) {
        return trabajadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: Trabajador no encontrado."));
    }
}