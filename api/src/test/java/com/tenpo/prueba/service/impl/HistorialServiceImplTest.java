package com.tenpo.prueba.service.impl;

import com.tenpo.prueba.dto.HistorialLlamadaDTO;
import com.tenpo.prueba.exceptions.TechnicalException;
import com.tenpo.prueba.exceptions.TransactionException;
import com.tenpo.prueba.mappers.IHistorialLlamadaMapper;
import com.tenpo.prueba.model.HistorialLlamada;
import com.tenpo.prueba.repository.IHistorialLlamadaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HistorialServiceImplTest {

    @InjectMocks
    private HistorialServiceImpl historialService;
    @Mock
    private IHistorialLlamadaRepository historialRepository;
    @Spy
    private IHistorialLlamadaMapper iHistorialLlamadaMapper = Mappers.getMapper(IHistorialLlamadaMapper.class);

    @Test
    void obtenerHistorialLlamadas() {
        //Given
        HistorialLlamada historialLlamada = HistorialLlamada.builder()
                .id(1L)
                .fecha(new Date())
                .endpoint("/tenpo/api/v1/operaciones/suma")
                .parametros(Map.of("num1", 10, "num2", 20))
                .respuesta("{\"resultado\": 33.0}")
                .error(null)
                .statusCode(200L)
                .build();

        given(historialRepository.findAll()).willReturn(List.of(historialLlamada));

        //When
        List<HistorialLlamadaDTO> historialLlamadas = historialService.obtenerHistorialLlamadas();

        //Then
        assertThat(historialLlamadas).isNotNull().isNotEmpty().hasSize(1);
        assertThat(historialLlamadas.getFirst().getStatusCode()).isEqualTo(200L);
    }

    @Test
    void guardarLlamadaExito() {
        //Given
        Date fecha = new Date();
        String endpoint = "/tenpo/api/v1/operaciones/suma";
        Map<String, Object> parametros = Map.of("num1", 10, "num2", 20);
        String respuesta = "{\"resultado\": 33.0}";
        Long statusCode = 200L;

        given(historialRepository.save(any(HistorialLlamada.class))).willReturn(new HistorialLlamada());

        //When
        historialService.guardarLlamadaExito(fecha, endpoint, parametros, respuesta, statusCode);

        //Then
        verify(historialRepository).save(any(HistorialLlamada.class));
        verify(historialRepository, times(1)).save(any(HistorialLlamada.class));
    }

    @Test
    void guardarLlamadaError() {
        //Given
        Date fecha = new Date();
        String endpoint = "/tenpo/api/v1/operaciones/suma";
        Map<String, Object> parametros = Map.of("num1", 10, "num2", 20);
        String error = "El servicio externo no devolvió un valor válido.";
        Long statusCode = 200L;

        given(historialRepository.save(any(HistorialLlamada.class))).willReturn(new HistorialLlamada());

        //When
        historialService.guardarLlamadaError(fecha, endpoint, parametros, error, statusCode);

        //Then
        verify(historialRepository).save(any(HistorialLlamada.class));
        verify(historialRepository, times(1)).save(any(HistorialLlamada.class));
    }

    @Test
    void guardarLlamadaExitoArrojaExcepcionAlGuardar() {
        //Given
        Date fecha = new Date();
        String endpoint = "/tenpo/api/v1/operaciones/suma";
        Map<String, Object> parametros = Map.of("num1", 10, "num2", 20);
        String respuesta = "{\"resultado\": 33.0}";
        Long statusCode = 200L;

        given(historialRepository.save(any(HistorialLlamada.class))).willThrow(new RuntimeException());

        //When
        TransactionException exception = assertThrows(TransactionException.class,
                () -> historialService.guardarLlamadaExito(fecha, endpoint, parametros, respuesta, statusCode));

        //Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Error al guardar la llamada en el historial.");
    }

    @Test
    void guardarLlamadaErrorArrojaExcepcionAlGuardar() {
        //Given
        Date fecha = new Date();
        String endpoint = "/tenpo/api/v1/operaciones/suma";
        Map<String, Object> parametros = Map.of("num1", 10, "num2", 20);
        String error = "El servicio externo no devolvió un valor válido.";
        Long statusCode = 200L;

        given(historialRepository.save(any(HistorialLlamada.class))).willThrow(new RuntimeException());

        //When
        TransactionException exception = assertThrows(TransactionException.class,
                () -> historialService.guardarLlamadaError(fecha, endpoint, parametros, error, statusCode));

        //Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Error al guardar la llamada en el historial.");
    }
}