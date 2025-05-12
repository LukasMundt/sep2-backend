package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.facade.api.GamesFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.GameUseCase;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.LeaderboardUseCase;
import de.haw.se2.speedrun.openapitools.model.Category;
import de.haw.se2.speedrun.openapitools.model.GameDto;
import de.haw.se2.speedrun.openapitools.model.RunDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class GamesFacadeImpl implements GamesFacade {

    private final GameUseCase gameUseCase;
    private final LeaderboardUseCase leaderboardUseCase;
    private final CustomizedModelMapper mapper;

    @Autowired
    public GamesFacadeImpl(LeaderboardUseCase leaderboardUseCase, CustomizedModelMapper mapper, GameUseCase gameUseCase) {
        this.leaderboardUseCase = leaderboardUseCase;
        this.mapper = mapper;
        this.gameUseCase = gameUseCase;
    }

    @Override
    public ResponseEntity<List<GameDto>> restApiGamesAllGet() {
        List<GameDto> gameDtos = gameUseCase.getAllGames()
                .stream()
                .map(gameDto -> mapper.map(gameDto, GameDto.class))
                .toList();

        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Category>> restApiGamesGameSlugCategoriesGet(String gameSlug) {


        return new ResponseEntity<>(List.of(new Category()), HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<RunDto>> restApiGamesGameSlugCategoryIdLeaderboardGet(String gameSlug, String categoryId) {
        Leaderboard leaderboard = leaderboardUseCase.getLeaderboard(gameSlug, categoryId);

        List<RunDto> runs = leaderboard.getRuns()
                .stream()
                .map(run -> mapper.map(run, RunDto.class))
                .toList();

        return new ResponseEntity<>(runs, HttpStatus.OK);
    }
}
