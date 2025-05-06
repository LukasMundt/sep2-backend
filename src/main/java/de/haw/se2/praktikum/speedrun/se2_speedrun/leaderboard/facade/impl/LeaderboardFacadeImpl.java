package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.facade.impl;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.facade.api.LeaderboardFacade;
import de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model.Leaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class LeaderboardFacadeImpl implements LeaderboardFacade {

    private final GameRepository gameRepository;

    @Autowired
    public LeaderboardFacadeImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public ResponseEntity<Leaderboard> leaderboardGameCategoryGet(String game, String category) {
        Optional<Game> leaderboardGame = gameRepository.findByName(game);
        leaderboardGame.leaderboards()
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
