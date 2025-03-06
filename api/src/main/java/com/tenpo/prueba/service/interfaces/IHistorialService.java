package com.tenpo.prueba.service.interfaces;

import com.tenpo.prueba.dto.HistorialLlamadaDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IHistorialService {

    /**
     * Obtiene el historial de llamadas
     * @return {@link List} de {@link HistorialLlamadaDTO} lista con el historial de llamadas de los endpoints
     */
    List<HistorialLlamadaDTO> obtenerHistorialLlamadas();

    /**
     * Guarda una llamada a un endpoint
     * @param fecha {@link Date} fecha de la llamada
     * @param endpoint {@link String} endpoint al que se llamó
     * @param parametros {@code Map<String, Object>} parametros de la llamada
     * @param respuesta {@link String} respuesta de la llamada
     * @param statusCode {@link Long} código de estado de la llamada
     */
    void guardarLlamadaExito(Date fecha, String endpoint, Map<String, Object> parametros,
                        String respuesta, Long statusCode);

    /**
     * Guarda una llamada a un endpoint
     * @param fecha {@link Date} fecha de la llamada
     * @param endpoint {@link String} endpoint al que se llamó
     * @param parametros {@code Map<String, Object>} parametros de la llamada
     * @param error {@link String} error de la llamada
     * @param statusCode {@link Long} código de estado de la llamada
     */
    void guardarLlamadaError(Date fecha, String endpoint, Map<String, Object> parametros,
                                 String error, Long statusCode);

}
