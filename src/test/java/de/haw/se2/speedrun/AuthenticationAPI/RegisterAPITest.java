package de.haw.se2.speedrun.AuthenticationAPI;

import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterAPITest {
    @Autowired
    private SpeedrunnerRepository speedrunnerRepository;
    @Autowired
    private MockMvc mockMvc;
    private static final String REGISTER_URL = "/rest/auth/register";



    @Test
    public void registerTest() throws Exception {
        String speedrunnerDetails = """
                {
                  "username": "test",
                  "password": "123456Aa",
                  "email": "test@test.com"
                }""";
        mockMvc.perform(post(REGISTER_URL)
                        .contentType("application/json")
                        .content(speedrunnerDetails))
                .andExpect(status().isOk())
                .andReturn();
        speedrunnerRepository.delete(speedrunnerRepository.findByUsername("test").get());
    }

    @Test
    public void registerWithExistingEmailTest() throws Exception {
        String speedrunnerDetails = """
                {
                  "username": "Fast_Joe",
                  "password": "123456Aa",
                  "email": "fastjoe@gmail.com"
                }""";
        mockMvc.perform(post(REGISTER_URL)
                        .contentType("application/json")
                        .content(speedrunnerDetails))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void registerWithExistingUsernameTest() throws Exception {
        String speedrunnerDetails = """
                {
                  "username": "Fast Joe",
                  "password": "123456Aa",
                  "email": "fasttjoe@gmail.com"
                }""";
        mockMvc.perform(post(REGISTER_URL)
                        .contentType("application/json")
                        .content(speedrunnerDetails))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void registerPasswordRequirementsNotMetTest() throws Exception {
        String speedrunnerDetails = """
                {
                  "username": "test",
                  "password": "123456",
                  "email": "test@test.com"
                }""";
        mockMvc.perform(post(REGISTER_URL)
                        .contentType("application/json")
                        .content(speedrunnerDetails))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }

    @Test
    public void regiserWithInvalidEmailTest() throws Exception {
        String speedrunnerDetails = """
                {
                  "username": "testA",
                  "password": "123456Aa",
                  "email": "test@test"
                }""";
        mockMvc.perform(post(REGISTER_URL)
                        .contentType("application/json")
                        .content(speedrunnerDetails))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        speedrunnerDetails = """
                {
                  "username": "testB",
                  "password": "123456Aa",
                  "email": ""
                }""";
        mockMvc.perform(post(REGISTER_URL)
                        .contentType("application/json")
                        .content(speedrunnerDetails))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

    }
    @AfterEach
    public void cleanUp() {
        if (speedrunnerRepository.findByUsername("testA").isPresent()) {
            speedrunnerRepository.delete(speedrunnerRepository.findByUsername("testA").get());
        }
        if (speedrunnerRepository.findByUsername("testB").isPresent()) {
            speedrunnerRepository.delete(speedrunnerRepository.findByUsername("testB").get());
        }
    }
}
