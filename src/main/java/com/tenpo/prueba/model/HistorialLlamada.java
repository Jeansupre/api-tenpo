package com.tenpo.prueba.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "historial_llamadas")
public class HistorialLlamada {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial_llamadas_id_gen")
    @SequenceGenerator(name = "historial_llamadas_id_gen", sequenceName = "historial_llamadas_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ColumnDefault("now()")
    @Column(name = "fecha", nullable = false)
    private Timestamp fecha;

    @Column(name = "endpoint", nullable = false, length = 250)
    private String endpoint;

    @Column(name = "parametros")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> parametros;

    @Column(name = "respuesta")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> respuesta;

    @Column(name = "error", length = 250)
    private String error;

    @Column(name = "status_code", nullable = false)
    private Long statusCode;
}