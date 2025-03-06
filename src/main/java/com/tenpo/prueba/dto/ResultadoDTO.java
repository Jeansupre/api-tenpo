package com.tenpo.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ResultadoDTO", description = "DTO para las operaciones")
public class ResultadoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6135451528677679483L;

    @Schema(name = "resultado", description = "Resultado de la operación", example = "5.1")
    private Float resultado;
}
