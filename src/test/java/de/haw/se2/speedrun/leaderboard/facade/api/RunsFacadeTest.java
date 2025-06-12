package de.haw.se2.speedrun.leaderboard.facade.api;

import de.haw.se2.speedrun.Se2SpeedrunApplication;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.openapitools.model.RunDto;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Administrator;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import io.restassured.mapper.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Se2SpeedrunApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class) // required to use Spring TestContext Framework in JUnit 5
@ActiveProfiles("test") // causes exclusive creation of general and test-specific beans marked by @Profile("test")

@Transactional
@AutoConfigureMockMvc
public class RunsFacadeTest extends BaseTest
{

    @Autowired
    private MockMvc mvc;

    @Autowired
    public RunsFacadeTest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository,
                          GameRepository gameRepository, LeaderboardRepository leaderboardRepository,
                          RunRepository runRepository, PasswordEncoder passwordEncoder)
    {
        super(speedrunnerRepository, administratorRepository, gameRepository, leaderboardRepository, runRepository, passwordEncoder);
    }

    @Test
    public void testGetRunsSuccessfully() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "any_percent";

        List<Run> runs = runRepository.findAll();
        List<Game> games = gameRepository.findAll();
        List<Leaderboard> leaderboards = leaderboardRepository.findAll();

        mvc.perform(get("/rest/api/games/{gameSlug}/{categoryId}/leaderboard", gameSlug, categoryId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRunsCategoryNotFound() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "categorie_not_found";

        List<Run> runs = runRepository.findAll();
        List<Game> games = gameRepository.findAll();
        List<Leaderboard> leaderboards = leaderboardRepository.findAll();

        mvc.perform(get("/rest/api/games/{gameSlug}/{categoryId}/runs", gameSlug, categoryId))
                .andExpect(status().isNotFound());
    }
}
