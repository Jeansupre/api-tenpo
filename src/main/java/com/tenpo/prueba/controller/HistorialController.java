package com.tenpo.prueba.controller;

import com.tenpo.prueba.dto.HistorialLlamadaDTO;
import com.tenpo.prueba.service.interfaces.IHistorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final IHistorialService historialService;

    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<HistorialLlamadaDTO>> obtenerHistorial() {
        var historial = historialService.obtenerHistorialLlamadas();
        if (historial.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }
}
