package com.paryceos.mspagos.controller;

import com.paryceos.mspagos.model.Pago;
import com.paryceos.mspagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return new ResponseEntity<>(pagoService.listarTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Pago p) {
        try {
            return new ResponseEntity<>(pagoService.registrarPago(p), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}