package com.paryceos.msclientes.service;

import com.paryceos.msclientes.model.Cliente;
import com.paryceos.msclientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() { 
        return clienteRepository.findAll(); 
    }
    
    public Cliente guardar(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new RuntimeException("Error: El nombre del cliente es obligatorio.");
        }
        return clienteRepository.save(cliente);
    }
    
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: Cliente no encontrado."));
    }
}
