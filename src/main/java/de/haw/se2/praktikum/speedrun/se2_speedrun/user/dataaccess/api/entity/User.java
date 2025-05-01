package de.haw.se2.praktikum.speedrun.se2_speedrun.user.dataaccess.api.entity;

import de.haw.se2.praktikum.speedrun.se2_speedrun.user.common.api.datatype.Right;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "our_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NonNull
    @NotNull
    private String password;

    @NonNull
    @NotNull
    @Email
    private String email;

    @Column(name = "user_right")
    @Enumerated(EnumType.STRING)
    private Right right;
}
