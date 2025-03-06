package com.tenpo.prueba.security.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.prueba.dto.ErrorDTO;
import com.tenpo.prueba.service.interfaces.IHistorialService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistroInterceptor implements HandlerInterceptor {

    private final IHistorialService historialService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().contains("/tenpo/api")) {
            return true;
        }
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable Exception exception) throws JsonProcessingException {
        if (!request.getRequestURI().contains("/tenpo/api")) {
            return;
        }
        Long startTime = (Long) request.getAttribute("startTime");
        Date fechaPeticion = new Date(startTime);
        String url = String.join(" ", request.getMethod(), request.getRequestURI());
        Map<String, Object> parametros = obtenerParametros(request);
        Long status = (long) response.getStatus();
        String respuesta;

        if (response instanceof ContentCachingResponseWrapper wrappedResponse) {
            respuesta = obtenerRespuesta(wrappedResponse);

            try {
                wrappedResponse.copyBodyToResponse();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            JsonNode errorNode = obtenerErrorEnRespuesta(respuesta);
            String errorNodeAsText = errorNode != null ? errorNode.asText() : "null";
            if (!errorNodeAsText.contains("null")) {
                try {
                    historialService.guardarLlamadaError(fechaPeticion, url, parametros, errorNodeAsText, status);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            } else {
                historialService.guardarLlamadaExito(fechaPeticion, url, parametros, respuesta, status);
            }
        }
    }

    @Nullable
    private String obtenerRespuesta(ContentCachingResponseWrapper response) {
        try {
            byte[] contenido = response.getContentAsByteArray();
            return contenido.length > 0 ? new String(contenido, response.getCharacterEncoding()) : null;
        } catch (UnsupportedEncodingException e) {
            return "Error al leer la respuesta";
        }
    }

    private Map<String, Object> obtenerParametros(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
    }

    @Nullable
    private JsonNode obtenerErrorEnRespuesta(String respuesta) throws JsonProcessingException {
        if (respuesta == null) {
            return null;
        }
        String respuestaIndividual =
                respuesta.replace("[", "").replace("]", "");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(respuestaIndividual);
        return rootNode.get("message");
    }
}
