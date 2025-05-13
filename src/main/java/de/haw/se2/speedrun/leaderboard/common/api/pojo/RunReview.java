package de.haw.se2.speedrun.leaderboard.common.api.pojo;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RunReview {

    @NonNull
    private String gameName;

    @NonNull
    private String categoryLabel;

    @NonNull
    private UUID uuid;

    @NonNull
    private Run run;
}
