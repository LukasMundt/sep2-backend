package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;

import java.util.List;

public interface CategoryUseCase {
    void deleteCategory(String gameSlug, String categoryId);
    void addCategory(String gameSlug, Category category);
    List<Category> getCategories(String gameSlug);
}
