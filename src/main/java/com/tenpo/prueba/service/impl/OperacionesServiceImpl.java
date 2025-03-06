package com.tenpo.prueba.service.impl;

import com.tenpo.prueba.client.IApiExterna;
import com.tenpo.prueba.dto.ResultadoDTO;
import com.tenpo.prueba.exceptions.TechnicalException;
import com.tenpo.prueba.service.interfaces.IOperacionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import static com.tenpo.prueba.config.CacheConfig.CACHE_NAME;

@Service
@RequiredArgsConstructor
public class OperacionesServiceImpl implements IOperacionesService {

    private final IApiExterna apiExterna;
    private final CacheManager cacheManager;
    private final CacheService cacheService;

    /**
     * Obtiene la suma de dos números y le agrega un porcentaje
     * @param num1 {@code Long} primer número
     * @param num2 {@code Long} segundo número
     * @return {@code Float} suma de los números con un porcentaje extra
     */
    @Override
    public ResultadoDTO obtenerSumaConPorcentajeExtra(Long num1, Long num2) {
        Long suma = num1 + num2;
        Float porcentaje = obtenerPorcentajeExtra(suma);
        return new ResultadoDTO(suma + porcentaje);
    }

    private Float obtenerPorcentajeExtra(Long num) {
        Long resultadoExterno = obtenerPorcentajeExterno();
        Float porcentaje = resultadoExterno / 100f;
        return num * porcentaje;
    }

    public Long obtenerPorcentajeExterno() {
        try {
            Long resultadoExterno = apiExterna.obtenerPorcentaje().getBody();
            if (resultadoExterno == null) {
                throw new TechnicalException("El servicio externo no devolvió un valor válido.");
            }
            return cacheService.actualizarCache(resultadoExterno);
        } catch (Exception e) {
            return recuperarDesdeCache();
        }
    }

    private Long recuperarDesdeCache() {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache == null) {
            throw new TechnicalException("No hay caché almacenado.");
        }
        Long valorEnCache = cache.get("porcentaje", Long.class);
        if (valorEnCache == null) {
            throw new TechnicalException("No se encontró el porcentaje en caché.");
        }
        return valorEnCache;
    }
}
