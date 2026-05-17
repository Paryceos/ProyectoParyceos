package com.paryceos.mstrabajadores.controller;

import com.paryceos.mstrabajadores.model.Trabajador;
import com.paryceos.mstrabajadores.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {
    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping
    public ResponseEntity<List<Trabajador>> listar() {
        return new ResponseEntity<>(trabajadorService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(trabajadorService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Trabajador t) {
        try {
            return new ResponseEntity<>(trabajadorService.guardar(t), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}