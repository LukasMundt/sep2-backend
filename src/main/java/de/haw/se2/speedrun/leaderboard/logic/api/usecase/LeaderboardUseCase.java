package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;

public interface LeaderboardUseCase {
    Leaderboard getLeaderboard(String game, String category);
}
