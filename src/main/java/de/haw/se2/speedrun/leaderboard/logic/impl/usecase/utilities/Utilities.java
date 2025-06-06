package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.utilities;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Utilities {

    public static final String NOT_FOUND = " not found";

    private final RunRepository runRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final GameRepository gameRepository;

    public Run getRun(UUID runId) {
        Optional<Run> run = runRepository.getRunById(runId);

        if(run.isEmpty()){
            throw new EntityNotFoundException("Run with UUID " + runId + NOT_FOUND);
        }

        return run.get();
    }

    public Leaderboard getLeaderboardByRun(Run run) {
        Optional<Leaderboard> leaderboard = leaderboardRepository.findLeaderboardByRunsContaining(run);
        if(leaderboard.isEmpty()){
            throw new EntityNotFoundException(String.format("Leaderboard of run '%s'" + NOT_FOUND, run.getId().toString()));
        }

        return leaderboard.get();
    }

    public Leaderboard getLeaderboard(Game game, String categoryId) {
        Optional<Leaderboard> leaderboard = game.getLeaderboards()
                .stream()
                .filter(l -> l.getCategory()
                        .getCategoryId()
                        .equalsIgnoreCase(categoryId))
                .findFirst();

        if(leaderboard.isEmpty()){
            throw new EntityNotFoundException(String.format("Leaderboard of category '%s'" + NOT_FOUND, categoryId));
        }

        return leaderboard.get();
    }

    public Game getGame(String gameSlug) {
        Optional<Game> game = gameRepository.findBySlug(gameSlug);
        if (game.isEmpty()){
            throw new EntityNotFoundException(String.format("Game '%s'" + NOT_FOUND, gameSlug));
        }

        return game.get();
    }

    public Game getGame(Leaderboard leaderboard) {
        Optional<Game> game = gameRepository.findGameByLeaderboardsContaining(leaderboard);
        if (game.isEmpty()) {
            throw new EntityNotFoundException("Game of Leaderboard with id " + leaderboard.getId() + NOT_FOUND);
        }

        return game.get();
    }
}
