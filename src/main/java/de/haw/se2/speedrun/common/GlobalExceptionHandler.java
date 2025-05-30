package de.haw.se2.speedrun.common;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.NotAcceptableStatusException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public void handleConstraintViolationException() {
        //Empty for spring to use as an exception handler
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleBadCredentialsException() {
        //Empty for spring to use as an exception handler
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    public void handleNotFoundException() {
        //Empty for spring to use as an exception handler
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(value = EntityExistsException.class)
    public void handleEntityExistsException() {
       // Empty for spring to use as an exception handler
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = NotAcceptableStatusException.class)
    public void handleNotAcceptableStatusException() {
        //Empty for spring to use as an exception handler
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public void handleRuntimeException() {
        //Empty for spring to use as an exception handler
    }
}
