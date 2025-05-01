package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.common.api.datatype.Category;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Leaderboard {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @NonNull
    @NotNull
    private Category category;

    @NonNull
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LeaderboardEntry> entries;
}
