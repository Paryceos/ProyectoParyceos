package com.paryceos.mspagos.service;

import com.paryceos.mspagos.model.Pago;
import com.paryceos.mspagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listarTodos() { 
        return pagoRepository.findAll(); 
    }

    public Pago registrarPago(Pago pago) {
        if (pago.getMontoTotal() == null || pago.getMontoTotal() <= 0) {
            throw new RuntimeException("Error: El monto de facturación debe ser mayor a 0.");
        }
        pago.setEstado("Pendiente"); 
        return pagoRepository.save(pago);
    }
}