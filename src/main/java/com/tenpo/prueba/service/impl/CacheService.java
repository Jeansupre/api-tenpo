package com.tenpo.prueba.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import static com.tenpo.prueba.config.CacheConfig.CACHE_NAME;

@Service
public class CacheService {

    static final String PORCENTAJE_KEY = "'porcentaje'";

    @CachePut(value = CACHE_NAME, key = PORCENTAJE_KEY)
    public Long actualizarCache(Long nuevoPorcentaje) {
        return nuevoPorcentaje;
    }
}