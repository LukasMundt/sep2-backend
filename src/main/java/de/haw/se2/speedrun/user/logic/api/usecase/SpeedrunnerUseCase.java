package de.haw.se2.speedrun.user.logic.api.usecase;

import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;

public interface SpeedrunnerUseCase {
    Speedrunner getSpeedrunnerByEmail(String email);
}
