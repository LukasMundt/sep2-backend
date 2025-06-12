package de.haw.se2.speedrun.leaderboard.facade.api;

import de.haw.se2.speedrun.Se2SpeedrunApplication;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void testGetUnreviewedSuccessfully() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "any_percent";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        // [WHEN & THEN]
        mvc.perform(get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameSlug, categoryId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUnreviewedRunsUnauthorised() throws Exception {
    // [GIVEN]
    String gameSlug = "minecraft";
    String categoryId = "any_percent";

    // [WHEN & THEN]
    mvc.perform(get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameSlug, categoryId))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteUnreviewedRunUUIDNotFound() throws Exception {
        // [GIVEN]
        String uuid = "wrong-uuid";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        // [WHEN & THEN]
        mvc.perform(delete("/rest/api/reviews/unreviewed/{uuid}", uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUnreviewedRunUnauthorised() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "any_percent";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        var result = get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameSlug, categoryId)
                .header("Authorization", "Bearer " + token);

        var lambdaContext = new Object() {
            String token;
        };

        result.andExpect(status().isOk()).andExpect(r -> {
            lambdaContext.token = r.getResponse().getContentAsString().split(",")[0].split(":")[1].replace("\"", "");
        });

        // [WHEN & THEN]
        mvc.perform(delete("/rest/api/reviews/unreviewed/{uuid}", uuid))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteUnreviewedRunSuccessfully() throws Exception {
        // [GIVEN]
        String uuid = "";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        // [WHEN & THEN]
        mvc.perform(delete("/rest/api/games/{gameSlug}/{categoryId}/leaderboard/", uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testPatchUnreviewedRunNotFound() throws Exception {
        // [GIVEN]
        String uuid = "wrong-uuid";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        // [WHEN & THEN]
        mvc.perform(patch("/rest/api/reviews/unreviewed/{uuid}", uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPatchUnreviewedRunUnauthorised() throws Exception {
        // [GIVEN]
        String uuid = "";

        // [WHEN & THEN]
        mvc.perform(patch("/rest/api/reviews/unreviewed/{uuid}", uuid))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testPatchUnreviewedRunSuccessfully() throws Exception {
        // [GIVEN]
        String uuid = "";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        // [WHEN & THEN]
        mvc.perform(patch("/rest/api/reviews/unreviewed/{uuid}", uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
