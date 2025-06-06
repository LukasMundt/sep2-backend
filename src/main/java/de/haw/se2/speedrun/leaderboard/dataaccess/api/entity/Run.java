package de.haw.se2.speedrun.leaderboard.dataaccess.api.entity;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @NotEmpty
    @NonNull
    private String videoLink;

    private boolean isVerified;
}
