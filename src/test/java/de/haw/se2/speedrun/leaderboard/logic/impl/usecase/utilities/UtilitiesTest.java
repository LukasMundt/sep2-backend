package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.utilities;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class UtilitiesTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private Utilities utilities;

    @Test
    void getRun() {
    }

    @Test
    void getLeaderboardByRun() {
    }

    @Test
    void getLeaderboard() {
        Category category1 = new Category();
        category1.setCategoryId("Any%");
        category1.setLabel("Any%");
        Category category2 = new Category();
        category2.setCategoryId("Any%_Glitchless");
        category2.setLabel("Any%_Glitchless");

        Speedrunner speedrunner1 = new Speedrunner();
        speedrunner1.setUsername("speedrunner1");
        speedrunner1.setId(new UUID(123, 456));
        speedrunner1.setEmail("test1@test.com");
        speedrunner1.setRight(Right.SPEEDRUNNER);
        Speedrunner speedrunner2 = new Speedrunner();
        speedrunner2.setUsername("speedrunner2");
        speedrunner2.setId(new UUID(124, 457));
        speedrunner2.setEmail("test2@test.com");
        speedrunner2.setRight(Right.SPEEDRUNNER);
        Speedrunner speedrunner3 = new Speedrunner();
        speedrunner3.setUsername("speedrunner3");
        speedrunner3.setId(new UUID(125, 458));
        speedrunner3.setEmail("test3@test.com");
        speedrunner3.setRight(Right.SPEEDRUNNER);


        Run run1 = new Run();
        run1.setId(new UUID(123, 456));
        run1.setSpeedrunner(speedrunner1);
        run1.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        run1.setRuntime(new de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime(0,0,0,10));
        run1.setVerified(true);

        Run run2 = new Run();
        run2.setId(new UUID(124, 457));
        run2.setSpeedrunner(speedrunner2);
        run2.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        run2.setRuntime(new de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime(0,0,0,20));
        run2.setVerified(false);

        Run run3 = new Run();
        run3.setId(new UUID(125, 458));
        run3.setSpeedrunner(speedrunner3);
        run3.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        run3.setRuntime(new Runtime(0,0,0,30));
        run3.setVerified(true);

        List<Run> runs1 = new ArrayList<>(Arrays.asList(run1, run2, run3));
        List<Run> runs2 = new ArrayList<>(Collections.singletonList(run2));

        Leaderboard leaderboard1 = new Leaderboard();
        leaderboard1.setCategory(category1);
        leaderboard1.setRuns(runs1);
        Leaderboard leaderboard2 = new Leaderboard();
        leaderboard2.setCategory(category2);
        leaderboard2.setRuns(runs2);


        List<Leaderboard> leaderboards4 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard1, leaderboard2}));

        Game game4 = new Game();
        String slug4 = "game4";
        String name4 = "name4";
        game4.setSlug(slug4);
        game4.setName(name4);
        game4.setLeaderboards(leaderboards4);

        assertEquals(leaderboard1, utilities.getLeaderboard(game4, category1.getCategoryId()));
        assertEquals(leaderboard2, utilities.getLeaderboard(game4, category2.getCategoryId()));
    }

    @Test
    void getLeaderboardNotFound() {
        Category category1 = new Category();
        category1.setCategoryId("Any%");
        category1.setLabel("Any%");

        Game game1 = new Game();
        String slug1 = "game1";
        String name1 = "name1";
        game1.setSlug(slug1);
        game1.setName(name1);
        game1.setLeaderboards(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> utilities.getLeaderboard(game1, category1.getCategoryId()));
    }

    @Test
    void getGame() {
        Category category1 = new Category();
        category1.setCategoryId("Any%");
        category1.setLabel("Any%");
        Category category2 = new Category();
        category2.setCategoryId("Any%_Glitchless");
        category2.setLabel("Any%_Glitchless");
        Category category3 = new Category();
        category3.setCategoryId("All_Achievements");
        category3.setLabel("All_Achievements");
        Category category4 = new Category();
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

        assertEquals(game1, utilities.getGame(slug1));
        assertEquals(game2, utilities.getGame(slug2));
        assertEquals(game3, utilities.getGame(slug3));
        assertEquals(game4, utilities.getGame(slug4));
    }

    @Test
    void getGameGameNotFound() {
        Category category1= new Category();
        category1.setCategoryId("Any%");
        category1.setLabel("Any%");

        Leaderboard leaderboard1 = new Leaderboard();
        leaderboard1.setCategory(category1);

        Set<Game> games = new HashSet<>();
        Mockito.when(gameRepository.findBySlug(any(String.class))).thenAnswer(invocation -> {
            String slug = invocation.getArgument(0);
            Game foundGame = games.stream().filter(game -> game.getSlug().equals(slug)).findFirst().orElse(null);
            return Optional.ofNullable(foundGame);
        });

        Mockito.when(gameRepository.findGameByLeaderboardsContaining(leaderboard1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> utilities.getGame("nonexistentGame"));

        assertThrows(EntityNotFoundException.class, () -> utilities.getGame(leaderboard1));
    }

    @Test
    void testGetGame() {
        Category category1 = new Category();
        category1.setCategoryId("Any%");
        category1.setLabel("Any%");
        Category category2 = new Category();
        category2.setCategoryId("Any%_Glitchless");
        category2.setLabel("Any%_Glitchless");
        Category category3 = new Category();
        category3.setCategoryId("All_Achievements");
        category3.setLabel("All_Achievements");
        Category category4 = new Category();
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

        List<Leaderboard> leaderboards1 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard1}));
        List<Leaderboard> leaderboards2 = new ArrayList<>(Collections.singletonList(leaderboard3));
        List<Leaderboard> leaderboards3 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard2}));
        List<Leaderboard> leaderboards4 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard4}));

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
        Mockito.when(gameRepository.findGameByLeaderboardsContaining(any(Leaderboard.class))).thenAnswer(invocation -> {
            Leaderboard leaderboard = invocation.getArgument(0);
            Game foundGame = games.stream().filter(game -> game.getLeaderboards().contains(leaderboard)).findFirst().orElse(null);
            return Optional.ofNullable(foundGame);
        });

        assertEquals(game1, utilities.getGame(leaderboard1));
        assertEquals(game2, utilities.getGame(leaderboard3));
        assertEquals(game3, utilities.getGame(leaderboard2));
        assertEquals(game4, utilities.getGame(leaderboard4));
    }
}