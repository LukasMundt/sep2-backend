package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;

import java.util.List;

public interface LeaderboardUseCase {
    List<Run> getVerifiedLeaderboardRuns(String game, String category);
}
