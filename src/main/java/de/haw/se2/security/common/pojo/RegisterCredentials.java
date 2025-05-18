package de.haw.se2.security.common.pojo;

import de.haw.se2.security.common.validations.EmailNotRegistered;
import de.haw.se2.security.common.validations.UsernameNotInUse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterCredentials {

    public static final String PASSWORD_REGEX_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

    @NotNull
    @Size(min = 3, max = 32)
    @UsernameNotInUse
    private String username;

    @NotNull
    @Pattern(regexp = PASSWORD_REGEX_PATTERN)
    // Limit password length, as BCrypt can theoretically have collisions for data bigger than 72 Bytes
    //https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
    @Size(min = 8, max = 72)
    private String password;

    @NotNull
    @Email
    @EmailNotRegistered
    private String email;
}
