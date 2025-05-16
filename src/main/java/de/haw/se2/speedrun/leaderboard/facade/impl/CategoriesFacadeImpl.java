package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.facade.api.CategoriesFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.GameUseCase;
import de.haw.se2.speedrun.openapitools.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class CategoriesFacadeImpl implements CategoriesFacade {

    private final CustomizedModelMapper mapper;
    private final GameUseCase gameUseCase;

    @Autowired
    public CategoriesFacadeImpl(CustomizedModelMapper modelMapper, GameUseCase gameUseCase) {
        this.mapper = modelMapper;
        this.gameUseCase = gameUseCase;
    }

    @Override
    public ResponseEntity<List<Category>> restApiGamesGameSlugCategoriesGet(String gameSlug) {
        List<Category> categories = gameUseCase.getAllCategoriesOfGame(gameSlug)
                .stream()
                .map(c -> mapper.map(c, Category.class))
                .toList();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> restApiGamesGameSlugCategoriesPost(String gameSlug, Category category) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> restApiGamesGameSlugCategoryIdDelete(String gameSlug, String categoryId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
