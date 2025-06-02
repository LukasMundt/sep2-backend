package de.haw.se2.speedrun.AuthenticationAPI;



import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.leaderboard.facade.api.BaseTest;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationAPITest extends BaseTest {
    private static final String adminEmail = "admin@admin.de";
    private static final String adminPassword = "123456Aa";
    private static final String speedrunnerEmail = "fastjoe@gmail.com";
    private static final String speedrunnerPassword = "123456Aa";
    private static final String AUTHENTICATION_URL = "/rest/auth";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public AuthenticationAPITest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository, GameRepository gameRepository, LeaderboardRepository leaderboardRepository, RunRepository runRepository, PasswordEncoder passwordEncoder) {
        super(speedrunnerRepository, administratorRepository, gameRepository, leaderboardRepository, runRepository, passwordEncoder);
    }

    @Test
    public void authAdmin() throws Exception {
        String adminToken = getAccessToken(adminEmail, adminPassword,mockMvc);
        mockMvc.perform(get(AUTHENTICATION_URL)
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
            ["ADMIN", "USER"]
        """));
    }

    @Test
    public void authSpeedrunner() throws Exception {
        String speedrunnerToken = getAccessToken(speedrunnerEmail, speedrunnerPassword, mockMvc);
        mockMvc.perform(get(AUTHENTICATION_URL)
                        .header("Authorization", "Bearer " + speedrunnerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
            ["USER"]
        """));
    }

    @Test
    public void authWithOutToken() throws Exception {
        mockMvc.perform(get(AUTHENTICATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authWithWrongToken() throws Exception {
        String wrongToken = "ayJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW5AYWRtaW4uZGUiLCJleHAiOjE3NDgyODYzNTgsImlhdCI6MTc0ODI4Mjc1OCwiYXV0aG9yaXRpZXMiOlsiQURNSU4iLCJVU0VSIl19.s-6uYnEiYgRN4NhPk3hxmUxONK2GlTLamhCAM5cfByErBD11dAaDZZkaOQI-z6v-1SW-a6medF671e6FGNkhHMUR5pPj27SBzDoMEaQRimFttwIkFOAQ1iQOBU3NtqEookFor3VKVZg6bnhq4nDEqYBDkg6G81bXsFPq7ZxnUS-9pE_L9isFUsETA47cJN3AQ1A6RtpUOxUSJsjraJD3my3Kf4ganjo8PJNJBmHi1IZ4vkDnWCmn2OWCjDVkdlgEQ3zxaRWZ_9vmNC0QLtcIut19EcjuY9TeOBMX7JdYuTKR-EGbkcwIBIZy868E2ORox-Gse4ZIhcKVpm8e9jiGbA";
        mockMvc.perform(get(AUTHENTICATION_URL)
                        .header("Authorization", "Bearer " + wrongToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }
}
