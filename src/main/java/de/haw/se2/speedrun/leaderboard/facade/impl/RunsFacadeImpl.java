package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.facade.api.RunsFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunUseCase;
import de.haw.se2.speedrun.openapitools.model.RunDto;
import de.haw.se2.speedrun.openapitools.model.RunSubmit;
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
    private final RunUseCase runUseCase;

    @Autowired
    public RunsFacadeImpl(CustomizedModelMapper mapper, RunUseCase runUseCase) {
        this.mapper = mapper;
        this.runUseCase = runUseCase;
    }

    @Override
    public ResponseEntity<List<RunDto>> restApiGamesGameSlugCategoryIdLeaderboardGet(String gameSlug, String categoryId) {
        List<Run> runs = runUseCase.getVerifiedLeaderboardRuns(gameSlug, categoryId);

        List<RunDto> dtos = runs
                .stream()
                .map(r -> mapper.map(r, RunDto.class))
                .toList();

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> restApiGamesGameSlugCategoryIdSubmitPost(String gameSlug, String categoryId, RunSubmit runSubmit) {
        Runtime runtime = mapper.map(runSubmit.getRuntime(), Runtime.class);

        //TODO Speedrunner anhand vom token erkennen
        runUseCase.addUnverifiedRun(gameSlug, categoryId, "UNKOWN SPEEDRUNNER", runSubmit.getDate(), runtime);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


