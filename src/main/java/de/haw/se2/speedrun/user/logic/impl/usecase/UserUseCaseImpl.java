package de.haw.se2.speedrun.user.logic.impl.usecase;

import de.haw.se2.speedrun.user.dataaccess.api.entity.User;
import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import de.haw.se2.speedrun.user.logic.api.usecase.UserUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(String.format("User '%s' not found", email));
        }

        return user.get();
    }
}
