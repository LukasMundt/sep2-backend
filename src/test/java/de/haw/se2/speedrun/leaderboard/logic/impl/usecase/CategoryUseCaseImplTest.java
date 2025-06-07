package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private CategoryUseCaseImpl categoryUseCase;

    private Category category1;
    private Category category2;
    private Category category3;
    private Category category4;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setCategoryId("Any%");
        category1.setLabel("Any%");
        category2 = new Category();
        category2.setCategoryId("Any%_Glitchless");
        category2.setLabel("Any%_Glitchless");
        category3 = new Category();
        category3.setCategoryId("All_Achievements");
        category3.setLabel("All_Achievements");
        category4 = new Category();
        category4.setCategoryId("Randomizer");
        category4.setLabel("Randomizer");

        Leaderboard leaderboard1 = new Leaderboard();
        leaderboard1.setCategory(category1);
        Leaderboard leaderboard2 = new Leaderboard();
        leaderboard2.setCategory(category2);
        Leaderboard leaderboard3 = new Leaderboard();
        leaderboard3.setCategory(category3);
        Leaderboard leaderboard4 = new Leaderboard();
        leaderboard4.setCategory(category4);

        List<Leaderboard> leaderboards1 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard1, leaderboard2}));
        List<Leaderboard> leaderboards2 = new ArrayList<>(Collections.singletonList(leaderboard3));
        List<Leaderboard> leaderboards3 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard4, leaderboard1, leaderboard2}));
        List<Leaderboard> leaderboards4 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard3, leaderboard4, leaderboard1, leaderboard2}));

        Game game1 = new Game();
        String slug1 = "game1";
        String name1 = "name1";
        game1.setSlug(slug1);
        game1.setName(name1);
        game1.setLeaderboards(leaderboards1);

        Game game2 = new Game();
        String slug2 = "game2";
        String name2 = "name2";
        game2.setSlug(slug2);
        game2.setName(name2);
        game2.setLeaderboards(leaderboards2);

        Game game3 = new Game();
        String slug3 = "game3";
        String name3 = "name3";
        game3.setSlug(slug3);
        game3.setName(name3);
        game3.setLeaderboards(leaderboards3);

        Game game4 = new Game();
        String slug4 = "game4";
        String name4 = "name4";
        game4.setSlug(slug4);
        game4.setName(name4);
        game4.setLeaderboards(leaderboards4);

        Set<Game> games = new HashSet<>(Arrays.asList(game1, game2, game3, game4));
        Mockito.when(gameRepository.findBySlug(any(String.class))).thenAnswer(invocation -> {
            String slug = invocation.getArgument(0);
            Game foundGame = games.stream().filter(game -> game.getSlug().equals(slug)).findFirst().orElse(null);
            return Optional.ofNullable(foundGame);
        });
    }

    @Test
    void getCategories() {
        List<Category> expected1 = new ArrayList<>(Arrays.asList(category1, category2));
        assertEquals(expected1, categoryUseCase.getCategories("game1"));

        List<Category> expected2 = new ArrayList<>(Collections.singletonList(category3));
        assertEquals(expected2, categoryUseCase.getCategories("game2"));

        List<Category> expected3 = new ArrayList<>(Arrays.asList(category4, category1, category2));
        assertEquals(expected3, categoryUseCase.getCategories("game3"));

        List<Category> expected4 = new ArrayList<>(Arrays.asList(category3, category4, category1, category2));
        assertEquals(expected4, categoryUseCase.getCategories("game4"));
    }

    @Test
    void addCategory() {
        List<Category> expected2 = new ArrayList<>(Collections.singletonList(category3));
        assertEquals(expected2, categoryUseCase.getCategories("game2"));
        categoryUseCase.addCategory("game2", category1);
        expected2.add(category1);
        assertEquals(expected2, categoryUseCase.getCategories("game2"));
        categoryUseCase.addCategory("game2", category2);
        expected2.add(category2);
        assertEquals(expected2, categoryUseCase.getCategories("game2"));
        categoryUseCase.addCategory("game2", category4);
        expected2.add(category4);

        List<Category> expected3 = new ArrayList<>(Arrays.asList(category4, category1, category2));
        assertEquals(expected3, categoryUseCase.getCategories("game3"));
        categoryUseCase.addCategory("game3", category3);
        expected3.add(category3);
        assertEquals(expected3, categoryUseCase.getCategories("game3"));
    }

    @Test
    void addCategoryAlreadyExists() {
        assertThrows(EntityExistsException.class, () -> categoryUseCase.addCategory("game2", category3));

        assertThrows(EntityExistsException.class, () -> categoryUseCase.addCategory("game4", category1));
    }

    @Test
    void deleteCategory() {
        List<Category> expected4 = new ArrayList<>(Arrays.asList(category3, category4, category1, category2));
        assertEquals(expected4, categoryUseCase.getCategories("game4"));

        categoryUseCase.deleteCategory("game4", category1.getCategoryId());
        expected4.remove(category1);
        assertEquals(expected4, categoryUseCase.getCategories("game4"));

        categoryUseCase.deleteCategory("game4", category2.getCategoryId());
        expected4.remove(category2);
        assertEquals(expected4, categoryUseCase.getCategories("game4"));

        categoryUseCase.deleteCategory("game4", category3.getCategoryId());
        expected4.remove(category3);
        assertEquals(expected4, categoryUseCase.getCategories("game4"));

        categoryUseCase.deleteCategory("game4", category4.getCategoryId());
        expected4.remove(category4);
        assertEquals(expected4, categoryUseCase.getCategories("game4"));

    }

    @Test
    void gameNotFound() {
        assertThrows(EntityNotFoundException.class, () -> categoryUseCase.addCategory("nonexistentGame", category1));

        assertThrows(EntityNotFoundException.class, () -> categoryUseCase.getCategories("nonexistentGame"));

        assertThrows(EntityNotFoundException.class, () -> categoryUseCase.deleteCategory("nonexistentGame", category1.getCategoryId()));
    }
}