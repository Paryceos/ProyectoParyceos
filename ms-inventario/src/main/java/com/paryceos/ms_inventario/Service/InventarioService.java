package com.paryceos.msinventario.service;

import com.paryceos.msinventario.model.Inventario;
import com.paryceos.msinventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> listarTodos() { 
        return inventarioRepository.findAll(); 
    }

    public Inventario guardar(Inventario articulo) {
        if (articulo.getStock() == null || articulo.getStock() < 0) {
            throw new RuntimeException("Error: El stock disponible no puede ser menor a 0.");
        }
        return inventarioRepository.save(articulo);
    }
}