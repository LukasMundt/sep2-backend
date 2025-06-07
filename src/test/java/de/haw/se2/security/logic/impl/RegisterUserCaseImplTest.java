package de.haw.se2.security.logic.impl;

import de.haw.se2.security.common.pojo.RegisterCredentials;
import de.haw.se2.security.logic.impl.RegisterUserCaseImpl;
import de.haw.se2.speedrun.Se2SpeedrunApplication;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RegisterUserCaseImplTest {
    @Mock
    private SpeedrunnerRepository speedrunnerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserCaseImpl registerUseCase;



    @Test
    void registerUserSuccess() {
        List<Speedrunner> speedrunners = new ArrayList<>();
        Mockito.when(passwordEncoder.encode(any(String.class))).thenAnswer(invocation -> {
            String password = invocation.getArgument(0);
            return "encoded_" + password; // Simulate encoding
        });
        Mockito.when(speedrunnerRepository.save(any(Speedrunner.class))).thenAnswer(invocation -> {;
            Speedrunner speedrunner = invocation.getArgument(0);
            speedrunners.add(speedrunner);
            return speedrunner;
        });
        String password1 = "123456aA";
        String password2 = "123456aBajdfahfahf";
        String password3 = "1234567890aC1234567890aC1234567890aC1234567890aC1234567890aC1234567890aC";
        String username1 = "tes";
        String username2 = "test2";
        String username3 = "test3test3test3test3test3test3te";
        String email1 = "test1@test.com";
        String email2 = "test2@test.com";
        String email3 = "test3@test.com";
        RegisterCredentials testCredentials1 = new RegisterCredentials();
        testCredentials1.setUsername(username1);
        testCredentials1.setEmail(email1);
        testCredentials1.setPassword(password1);
        RegisterCredentials testCredentials2 = new RegisterCredentials();
        testCredentials2.setUsername(username2);
        testCredentials2.setEmail(email2);
        testCredentials2.setPassword(password2);
        RegisterCredentials testCredentials3 = new RegisterCredentials();
        testCredentials3.setUsername(username3);
        testCredentials3.setEmail(email3);
        testCredentials3.setPassword(password3);

        assertEquals(0, speedrunners.size());
        registerUseCase.registerUser(testCredentials1);
        assertEquals(1, speedrunners.size());
        assertEquals(username1, speedrunners.getFirst().getUsername());
        assertEquals(email1, speedrunners.getFirst().getEmail());
        assertEquals("encoded_"+password1, speedrunners.getFirst().getPassword());
        assertEquals(Right.SPEEDRUNNER, speedrunners.getFirst().getRight());
        registerUseCase.registerUser(testCredentials2);
        assertEquals(2, speedrunners.size());
        assertEquals(username2, speedrunners.get(1).getUsername());
        assertEquals(email2, speedrunners.get(1).getEmail());
        assertEquals("encoded_"+password2, speedrunners.get(1).getPassword());
        assertEquals(Right.SPEEDRUNNER, speedrunners.get(1).getRight());
        registerUseCase.registerUser(testCredentials3);
        assertEquals(3, speedrunners.size());
        assertEquals(username3, speedrunners.get(2).getUsername());
        assertEquals(email3, speedrunners.get(2).getEmail());
        assertEquals("encoded_"+password3, speedrunners.get(2).getPassword());
        assertEquals(Right.SPEEDRUNNER, speedrunners.get(2).getRight());
    }
}