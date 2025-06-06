package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.facade.api.CategoriesFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.CategoryUseCase;
import de.haw.se2.speedrun.openapitools.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
@RequiredArgsConstructor
public class CategoriesFacadeImpl implements CategoriesFacade {

    private final CustomizedModelMapper mapper;
    private final CategoryUseCase categoryUseCase;

    @Override
    public ResponseEntity<List<Category>> restApiGamesGameSlugCategoriesGet(String gameSlug) {
        List<Category> categories = categoryUseCase.getCategories(gameSlug)
                .stream()
                .map(c -> mapper.map(c, Category.class))
                .toList();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> restApiGamesGameSlugCategoriesPost(String gameSlug, Category category) {
        de.haw.se2.speedrun.leaderboard.common.api.datatype.Category convertedCategory = mapper.map(category, de.haw.se2.speedrun.leaderboard.common.api.datatype.Category.class);

        categoryUseCase.addCategory(gameSlug, convertedCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> restApiGamesGameSlugCategoryIdDelete(String gameSlug, String categoryId) {
        categoryUseCase.deleteCategory(gameSlug, categoryId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
