package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.logic.api.usecase.LeaderboardUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LeaderboardUseCaseImpl implements LeaderboardUseCase {

    private final GameRepository gameRepository;

    @Autowired
    public LeaderboardUseCaseImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Leaderboard getLeaderboard(String gameName, String category) {
        Optional<Game> leaderboardGame =  gameRepository.findByName(gameName);

        if (leaderboardGame.isEmpty()) {
            throw new EntityNotFoundException(gameName);
        }

        List<Leaderboard> categoryLeaderboards =  leaderboardGame
                .get()
                .getLeaderboards()
                .stream()
                .filter(g -> g.getCategory().equals(Category.valueOf(category)))
                .toList();

        if (categoryLeaderboards.isEmpty()) {
            throw new EntityNotFoundException(gameName);
        }

        return categoryLeaderboards.getFirst();
    }
}
