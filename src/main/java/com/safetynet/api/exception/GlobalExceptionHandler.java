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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception e){
        log.error("Erreur interne du serveur",e);
        //return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur interne du serveur");
        return buildResponse(e);
    }

    private ResponseEntity<Object> buildResponse(Exception exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        //body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        body.put("error", exception.getCause().getMessage()); //status.getReasonPhrase());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
