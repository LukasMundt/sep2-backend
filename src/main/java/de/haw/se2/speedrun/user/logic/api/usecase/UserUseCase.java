package de.haw.se2.speedrun.user.logic.api.usecase;

import de.haw.se2.speedrun.user.dataaccess.api.entity.User;

public interface UserUseCase {
    User findUserByEmail(String email);
}
