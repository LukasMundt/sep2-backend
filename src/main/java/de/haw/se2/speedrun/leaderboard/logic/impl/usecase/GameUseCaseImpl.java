package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.GameUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameUseCaseImpl implements GameUseCase {

    private final GameRepository gameRepository;

    @Autowired
    public GameUseCaseImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game getGameBySlug(String gameSlug) {
        Optional<Game> game = gameRepository.findBySlug(gameSlug);
        if(game.isEmpty()){
            throw new EntityNotFoundException("Game with Slug " + gameSlug + " not found");
        }

        return game.get();
    }
}
