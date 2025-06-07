package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface RunUseCase {
    List<Run> getVerifiedLeaderboardRuns(String game, String category);
    void addUnverifiedRun(String gameSlug, String categoryId, Date date, String videoLink, Runtime runtime);
    void deleteRun(UUID runId);
}
