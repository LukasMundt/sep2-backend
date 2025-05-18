package de.haw.se2.security.common.validations;

import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailNotRegisteredValidator implements ConstraintValidator<EmailNotRegistered, String> {

    private final UserRepository userRepository;

    @Autowired
    public EmailNotRegisteredValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return !userRepository.existsByEmail(value);
    }
}
