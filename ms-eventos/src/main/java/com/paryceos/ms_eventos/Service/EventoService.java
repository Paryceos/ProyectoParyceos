package com.paryceos.mseventos.service;

import com.paryceos.mseventos.model.Evento;
import com.paryceos.mseventos.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Evento> obtenerEventos() { 
        return eventoRepository.findAll(); 
    }

    public Evento crearEvento(Evento evento) {
        // Interconexión síncrona mediante RestTemplate
        try {
            restTemplate.getForObject("http://localhost:8081/api/clientes/" + evento.getIdCliente(), Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: El cliente con ID " + evento.getIdCliente() + " no está registrado.");
        }

        try {
            restTemplate.getForObject("http://localhost:8082/api/trabajadores/" + evento.getIdTrabajador(), Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error de validación: El trabajador con ID " + evento.getIdTrabajador() + " no está registrado.");
        }

        if (evento.getTipoFiesta() == null || evento.getTipoFiesta().isEmpty()) {
            throw new RuntimeException("Error: El tipo de fiesta es requerido.");
        }

        return eventoRepository.save(evento);
    }
}