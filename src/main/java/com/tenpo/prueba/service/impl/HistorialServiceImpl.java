package com.tenpo.prueba.service.impl;

import com.tenpo.prueba.dto.HistorialLlamadaDTO;
import com.tenpo.prueba.exceptions.TransactionException;
import com.tenpo.prueba.mappers.IHistorialLlamadaMapper;
import com.tenpo.prueba.model.HistorialLlamada;
import com.tenpo.prueba.repository.IHistorialLlamadaRepository;
import com.tenpo.prueba.service.interfaces.IHistorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HistorialServiceImpl implements IHistorialService {

    private final IHistorialLlamadaRepository historialRepository;
    private final IHistorialLlamadaMapper iHistorialLlamadaMapper;

    /**
     * Obtiene el historial de llamadas
     *
     * @return {@link List} de {@link HistorialLlamadaDTO} lista con el historial de llamadas de los endpoints
     */
    @Override
    public List<HistorialLlamadaDTO> obtenerHistorialLlamadas() {
        List<HistorialLlamada> historial = historialRepository.findAll();
        return historial.stream()
                .map(iHistorialLlamadaMapper::toHistorialLlamadaDTO)
                .toList();
    }

    /**
     * Guarda una llamada a un endpoint
     *
     * @param fecha      {@link Date} fecha de la llamada
     * @param endpoint   {@link String} endpoint al que se llamó
     * @param parametros {@code Map<String, Object>} parametros de la llamada
     * @param respuesta  {@code Map<String, Object>} respuesta de la llamada
     * @param statusCode {@link String} código de estado de la llamada
     */
    @Async
    @Override
    public void guardarLlamadaExito(Date fecha, String endpoint, Map<String, Object> parametros,
                               String respuesta, Long statusCode) {
        try {
            HistorialLlamada historialLlamada =
                    crearObjetoHistorialLlamadaExito(fecha, endpoint, parametros, respuesta, statusCode);
            historialRepository.save(historialLlamada);
        } catch (Exception e) {
            throw new TransactionException("Error al guardar la llamada en el historial.", e);
        }
    }

    /**
     * Guarda una llamada a un endpoint
     *
     * @param fecha      {@link Date} fecha de la llamada
     * @param endpoint   {@link String} endpoint al que se llamó
     * @param parametros {@code Map<String, Object>} parametros de la llamada
     * @param error {@link String} error de la llamada
     * @param statusCode {@link String} código de estado de la llamada
     */
    @Async
    @Override
    public void guardarLlamadaError(Date fecha, String endpoint, Map<String, Object> parametros,
                                    String error, Long statusCode) {
        try {
            HistorialLlamada historialLlamada =
                    crearObjetoHistorialLlamadaException(fecha, endpoint, parametros, error, statusCode);
            historialRepository.save(historialLlamada);
        } catch (Exception e) {
            throw new TransactionException("Error al guardar la llamada en el historial.", e);
        }
    }

    private HistorialLlamada crearObjetoHistorialLlamadaExito(Date fecha, String endpoint,
                                                              Map<String, Object> parametros,
                                                              String respuesta, Long statusCode) {
        return HistorialLlamada.builder()
                .fecha(fecha)
                .endpoint(endpoint)
                .parametros(parametros)
                .respuesta(respuesta)
                .statusCode(statusCode)
                .build();
    }

    private HistorialLlamada crearObjetoHistorialLlamadaException(Date fecha, String endpoint,
                                                                  Map<String, Object> parametros,
                                                                  String error, Long statusCode) {
        return HistorialLlamada.builder()
                .fecha(fecha)
                .endpoint(endpoint)
                .parametros(parametros)
                .error(error)
                .statusCode(statusCode)
                .build();
    }
}
