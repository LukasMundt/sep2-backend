package de.haw.se2.speedrun.user.logic.impl.usecase;

import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SpeedrunnerUseCaseImplTest {
    private Speedrunner speedrunner1;
    private Speedrunner speedrunner2;
    private Speedrunner speedrunner3;

    @Mock
    private SpeedrunnerRepository speedrunnerRepository;

    @InjectMocks
    private SpeedrunnerUseCaseImpl speedrunnerUseCase;

    @BeforeEach
    void setUp() {
        speedrunner1 = new Speedrunner();
        speedrunner1.setUsername("speedrunner1");
        speedrunner1.setId(new UUID(123, 456));
        speedrunner1.setEmail("test1@test.com");
        speedrunner1.setRight(Right.SPEEDRUNNER);
        speedrunner2 = new Speedrunner();
        speedrunner2.setUsername("speedrunner2");
        speedrunner2.setId(new UUID(124, 457));
        speedrunner2.setEmail("test2@test.com");
        speedrunner2.setRight(Right.SPEEDRUNNER);
        speedrunner3 = new Speedrunner();
        speedrunner3.setUsername("speedrunner3");
        speedrunner3.setId(new UUID(125, 458));
        speedrunner3.setEmail("test3@test.com");
        speedrunner3.setRight(Right.SPEEDRUNNER);
        Mockito.when(speedrunnerRepository.findByEmail(any(String.class)))
                .thenAnswer(invocation -> {
                    String email = invocation.getArgument(0);
                    if (email.equals(speedrunner1.getEmail())) {
                        return Optional.of(speedrunner1);
                    } else if (email.equals(speedrunner2.getEmail())) {
                        return Optional.of(speedrunner2);
                    } else if (email.equals(speedrunner3.getEmail())) {
                        return Optional.of(speedrunner3);
                    }
                    return Optional.empty();
                });
    }

    @Test
    void getSpeedrunnerByEmail() {
        assertEquals(speedrunner1, speedrunnerUseCase.getSpeedrunnerByEmail(speedrunner1.getEmail()));
        assertEquals(speedrunner2, speedrunnerUseCase.getSpeedrunnerByEmail(speedrunner2.getEmail()));
        assertEquals(speedrunner3, speedrunnerUseCase.getSpeedrunnerByEmail(speedrunner3.getEmail()));
    }

    @Test
    void getSpeedrunnerByEmailSpeedrunnerNotFound() {
        String notFoundEmail = "notFoundEmail";
        assertThrows(EntityNotFoundException.class, () -> speedrunnerUseCase.getSpeedrunnerByEmail(notFoundEmail));
    }


}