package com.paryceos.mseventos.controller;

import com.paryceos.mseventos.model.Evento;
import com.paryceos.mseventos.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<Evento>> listar() {
        return new ResponseEntity<>(eventoService.obtenerEventos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody Evento evento) {
        try {
            return new ResponseEntity<>(eventoService.crearEvento(evento), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}