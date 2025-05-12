package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.LeaderboardUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LeaderboardUseCaseImpl implements LeaderboardUseCase {

    private final GameRepository gameRepository;

    @Autowired
    public LeaderboardUseCaseImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Leaderboard getLeaderboard(String gameSlug, String categoryId) {
        Optional<Game> leaderboardGame =  gameRepository.findBySlug(gameSlug);

        if (leaderboardGame.isEmpty()) {
            throw new EntityNotFoundException(gameSlug);
        }

        List<Leaderboard> categoryLeaderboards =  leaderboardGame
                .get()
                .getLeaderboards()
                .stream()
                .filter(g -> g.getCategory().categoryId().equalsIgnoreCase(categoryId))
                .toList();

        if (categoryLeaderboards.isEmpty()) {
            throw new EntityNotFoundException(categoryId);
        }

        return categoryLeaderboards.getFirst();
    }
}
