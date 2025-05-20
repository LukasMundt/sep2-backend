package de.haw.se2.speedrun.user.dataaccess.api.entity;

import de.haw.se2.speedrun.user.common.api.datatype.Right;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import static de.haw.se2.security.common.pojo.RegisterCredentials.PASSWORD_REGEX_PATTERN;

@Data
@NoArgsConstructor
@Table(name = "our_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String username;

    @NonNull
    @NotNull
    @Pattern(regexp = PASSWORD_REGEX_PATTERN)
    //Only allow (BCrypt) hashed values in the DB
    @Size(min = 60, max = 60)
    private String password;

    @NonNull
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "user_right")
    @Enumerated(EnumType.STRING)
    private Right right;

    @Override
    public String toString() {
        return this.username;
    }
}
