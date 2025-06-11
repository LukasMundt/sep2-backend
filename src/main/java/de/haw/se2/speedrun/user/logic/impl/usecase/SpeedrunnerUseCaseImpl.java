package de.haw.se2.speedrun.user.logic.impl.usecase;

import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import de.haw.se2.speedrun.user.logic.api.usecase.SpeedrunnerUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpeedrunnerUseCaseImpl implements SpeedrunnerUseCase {

    private final SpeedrunnerRepository speedrunnerRepository;

    @Override
    public Speedrunner getSpeedrunnerByEmail(String email) {
        Optional<Speedrunner> speedrunner = speedrunnerRepository.findByEmail(email);
        if (speedrunner.isEmpty()) {
            throw new EntityNotFoundException(String.format("Speedrunner '%s' not found", email));
        }

        return speedrunner.get();
    }
}
