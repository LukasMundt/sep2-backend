package de.haw.se2.security.common.validations.exceptions;

public class UsernameAlreadyInUseException extends AlreadyInUseException {
    public UsernameAlreadyInUseException(String value) {
        super("Username '" + value + "'");
    }
}
