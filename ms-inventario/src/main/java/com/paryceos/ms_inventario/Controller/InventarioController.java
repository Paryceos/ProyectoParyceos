package com.paryceos.msinventario.controller;

import com.paryceos.msinventario.model.Inventario;
import com.paryceos.msinventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> listar() {
        return new ResponseEntity<>(inventarioService.listarTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Inventario articulo) {
        try {
            return new ResponseEntity<>(inventarioService.guardar(articulo), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}