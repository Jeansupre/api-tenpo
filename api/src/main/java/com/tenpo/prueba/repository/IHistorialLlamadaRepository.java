package com.tenpo.prueba.repository;

import com.tenpo.prueba.model.HistorialLlamada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistorialLlamadaRepository extends JpaRepository<HistorialLlamada, Long> {
}