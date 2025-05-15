package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.facade.api.LeaderboardFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.LeaderboardUseCase;
import de.haw.se2.speedrun.openapitools.model.Leaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(
        origins = {"http:localhost:5173", }, // allow only a specific domain
        methods = {RequestMethod.GET, RequestMethod.POST}, // restrict methods
        allowedHeaders = "*",
        allowCredentials = "true"
)
@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class LeaderboardFacadeImpl implements LeaderboardFacade {

    private final LeaderboardUseCase leaderboardUseCase;
    private final CustomizedModelMapper mapper;

    @Autowired
    public LeaderboardFacadeImpl(LeaderboardUseCase leaderboardUseCase, CustomizedModelMapper mapper) {
        this.leaderboardUseCase = leaderboardUseCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Leaderboard> leaderboardGameCategoryGet(String game, String category) {
        de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard leaderboard = leaderboardUseCase.getLeaderboard(game, category);

        Leaderboard convertedLeaderboard = mapper.map(leaderboard, Leaderboard.class);

        return new ResponseEntity<>(convertedLeaderboard, HttpStatus.OK);
    }
}
