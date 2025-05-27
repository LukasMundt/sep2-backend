package de.haw.se2.speedrun.leaderboard.facade.api;

import com.fasterxml.jackson.core.type.TypeReference;
import de.haw.se2.speedrun.Se2SpeedrunApplication;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.openapitools.model.GameDto;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Se2SpeedrunApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class) // required to use Spring TestContext Framework in JUnit 5
@ActiveProfiles("test") // causes exclusive creation of general and test-specific beans marked by @Profile("test")

@Transactional
@AutoConfigureMockMvc
public class GamesFacadeTest extends BaseTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    public GamesFacadeTest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository,
                          GameRepository gameRepository, LeaderboardRepository leaderboardRepository,
                          RunRepository runRepository, PasswordEncoder passwordEncoder)
    {
        super(speedrunnerRepository, administratorRepository, gameRepository, leaderboardRepository, runRepository, passwordEncoder);
    }

    @Override
    protected void addGames() {
        addLeaderboards();
        Game game1 = new Game();
        game1.setName("Minecraft");
        game1.setSlug("minecraft");
        game1.setImageUrl("/games/minecraft.avif");
        game1.setLeaderboards(leaderboardRepository.findAll());
        gameRepository.save(game1);

        Game game2 = new Game();
        game2.setName("SuperMonkeyBall");
        game2.setSlug("supermonkeyball");
        game2.setImageUrl("/games/supermonkeyball.avif");
        game2.setLeaderboards(leaderboardRepository.findAll());
        gameRepository.save(game2);

    }

    @Test
    public void testGetGameSuccessfully() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";

        List<Run> runs = runRepository.findAll();
        List<Game> games = gameRepository.findAll();
        List<Leaderboard> leaderboards = leaderboardRepository.findAll();
        // [WHEN & THEN]
        mvc.perform(get("/rest/api/games/minecraft"))
                .andExpect(status().isOk());


        // [GIVEN]
        gameSlug = "supermonkeyball";

        runs = runRepository.findAll();
        games = gameRepository.findAll();
        leaderboards = leaderboardRepository.findAll();
        // [WHEN & THEN]
        mvc.perform(get("/rest/api/games/supermonkeyball"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllGamesSuccessfully() throws Exception {
        // [WHEN]
        var result = mvc.perform(get("/rest/api/games/all"))
                .andExpect(status().isOk())
                .andReturn();

        // [THEN]
        String jsonResponse = result.getResponse().getContentAsString();
        List<GameDto> actualGames = new ObjectMapper().readValue(jsonResponse, new TypeReference<>() {});

        List<String> expectedGameSlugs = List.of("minecraft", "supermonkeyball");
        List<String> actualGameSlugs = actualGames.stream().map(GameDto::getSlug).toList();

        assertEquals(expectedGameSlugs.size(), actualGameSlugs.size());
        assertTrue(actualGameSlugs.containsAll(expectedGameSlugs));

        given()
                .basePath("/rest/api/games")
                .when()
                .get("/all")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInvalidEndpointReturns404() {
        given()
                .basePath("/rest/api/games")
                .when()
                .get("/PeerDropSuxx")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetGamesNotFound() {
        // [GIVEN]
        String gameSlug = "PeerDropSuxx";

        // [WHEN & THEN]
        given()
                .basePath("/rest/api/games")
                .pathParam("gameSlug", gameSlug)
                .when()
                .get("/{gameSlug}")
                .then()
                .statusCode(404);
    }
}
