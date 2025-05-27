package de.haw.se2.security.common.validations.exceptions;

import jakarta.validation.ValidationException;

public class EmailInvalidException extends ValidationException {
    public EmailInvalidException(String message) {
        super(message);
    }
}
