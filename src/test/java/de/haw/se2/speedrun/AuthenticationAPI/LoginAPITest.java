package de.haw.se2.speedrun.AuthenticationAPI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginAPITest {
    @Autowired
    private MockMvc mockMvc;

    private static final String LOGIN_URL = "/rest/auth/login";

    @Test
    public void loginSpeedrunnerTest() throws Exception {
        String speedrunnerDetails = """
                {
                  "email": "fastjoe@gmail.com",
                  "password": "123456Aa"
                }""";
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(speedrunnerDetails))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn();
    }

    @Test
    public void loginAdminTest() throws Exception {
        String adminDetails = """
                {
                  "email": "admin@admin.de",
                  "password": "123456Aa"
                }""";
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminDetails))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn();
    }

    @Test
    public void loginWithInvalidCredentialsSpeedrunnerTest() throws Exception {
        String adminDetailsWrongEmail = """
                {
                  "email": "admi@admin.de",
                  "password": "123456Aa"
                }""";

        String adminDetailsWrongPassword = """
                {
                  "email": "admin@admin.de",
                  "password": "123456Ab"
                }""";
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminDetailsWrongEmail))
                .andExpect(status().isUnauthorized())
                .andReturn();
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminDetailsWrongPassword))
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    @Test
    public void loginWithInvalidCredentialsAdminTest() throws Exception {
        String speedrunnerDetailsWrongEmail = """
                {
                  "email": "fastjo@gmail.com",
                  "password": "123456Aa"
                }""";
        String speedrunnerDetailsWrongPassword = """
                {
                  "email": "fastjoe@gmail.com",
                  "password": "123456Ab"
                }""";
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(speedrunnerDetailsWrongEmail))
                .andExpect(status().isUnauthorized())
                .andReturn();
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(speedrunnerDetailsWrongPassword))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}
