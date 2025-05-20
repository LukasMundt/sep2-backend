package de.haw.se2.security.common.validations;

import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameNotInUseValidator implements ConstraintValidator<UsernameNotInUse, String> {

    private final UserRepository userRepository;

    @Autowired
    public UsernameNotInUseValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.isEmpty()) {
            return true;
        }

        return !userRepository.existsByUsername(username);
    }
}
