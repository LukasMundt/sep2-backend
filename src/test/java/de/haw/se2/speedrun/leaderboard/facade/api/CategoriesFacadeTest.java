package de.haw.se2.speedrun.leaderboard.facade.api;

import de.haw.se2.speedrun.Se2SpeedrunApplication;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Se2SpeedrunApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class CategoriesFacadeTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    public CategoriesFacadeTest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository,
                                GameRepository gameRepository, LeaderboardRepository leaderboardRepository,
                                RunRepository runRepository, PasswordEncoder passwordEncoder) {
        super(speedrunnerRepository, administratorRepository, gameRepository, leaderboardRepository, runRepository, passwordEncoder);
    }

    @Test
    public void testGetCategorySuccessfully() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "any_percent";

        // [WHEN & THEN]
        mvc.perform(get("/rest/api/games/{gameSlug}/categories", gameSlug))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoryNotFound() throws Exception {
        // [GIVEN]
        String gameSlug = "nonexistent_game";

        // [WHEN & THEN]
        mvc.perform(get("/rest/api/games/{gameSlug}/categories", gameSlug))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddCategoryBadRequest() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String invalidRequestBody = "{ \"invalidField\": \"value\" }"; // Invalid JSON structure

        String token = super.getAccessToken("admin@admin.de", "123456Aa", mvc);

        System.out.println(token);

        // [WHEN & THEN]
        mvc.perform(post("/rest/api/games/{gameSlug}/categories", gameSlug)
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    // TODO Berechtigungen zum Löschen fehlen
    @Test
    public void testDeleteCategorySuccessfully() throws Exception {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "any_percent";

        // Ensure the category exists before deletion
        mvc.perform(get("/rest/api/games/{gameSlug}/categories", gameSlug))
                .andExpect(status().isOk());

        // [WHEN & THEN] Delete the category
        mvc.perform(delete("/rest/api/games/{gameSlug}/{categoryId}", gameSlug, categoryId))
                .andExpect(status().isNoContent());

        // Verify the category no longer exists
        mvc.perform(get("/rest/api/games/{gameSlug}/categories", gameSlug))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    assertFalse(response.contains(categoryId), "Category should be deleted");
                });
    }
}