package de.haw.se2.security.logic.impl;

import de.haw.se2.security.common.pojo.RegisterCredentials;
import de.haw.se2.security.logic.api.RegisterUseCase;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
public class RegisterUserCaseImpl implements RegisterUseCase {

    private final SpeedrunnerRepository speedrunnerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterUserCaseImpl(SpeedrunnerRepository speedrunnerRepository, PasswordEncoder passwordEncoder) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void registerUser(@Valid RegisterCredentials registerCredentials) {
        Speedrunner speedrunner = new Speedrunner();
        speedrunner.setEmail(registerCredentials.getEmail());
        speedrunner.setUsername(registerCredentials.getUsername());
        speedrunner.setPassword(passwordEncoder.encode(registerCredentials.getPassword()));
        speedrunner.setRight(Right.SPEEDRUNNER);
        speedrunnerRepository.save(speedrunner);
    }
}
