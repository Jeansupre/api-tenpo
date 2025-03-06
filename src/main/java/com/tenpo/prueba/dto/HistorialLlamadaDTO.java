package com.tenpo.prueba.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO for {@link com.tenpo.prueba.model.HistorialLlamada}
 */
@Data
public class HistorialLlamadaDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5225816881434467964L;

    private Timestamp fecha;
    private String endpoint;
    private Map<String, Object> parametros;
    private String respuesta;
    private String error;
    private Long statusCode;
}