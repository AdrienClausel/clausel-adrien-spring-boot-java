package com.safetynet.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestion globale des exceptions
     * @param e exception
     * @return ResponseEntity<Object> L'objet de la réponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception e){
        log.error("Erreur interne du serveur",e);
        return buildResponse(e);
    }

    /**
     * Construction d'une réponse en cas d'exception
     * @param exception exception
     * @return ResponseEntity<Object> L'objet de la réponse
     */
    private ResponseEntity<Object> buildResponse(Exception exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", exception.getCause().getMessage());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}