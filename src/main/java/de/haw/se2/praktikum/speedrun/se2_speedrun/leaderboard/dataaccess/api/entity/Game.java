package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotNull
    private String name;

    @NonNull
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leaderboard> leaderboards;

    @NonNull
    @NotNull
    private String imageUrl;

    @NonNull
    @NotNull
    private String slug;
}
