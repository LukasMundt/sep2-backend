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
import org.springframework.http.MediaType;
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
class ReviewingFacadeTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    public ReviewingFacadeTest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository,
                                GameRepository gameRepository, LeaderboardRepository leaderboardRepository,
                                RunRepository runRepository, PasswordEncoder passwordEncoder) {
        super(speedrunnerRepository, administratorRepository, gameRepository, leaderboardRepository, runRepository, passwordEncoder);
    }

    @Test
    void testGetUnreviewedSuccessfully() throws Exception {
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
    void testGetUnreviewedRunsUnauthorised() throws Exception {
    // [GIVEN]
    String gameSlug = "minecraft";
    String categoryId = "any_percent";

    // [WHEN & THEN]
    mvc.perform(get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameSlug, categoryId))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteUnreviewedRunUUIDNotFound() throws Exception {
        // [GIVEN]
        String uuid = "wrong-uuid";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        // [WHEN & THEN]
        mvc.perform(delete("/rest/api/reviews/unreviewed/{uuid}", uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUnreviewedRunUnauthorised() throws Exception {
        // [GIVEN]
        String gameId = "minecraft";
        String categoryId = "any_percent";
        String adminToken = getAccessToken("admin@admin.de", "123456Aa", mvc);
        addUnreviewedRun();
        var r = mvc.perform(get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameId, categoryId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        var uuid = r.andReturn().getResponse().getContentAsString().split(",")[2].split(":")[2].replace("\"", "");

        // [WHEN & THEN]
        mvc.perform(delete("/rest/api/reviews/unreviewed/{uuid}", uuid))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteUnreviewedRunSuccessfully() throws Exception {
        // [GIVEN]
        String gameId = "minecraft";
        String categoryId = "any_percent";
        String adminToken = getAccessToken("admin@admin.de", "123456Aa", mvc);
        addUnreviewedRun();
        var r = mvc.perform(get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameId, categoryId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        var uuid = r.andReturn().getResponse().getContentAsString().split(",")[2].split(":")[2].replace("\"", "");

        // [WHEN & THEN]
        mvc.perform(delete("/rest/api/reviews/unreviewed/{uuid}", uuid)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    void testPatchUnreviewedRunNotFound() throws Exception {
        // [GIVEN]
        String uuid = "wrong-uuid";
        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        // [WHEN & THEN]
        mvc.perform(patch("/rest/api/reviews/unreviewed/{uuid}", uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPatchUnreviewedRunUnauthorised() throws Exception {
        // [GIVEN]
        String gameId = "minecraft";
        String categoryId = "any_percent";
        String adminToken = getAccessToken("admin@admin.de", "123456Aa", mvc);
        addUnreviewedRun();
        var r = mvc.perform(get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameId, categoryId)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        var uuid = r.andReturn().getResponse().getContentAsString().split(",")[2].split(":")[2].replace("\"", "");

        mvc.perform(patch("/rest/api/reviews/unreviewed/{uuid}", uuid))
                .andExpect(status().isForbidden());
    }

    @Test
    void testPatchUnreviewedRunSuccessfully() throws Exception {
        // [GIVEN]
        String gameId = "minecraft";
        String categoryId = "any_percent";
        String adminToken = getAccessToken("admin@admin.de", "123456Aa", mvc);
        addUnreviewedRun();
        var r = mvc.perform(get("/rest/api/reviews/unreviewed/{gameSlug}/{categoryId}", gameId, categoryId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        var uuid = r.andReturn().getResponse().getContentAsString().split(",")[2].split(":")[2].replace("\"", "");

        // [WHEN & THEN]
        mvc.perform(patch("/rest/api/reviews/unreviewed/{uuid}", uuid)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    private void addUnreviewedRun() throws Exception {
        String token = getAccessToken("fastjoe@gmail.com", "123456Aa", mvc);

        // [WHEN & THEN]
        String gameId = "minecraft";
        String categoryId = "any_percent";
        mvc.perform(post("/rest/api/games/{gameSlug}/{categoryId}/runs", gameId, categoryId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "  \"date\": \"2025-06-12T08:56:35.580Z\"," +
                        "  \"runtime\": {" +
                        "    \"hours\": 0," +
                        "    \"minutes\": 0," +
                        "    \"seconds\": 0," +
                        "    \"milliseconds\": 0" +
                        "  }," +
                        "  \"videoLink\": \"string\"" +
                        "}")).andExpect(status().isOk());
    }
}
