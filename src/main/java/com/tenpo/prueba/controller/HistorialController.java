package com.tenpo.prueba.controller;

import com.tenpo.prueba.dto.HistorialLlamadaDTO;
import com.tenpo.prueba.service.interfaces.IHistorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final IHistorialService historialService;

    @Operation(
            summary = "obtenerTodos",
            description = "Endpoint para obtener el historial de todos los endpoints llamados",
            tags = {"Historial"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de endpoints llamados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HistorialLlamadaDTO.class)
                            )),
                    @ApiResponse(responseCode = "204", description = "No hay contenido")
            }
    )
    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<HistorialLlamadaDTO>> obtenerHistorial() {
        var historial = historialService.obtenerHistorialLlamadas();
        if (historial.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }
}
