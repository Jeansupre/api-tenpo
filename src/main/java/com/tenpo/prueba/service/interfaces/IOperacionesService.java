package com.tenpo.prueba.service.interfaces;

import com.tenpo.prueba.dto.ResultadoDTO;

public interface IOperacionesService {

    /**
     * Obtiene la suma de dos números y le agrega un porcentaje
     * @param num1 {@code Long} primer número
     * @param num2 {@code Long} segundo número
     * @return {@code Float} suma de los números con un porcentaje extra
     */
    ResultadoDTO obtenerSumaConPorcentajeExtra(Long num1, Long num2);
}
