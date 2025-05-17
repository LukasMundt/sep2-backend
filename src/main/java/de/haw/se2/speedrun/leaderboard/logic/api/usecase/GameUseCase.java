package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;

import java.util.List;

public interface GameUseCase {
    List<Game> getAllGames();
    Game getGameBySlug(String gameSlug);
}
