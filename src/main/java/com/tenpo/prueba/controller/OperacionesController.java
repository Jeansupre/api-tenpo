package com.tenpo.prueba.controller;

import com.tenpo.prueba.dto.ResultadoDTO;
import com.tenpo.prueba.service.interfaces.IOperacionesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/operaciones")
@RequiredArgsConstructor
public class OperacionesController {

    private final IOperacionesService operacionesService;

    @GetMapping("/suma")
    public ResponseEntity<ResultadoDTO> obtenerSuma(@RequestParam Long num1, @RequestParam Long num2) {
        ResultadoDTO resultado = operacionesService.obtenerSumaConPorcentajeExtra(num1, num2);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
