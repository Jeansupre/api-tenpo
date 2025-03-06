package com.tenpo.prueba.service.impl;

import com.tenpo.prueba.client.IApiExterna;
import com.tenpo.prueba.dto.ResultadoDTO;
import com.tenpo.prueba.exceptions.TechnicalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OperacionesServiceImplTest {

    @InjectMocks
    private OperacionesServiceImpl operacionesService;
    @Mock
    private IApiExterna apiExterna;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private CacheService cacheService;
    @Mock
    private Cache cache;


    @Test
    void obtenerSumaConPorcentajeExtra() {
        //Given
        Long num1 = 10L;
        Long num2 = 20L;
        Long porcentajeExterno = 10L;

        given(apiExterna.obtenerPorcentaje()).willReturn(ResponseEntity.ok(porcentajeExterno));
        given(cacheService.actualizarCache(anyLong())).willReturn(porcentajeExterno);

        //When
        ResultadoDTO resultado = operacionesService.obtenerSumaConPorcentajeExtra(num1, num2);

        //Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getResultado()).isEqualTo(33.0f);
    }

    @Test
    void obtenerSumaConServicioExternoCaido() {
        //Given
        Long num1 = 10L;
        Long num2 = 20L;
        Long porcentajeCache = 10L;

        given(apiExterna.obtenerPorcentaje()).willReturn(ResponseEntity.ok(null));
        given(cacheManager.getCache(anyString())).willReturn(cache);
        given(cache.get(anyString(), eq(Long.class))).willReturn(porcentajeCache);

        //When
        ResultadoDTO resultado = operacionesService.obtenerSumaConPorcentajeExtra(num1, num2);

        //Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getResultado()).isEqualTo(33.0f);
    }

    @Test
    void obtenerSumaArrojaExcepcionCuandoNoHayPorcentajeEnCache() {
        //Given
        Long num1 = 10L;
        Long num2 = 20L;

        given(apiExterna.obtenerPorcentaje()).willReturn(ResponseEntity.ok(null));
        given(cacheManager.getCache(anyString())).willReturn(cache);
        given(cache.get(anyString(), eq(Long.class))).willReturn(null);

        //When
        TechnicalException exception = assertThrows(TechnicalException.class,
                () -> operacionesService.obtenerSumaConPorcentajeExtra(num1, num2));

        //Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("No se encontró el porcentaje en caché.");
    }

    @Test
    void obtenerSumaArrojaExcepcionCuandoNoHayCache() {
        //Given
        Long num1 = 10L;
        Long num2 = 20L;

        given(apiExterna.obtenerPorcentaje()).willReturn(ResponseEntity.ok(null));
        given(cacheManager.getCache(anyString())).willReturn(null);

        //When
        TechnicalException exception = assertThrows(TechnicalException.class,
                () -> operacionesService.obtenerSumaConPorcentajeExtra(num1, num2));

        //Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("No hay caché almacenado.");
    }
}