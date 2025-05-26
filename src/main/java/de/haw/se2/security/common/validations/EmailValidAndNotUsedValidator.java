package de.haw.se2.security.common.validations;

import de.haw.se2.security.common.validations.exceptions.EmailAlreadyInUseException;
import de.haw.se2.security.common.validations.exceptions.EmailInvalidException;
import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidAndNotUsedValidator implements ConstraintValidator<EmailValidAndNotUsed, String> {

    private final UserRepository userRepository;

    @Autowired
    public EmailValidAndNotUsedValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new EmailInvalidException("Email is not valid");
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException(email);
        }

        return true;
    }
}
