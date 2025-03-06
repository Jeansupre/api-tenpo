package com.tenpo.prueba.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historial_llamadas")
@Schema(name = "HistorialLlamada", description = "Entidad que representa el historial de llamadas a los endpoints")
public class HistorialLlamada implements Serializable {

    @Serial
    private static final long serialVersionUID = -2882841117241721394L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial_llamadas_id_gen")
    @SequenceGenerator(name = "historial_llamadas_id_gen", sequenceName = "historial_llamadas_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ColumnDefault("now()")
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "endpoint", nullable = false, length = 250)
    private String endpoint;

    @Column(name = "parametros")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> parametros;

    @Column(name = "respuesta")
    @JdbcTypeCode(SqlTypes.JSON)
    private String respuesta;

    @Column(name = "error", length = 250)
    private String error;

    @Column(name = "status_code", nullable = false)
    private Long statusCode;
}