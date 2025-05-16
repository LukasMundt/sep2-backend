package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.CategoryUseCase;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryUseCaseImpl implements CategoryUseCase {

    private final GameRepository gameRepository;

    @Autowired
    public CategoryUseCaseImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Category> getCategories(String gameSlug) {
        Game game = getGame(gameSlug);

        return game.getLeaderboards()
                .stream()
                .map(Leaderboard::getCategory)
                .toList();
    }

    @Transactional
    @Override
    public void addCategory(String gameSlug, Category category) {
        Game game = getGame(gameSlug);

        if(game.getLeaderboards().stream().anyMatch(leaderboard -> leaderboard.getCategory().getCategoryId().equalsIgnoreCase(category.getCategoryId()))) {
            throw new EntityExistsException("Category with id " + category.getCategoryId() + " already exists");
        }

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(category);
        leaderboard.setRuns(new ArrayList<>());

        game.getLeaderboards()
                .add(leaderboard);
    }

    @Transactional
    @Override
    public void deleteCategory(String gameSlug, String categoryId) {
        Game game = getGame(gameSlug);
        Optional<Leaderboard> leaderboard = game.getLeaderboards()
                .stream()
                .filter(l -> l.getCategory().getCategoryId().equalsIgnoreCase(categoryId))
                .findFirst();

        game.getLeaderboards().remove(leaderboard.orElseThrow(EntityNotFoundException::new));
    }

    private Game getGame(String gameSlug) {
        Optional<Game> game = gameRepository.findBySlug(gameSlug);
        if (game.isEmpty()) {
            throw new EntityNotFoundException(String.format("Game '%s' not found", gameSlug));
        }

        return game.get();
    }
}
