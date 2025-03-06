package com.tenpo.prueba.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CacheServiceTest {

    @InjectMocks
    private CacheService cacheService;

    @Test
    void actualizarCache() {
        //Given
        Long nuevoPorcentaje = 10L;

        //When
        Long result = cacheService.actualizarCache(nuevoPorcentaje);

        //Then
        assertEquals(nuevoPorcentaje, result);
    }
}