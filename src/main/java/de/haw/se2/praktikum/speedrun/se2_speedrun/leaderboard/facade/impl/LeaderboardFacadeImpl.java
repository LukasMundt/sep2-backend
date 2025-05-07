package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.facade.impl;

import de.haw.se2.praktikum.speedrun.se2_speedrun.common.MyModelMapper;
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
    private final MyModelMapper mapper;

    @Autowired
    public LeaderboardFacadeImpl(LeaderboardUseCase leaderboardUseCase, MyModelMapper mapper) {
        this.leaderboardUseCase = leaderboardUseCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Leaderboard> leaderboardGameCategoryGet(String game, String category) {
        de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Leaderboard leaderboard = leaderboardUseCase.getLeaderboard(game, category);

        Leaderboard convertedLeaderboard = mapper.map(leaderboard, Leaderboard.class);

        return new ResponseEntity<>(convertedLeaderboard, HttpStatus.OK);
    }
}
