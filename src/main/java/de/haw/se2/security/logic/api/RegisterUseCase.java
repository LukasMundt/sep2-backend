package de.haw.se2.security.logic.api;

import de.haw.se2.security.common.pojo.RegisterCredentials;
import jakarta.validation.Valid;

public interface RegisterUseCase {
    void registerUser(@Valid RegisterCredentials registerCredentials);
}
