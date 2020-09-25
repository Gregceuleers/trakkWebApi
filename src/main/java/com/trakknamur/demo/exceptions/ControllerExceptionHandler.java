package com.trakknamur.demo.exceptions;

/*
    Approche de gestion globale des exceptions
 */

import com.trakknamur.demo.exceptions.models.ErrorDTO;
import com.trakknamur.demo.exceptions.models.RuleGolfException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDTO> handle(Throwable e) {

        if (e.getMessage() != null) {

            if (e instanceof IllegalArgumentException) {
                log.debug("Erreur gérée : " +  e.getClass() + " : " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDTO(e.getMessage()));
            }
            if (e instanceof IllegalAccessException) {
                log.debug("Erreur gérée : " +  e.getClass() + " : " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDTO(e.getMessage()));
            }
            if (e instanceof RuleGolfException) {
                log.debug("Erreur gérée : " +  e.getClass() + " : " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new ErrorDTO(e.getMessage()));
            }
            if (e instanceof NoSuchElementException) {
                log.debug("Erreur gérée : " +  e.getClass() + " : " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDTO(e.getMessage()));
            }

            if (e instanceof UsernameNotFoundException) {
                log.debug("Erreur gérée : " +  e.getClass() + " : " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDTO(e.getMessage()));
            }
            if (e instanceof NotYetImplementedException) {
                log.debug("Erreur gérée : " +  e.getClass() + " : " + e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.NOT_IMPLEMENTED)
                        .body(new ErrorDTO(e.getMessage()));
            }
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO("Une erreur inconnue s'est produite. Les développeurs ont été prévenus."));
    }

}
