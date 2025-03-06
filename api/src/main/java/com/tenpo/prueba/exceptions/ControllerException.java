package com.tenpo.prueba.exceptions;

import com.tenpo.prueba.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class ControllerException extends Exception implements Serializable {

    @Serial
    private static final long serialVersionUID = 7277217223388140712L;

    private static final String TIPO_ERROR = "error";

    /**
     * Manejo de excepciones en errores en las peticiones.
     * @param e {@code Exception} exception
     * @param handlerMethod {@code HandlerMethod} handlerMethod
     * @return {@code ResponseEntity<ErrorDTO>} errorDTO
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorDTO> errorEnParametros(Exception e, HandlerMethod handlerMethod) {
        final HttpStatus codigoHttp = HttpStatus.BAD_REQUEST;

        saveExceptionLog(e, handlerMethod, false);

        return new ResponseEntity<>(
                new ErrorDTO(codigoHttp.value(), e.getMessage(), TIPO_ERROR, null, false),
                codigoHttp
        );
    }

    /**
     * Manejo de excepciones de errores técnicos.
     * @param e {@code Exception} exception
     * @param handlerMethod {@code HandlerMethod} handlerMethod
     * @return {@code ResponseEntity<ErrorDTO>} errorDTO
     */
    @ExceptionHandler({TechnicalException.class})
    public ResponseEntity<ErrorDTO> errorTecnicoAtrapado(Exception e, HandlerMethod handlerMethod) {
        final HttpStatus codigoHttp = HttpStatus.INTERNAL_SERVER_ERROR;

        saveExceptionLog(e, handlerMethod, false);

        return new ResponseEntity<>(
                new ErrorDTO(codigoHttp.value(), e.getMessage(), TIPO_ERROR, null, false),
                codigoHttp
        );
    }

    /**
     * Atrapa excepciones de transacción no exitosa por errores en la base de datos
     */
    @ExceptionHandler({SQLException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDTO> errorEnDBTransaccionNoExitosa(HttpServletRequest request, Exception e) {

        saveExceptionLog(e, null, true);

        final HttpStatus codigoHttp = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                new ErrorDTO(codigoHttp.value(), e.getMessage(), TIPO_ERROR,
                        request.getRequestURI(), false),
                codigoHttp
        );
    }

    /**
     * * Guarda log de la excepción.
     *
     * @param e: exception y su información de clase de servcio,
     * @param handlerMethod: información que viene desde la capa controladora
     * @param aExceptionContainer: si viene del contenedor o no controlada es true y
     *                             si manual del usuario es false
     */
    private void saveExceptionLog(Exception e, HandlerMethod handlerMethod, boolean aExceptionContainer) {
        String claseConPaquete;
        String metodo;
        String linea;
        String error;

        // capa de servicio o clase que lo lanza
        if (e != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().contains("com.tenpo.prueba")) {
                    claseConPaquete = element.getClassName();
                    metodo = element.getMethodName();
                    linea = "" + element.getLineNumber();
                    error = aExceptionContainer ? e.toString() : e.getMessage();

                    writeExceptionLogFile(claseConPaquete, metodo, linea, error);
                }
            }
        }

        // desde el origen padre (capa controladora)
        if (e == null && handlerMethod != null) {
            claseConPaquete = handlerMethod.getMethod().getDeclaringClass().getName();
            metodo = handlerMethod.getMethod().getName();

            writeExceptionLogFile(claseConPaquete, metodo);
        }
    }

    /**
     * Guarda log de la excepción.
     */
    private void writeExceptionLogFile(String aClaseConPaquete, String aMetodo, String aLinea, String aError) {
        log.error("[CLASE: {}] [METODO: {}] [LINEA: {}] [ERROR: {}]", aClaseConPaquete, aMetodo, aLinea, aError);
    }

    private void writeExceptionLogFile(String aClaseConPaquete, String aMetodo) {
        log.error("[CLASE: {}] [METODO: {}]", aClaseConPaquete, aMetodo);
    }
}
