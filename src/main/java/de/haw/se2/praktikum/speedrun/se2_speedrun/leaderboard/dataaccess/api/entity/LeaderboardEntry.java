package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.common.api.datatype.Runtime;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LeaderboardEntry {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    @NonNull
    @NotNull
    private Date date;

    @Embedded
    @NonNull
    @NotNull
    private Runtime runtime;
}
