package de.haw.se2.speedrun.user.dataaccess.api.entity;

import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("SPEEDRUNNER")
@NoArgsConstructor
public class Speedrunner extends User {

    @ElementCollection(fetch = FetchType.LAZY)
    @NonNull
    @NotNull
    private List<FasterInformation> newFasterPlayers;
}
