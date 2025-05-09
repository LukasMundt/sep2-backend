package de.haw.se2.speedrun.leaderboard.dataaccess.api.entity;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Entry {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @NotNull
    @Temporal(TemporalType.DATE)
    @Past
    private Date date;

    @Embedded
    @NonNull
    @NotNull
    private Runtime runtime;

    @NonNull
    @NotNull
    @ManyToOne
    private Speedrunner speedrunner;
}
