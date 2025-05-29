package de.haw.se2.speedrun.user.dataaccess.api.entity;

import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("SPEEDRUNNER")
public class Speedrunner extends User {

    @ElementCollection(fetch = FetchType.LAZY)
    private List<FasterInformation> newFasterPlayers;
}
