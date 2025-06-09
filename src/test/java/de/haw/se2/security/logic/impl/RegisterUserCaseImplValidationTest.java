package de.haw.se2.security.logic.impl;

import de.haw.se2.security.common.pojo.RegisterCredentials;
import de.haw.se2.speedrun.Se2SpeedrunApplication;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest(classes = Se2SpeedrunApplication.class)
class RegisterUserCaseImplValidationTest {
    @Autowired
    private RegisterUserCaseImpl registerUseCase;
    @Test
    void registerUserBadPassword() {
        String password1 = "12345aA";
        String password2 = "11111111AAA";
        String password3 = "1234567890aC1234567890aC1234567890aC1234567890aC1234567890aC1234567890aCa";
        String password4 = "aaaaaaaaa11";
        String password5 = "AAAAAAAAAAaa";
        String password6 = "";
        String username1 = "tes";
        String username2 = "test2";
        String username3 = "test3";
        String username4 = "test4";
        String username5 = "test3test3test3test3test3test3te";
        String username6 = "test6";
        String email1 = "test1@test.com";
        String email2 = "test2@test.com";
        String email3 = "test3@test.com";
        String email4 = "test4@test.com";
        String email5 = "test5@test.com";
        String email6 = "test6@test.com";
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
        RegisterCredentials testCredentials4 = new RegisterCredentials();
        testCredentials4.setUsername(username4);
        testCredentials4.setEmail(email4);
        testCredentials4.setPassword(password4);
        RegisterCredentials testCredentials5 = new RegisterCredentials();
        testCredentials5.setUsername(username5);
        testCredentials5.setEmail(email5);
        testCredentials5.setPassword(password5);
        RegisterCredentials testCredentials6 = new RegisterCredentials();
        testCredentials6.setUsername(username6);
        testCredentials6.setEmail(email6);
        testCredentials6.setPassword(password6);
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials1));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials2));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials3));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials4));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials5));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials6));
    }

    @Test
    void registerUserBadEmail() {
        String password1 = "123456aA";
        String password2 = "123456aA";
        String password3 = "123456aA";
        String password4 = "123456aA";
        String password5 = "123456aA";
        String password6 = "123456aA";
        String username1 = "tes";
        String username2 = "test2";
        String username3 = "test3";
        String username4 = "test4";
        String username5 = "test3test3test3test3test3test3te";
        String username6 = "test6";
        String email1 = "";
        String email2 = "test2@test";
        String email3 = "test3test.com";
        String email4 = "test4";
        String email5 = "test5@test.cojadhshfashfhs";
        String email6 = "@test.com";
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
        RegisterCredentials testCredentials4 = new RegisterCredentials();
        testCredentials4.setUsername(username4);
        testCredentials4.setEmail(email4);
        testCredentials4.setPassword(password4);
        RegisterCredentials testCredentials5 = new RegisterCredentials();
        testCredentials5.setUsername(username5);
        testCredentials5.setEmail(email5);
        testCredentials5.setPassword(password5);
        RegisterCredentials testCredentials6 = new RegisterCredentials();
        testCredentials6.setUsername(username6);
        testCredentials6.setEmail(email6);
        testCredentials6.setPassword(password6);
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials1));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials2));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials3));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials4));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials5));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials6));
    }

    @Test
    void registerUserBadUsername() {
        String password1 = "123456aA";
        String password2 = "123456aA";
        String password3 = "123456aA";
        String password4 = "123456aA";
        String password5 = "123456aA";
        String password6 = "123456aA";
        String username1 = "te";
        String username2 = "";
        String username3 = "test3test3test3test3test3test3testest3test3test3test3test3test3tes";
        String username4 = "t";
        String username5 = "test3test3test3test3test3test3tes";
        String username6 = "";
        String email1 = "test1@test.com";
        String email2 = "test2@test.com";
        String email3 = "test3@test.com";
        String email4 = "test4@test.com";
        String email5 = "test5@test.com";
        String email6 = "test6@test.com";
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
        RegisterCredentials testCredentials4 = new RegisterCredentials();
        testCredentials4.setUsername(username4);
        testCredentials4.setEmail(email4);
        testCredentials4.setPassword(password4);
        RegisterCredentials testCredentials5 = new RegisterCredentials();
        testCredentials5.setUsername(username5);
        testCredentials5.setEmail(email5);
        testCredentials5.setPassword(password5);
        RegisterCredentials testCredentials6 = new RegisterCredentials();
        testCredentials6.setUsername(username6);
        testCredentials6.setEmail(email6);
        testCredentials6.setPassword(password6);
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials1));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials2));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials3));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials4));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials5));
        assertThrows(ValidationException.class, () -> registerUseCase.registerUser(testCredentials6));
    }
}