package de.haw.se2.speedrun.user.dataaccess.api.entity;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("SPEEDRUNNER")
public class Speedrunner extends User {

    @ManyToOne
    private Run runThatBeatYou;

    @ManyToOne
    private Speedrunner beatenBy;
}
