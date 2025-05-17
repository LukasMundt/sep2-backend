package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.facade.api.RunsFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.LeaderboardUseCase;
import de.haw.se2.speedrun.openapitools.model.RunDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class RunsFacadeImpl implements RunsFacade {

    private final CustomizedModelMapper mapper;
    private final LeaderboardUseCase leaderboardUseCase;

    @Autowired
    public RunsFacadeImpl(CustomizedModelMapper mapper, LeaderboardUseCase leaderboardUseCase) {
        this.mapper = mapper;
        this.leaderboardUseCase = leaderboardUseCase;
    }

    @Override
    public ResponseEntity<List<RunDto>> restApiGamesGameSlugCategoryIdLeaderboardGet(String gameSlug, String categoryId) {
        List<Run> runs = leaderboardUseCase.getVerifiedLeaderboardRuns(gameSlug, categoryId);

        List<RunDto> dto = runs
                .stream()
                .map(r -> mapper.map(r, RunDto.class))
                .toList();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> restApiGamesGameSlugCategoryIdSubmitPost(String gameSlug, String categoryId, RunDto runDto) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}


