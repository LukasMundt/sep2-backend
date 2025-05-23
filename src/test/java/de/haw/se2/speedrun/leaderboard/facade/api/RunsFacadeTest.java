package de.haw.se2.speedrun.leaderboard.facade.api;

import de.haw.se2.speedrun.HealthController;
import de.haw.se2.speedrun.Se2SpeedrunApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static io.restassured.RestAssured.given;

@SpringBootTest(classes = Se2SpeedrunApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class) // required to use Spring TestContext Framework in JUnit 5
@ActiveProfiles("test") // causes exclusive creation of general and test-specific beans marked by @Profile("test")

public class RunsFacadeTest
{
    @Autowired
    private HealthController healthController;

    @BeforeEach
    public void setUP()
    {
        healthController.insertSampleData();
    }

    @AfterEach
    public void tearDown()
    {
        healthController.deleteSampleData();
    }

    @Test
    public void testGetLeaderboardSuccessfully() {
        // [GIVEN]
        String gameSlug = "minecraft";
        String categoryId = "any_percent";

        // [WHEN & THEN]
        given()
                .basePath("/rest/api/games")
                .pathParam("gameSlug", gameSlug)
                .pathParam("categoryId", categoryId)
                .when()
                .get("/{gameSlug}/{categoryId}/leaderboard")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", org.hamcrest.Matchers.greaterThan(0));
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
}
