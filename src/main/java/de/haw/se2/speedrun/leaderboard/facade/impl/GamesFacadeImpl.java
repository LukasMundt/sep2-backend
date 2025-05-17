package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.facade.api.GamesFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.GameUseCase;
import de.haw.se2.speedrun.openapitools.model.GameDto;
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
    private final CustomizedModelMapper mapper;

    @Autowired
    public GamesFacadeImpl(CustomizedModelMapper mapper, GameUseCase gameUseCase) {
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
    public ResponseEntity<GameDto> restApiGamesGameSlugGet(String gameSlug) {
        Game game = gameUseCase.getGameBySlug(gameSlug);

        GameDto gameDto = mapper.map(game, GameDto.class);
        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }
}
