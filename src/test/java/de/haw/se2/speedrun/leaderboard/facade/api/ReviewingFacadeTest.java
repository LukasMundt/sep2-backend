package de.haw.se2.speedrun.leaderboard.facade.api;

import de.haw.se2.speedrun.Se2SpeedrunApplication;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = Se2SpeedrunApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class ReviewingFacadeTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    public ReviewingFacadeTest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository,
                                GameRepository gameRepository, LeaderboardRepository leaderboardRepository,
                                RunRepository runRepository, PasswordEncoder passwordEncoder) {
        super(speedrunnerRepository, administratorRepository, gameRepository, leaderboardRepository, runRepository, passwordEncoder);
    }
}
