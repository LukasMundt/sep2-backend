package de.haw.se2.security.common.validations.exceptions;

public class AlreadyInUseException extends RuntimeException {
    public AlreadyInUseException(String emailOrUsername) {
        super(emailOrUsername + " is already in use");
    }
}
