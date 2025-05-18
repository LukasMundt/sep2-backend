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

    @NotNull
    @Size(min = 3, max = 32)
    @UsernameNotInUse
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
    private String password;

    @NotNull
    @Email
    @EmailNotRegistered
    private String email;
}
