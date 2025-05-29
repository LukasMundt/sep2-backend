package de.haw.se2.security.common.validations.exceptions;

public class EmailAlreadyInUseException extends AlreadyInUseException {
    public EmailAlreadyInUseException(String value) {
        super("Email address '" + value + "'");
    }
}
