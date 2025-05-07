package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.facade.impl;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.facade.api.LeaderboardFacade;
import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.logic.api.usecase.LeaderboardUseCase;
import de.haw.se2.praktikum.speedrun.se2_speedrun.openapitools.model.Leaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class LeaderboardFacadeImpl implements LeaderboardFacade {

    private final LeaderboardUseCase leaderboardUseCase;

    @Autowired
    public LeaderboardFacadeImpl(LeaderboardUseCase leaderboardUseCase) {
        this.leaderboardUseCase = leaderboardUseCase;
    }

    @Override
    public ResponseEntity<Leaderboard> leaderboardGameCategoryGet(String game, String category) {
        de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Leaderboard leaderboard = leaderboardUseCase.getLeaderboard(game, category);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
