package de.haw.se2.speedrun.leaderboard.dataaccess.api.entity;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    @Embedded
    private Category category;

    @NonNull
    @NotNull
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("runtime asc")
    @BatchSize(size = 100)
    private List<Run> runs;
}
