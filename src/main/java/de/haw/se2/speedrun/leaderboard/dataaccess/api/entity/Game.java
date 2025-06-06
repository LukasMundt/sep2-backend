package de.haw.se2.speedrun.leaderboard.dataaccess.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    private String name;

    @NonNull
    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leaderboard> leaderboards;

    @NonNull
    @NotNull
    private String imageUrl;

    @NonNull
    @NotNull
    private String slug;
}
