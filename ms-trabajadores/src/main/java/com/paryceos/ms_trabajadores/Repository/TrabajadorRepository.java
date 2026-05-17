package com.paryceos.mstrabajadores.repository;

import com.paryceos.mstrabajadores.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
}