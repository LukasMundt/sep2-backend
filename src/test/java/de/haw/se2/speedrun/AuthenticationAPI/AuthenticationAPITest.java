package de.haw.se2.speedrun.AuthenticationAPI;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class AuthenticationAPITest {
    private static String adminToken;
    private static String speedrunnerToken;
    private static final String AUTHENTICATION_URL = "/rest/auth";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        String adminDetails = """
                {
                  "email": "admin@admin.de",
                  "password": "123456Aa"
                }""";
        String speedrunnerDetails = """
                {
                  "email": "fastjoe@gmail.com",
                  "password": "123456Aa"
                }""";
        String token = mockMvc.perform(post(AUTHENTICATION_URL+ "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminDetails))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        adminToken = objectMapper.readTree(token).get("accessToken").asText();

        token = mockMvc.perform(post(AUTHENTICATION_URL+ "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(speedrunnerDetails))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        speedrunnerToken = objectMapper.readTree(token).get("accessToken").asText();

    }
    @Test
    public void authAdmin() throws Exception {
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
