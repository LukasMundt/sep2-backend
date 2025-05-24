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
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Administrator;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Se2SpeedrunApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class) // required to use Spring TestContext Framework in JUnit 5
@ActiveProfiles("test") // causes exclusive creation of general and test-specific beans marked by @Profile("test")

@Transactional
@AutoConfigureMockMvc
public class RunsFacadeTest
{
    private final RunRepository runRepository;

    private final SpeedrunnerRepository speedrunnerRepository;
    private final AdministratorRepository administratorRepository;

    private final PasswordEncoder passwordEncoder;

    private final GameRepository gameRepository;

    final LeaderboardRepository leaderboardRepository;

    private final Random rng;

    @Autowired
    private MockMvc mvc;

    @Autowired
    public RunsFacadeTest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository,
                          GameRepository gameRepository, LeaderboardRepository leaderboardRepository,
                          RunRepository runRepository, PasswordEncoder passwordEncoder)
    {
        this.speedrunnerRepository = speedrunnerRepository;
        this.administratorRepository = administratorRepository;
        this.gameRepository = gameRepository;
        this.leaderboardRepository = leaderboardRepository;
        this.runRepository = runRepository;
        this.rng = new Random();
        this.passwordEncoder = passwordEncoder;
    }
    @BeforeEach
    public void setUP()
    {
        addThreeUsers();
        getEntrys();
        addGames();
    }

    @AfterEach
    public void tearDown()
    {
        runRepository.deleteAll();
        leaderboardRepository.deleteAll();
        gameRepository.deleteAll();
        administratorRepository.deleteAll();
        speedrunnerRepository.deleteAll();
    }

    @Test
    public void testGetLeaderboardSuccessfully() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "any_percent";

        List<Run> runs = runRepository.findAll();
        List<Game> games = gameRepository.findAll();
        List<Leaderboard> leaderboards = leaderboardRepository.findAll();

        mvc.perform(get("/rest/api/games/minecraft/any_percent/leaderboard"))
            .andExpect(status().isOk());

        // [WHEN & THEN]
        //given()
        //        .basePath("/rest/api/games")
        //        .pathParam("gameSlug", gameSlug)
        //        .pathParam("categoryId", categoryId)
        //        .when()
        //        .get("/{gameSlug}/{categoryId}/leaderboard")
        //        .then()
        //        .statusCode(200)
        //        .assertThat()
        //        .body("size()", org.hamcrest.Matchers.greaterThan(0));
    }

    @Test
    public void testGetLeaderboardCategoryNotFound() {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "PeerDropSuxx";

        // [WHEN & THEN]
        given()
                .basePath("/rest/api/games")
                .pathParam("gameSlug", gameSlug)
                .pathParam("categoryId", categoryId)
                .when()
                .get("/{gameSlug}/{categoryId}/leaderboard")
                .then()
                .statusCode(404);
    }










    private List<Run> getEntrys(){
        Run run1 = new Run();
        Run run2 = new Run();
        run1.setDate(new Date());
        run2.setDate(new Date());
        run1.setRuntime(new de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));
        run2.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));

        Optional<Speedrunner> fastJoe = speedrunnerRepository.findByUsername("Fast Joe");
        Optional<Speedrunner> slowBob = speedrunnerRepository.findByUsername("Slow Bob");
        if(fastJoe.isEmpty() || slowBob.isEmpty()){
            throw new EntityNotFoundException("Fetter fehler lol");
        }

        run1.setSpeedrunner(fastJoe.get());
        run2.setSpeedrunner(slowBob.get());
        run1.setVerified(true);
        run2.setVerified(true);
        runRepository.saveAllAndFlush(List.of(run1, run2));
        return runRepository.findAll();
    }

    private Leaderboard addLeaderboards() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(new Category("any_percent", "Any %"));
        leaderboard.setRuns(new ArrayList<>());
        leaderboardRepository.save(leaderboard);
        var leaderboar = leaderboardRepository.findAll().getFirst();
        var r = runRepository.findAll();
        leaderboar.getRuns().addAll(r);
        return leaderboardRepository.findAll().getFirst();
    }

    private void addGames() {
        addLeaderboards();
        Game game1 = new Game();
        game1.setName("Minecraft");
        game1.setSlug("minecraft");
        game1.setImageUrl("/games/minecraft.avif");
        game1.setLeaderboards(leaderboardRepository.findAll());
        gameRepository.saveAndFlush(game1);
    }

    private void addThreeUsers(){
        String passwordEncrypted = passwordEncoder.encode("123456Aa");

        Speedrunner speedrunner1 = new Speedrunner();
        Speedrunner speedrunner2 = new Speedrunner();
        Administrator admin = new Administrator();
        speedrunner1.setUsername("Fast Joe");
        speedrunner2.setUsername("Slow Bob");
        admin.setUsername("The admin");
        speedrunner1.setPassword(passwordEncrypted);
        speedrunner2.setPassword(passwordEncrypted);
        admin.setPassword(passwordEncrypted);
        speedrunner1.setEmail("fastjoe@gmail.com");
        speedrunner2.setEmail("slowbob@gmail.com");
        admin.setEmail("admin@admin.de");
        speedrunner1.setRight(Right.SPEEDRUNNER);
        speedrunner2.setRight(Right.SPEEDRUNNER);
        admin.setRight(Right.ADMIN);
        speedrunnerRepository.saveAllAndFlush(List.of(speedrunner1, speedrunner2));
        administratorRepository.saveAndFlush(admin);
    }
}
