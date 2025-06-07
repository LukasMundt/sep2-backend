package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GameUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameUseCaseImpl gameUseCase;

    private Set<Game> games;

    @BeforeEach
    void setUp() {
        Game game1 = new Game();
        String slug1 = "game1";
        String name1 = "name1";
        game1.setSlug(slug1);
        game1.setName(name1);

        Game game2 = new Game();
        String slug2 = "game2";
        String name2 = "name2";
        game2.setSlug(slug2);
        game2.setName(name2);

        Game game3 = new Game();
        String slug3 = "game3";
        String name3 = "name3";
        game3.setSlug(slug3);
        game3.setName(name3);
        Game game4 = new Game();
        String slug4 = "game4";
        String name4 = "name4";
        game4.setSlug(slug4);
        game4.setName(name4);

        games = new HashSet<>(Arrays.asList(game1, game2, game3, game4));
    }

    @Test
    void getAllGames() {
        Mockito.when(gameRepository.findAll()).thenReturn(new ArrayList<>(games));
        List<Game> games = gameUseCase.getAllGames();
        assertNotNull(games);
        assertEquals(4, games.size());
        assertTrue(games.stream().anyMatch(game -> game.getSlug().equals("game1")));
        assertTrue(games.stream().anyMatch(game -> game.getSlug().equals("game2")));
        assertTrue(games.stream().anyMatch(game -> game.getSlug().equals("game3")));
        assertTrue(games.stream().anyMatch(game -> game.getSlug().equals("game4")));
    }

    @Test
    void getGameBySlug() {
        Mockito.when(gameRepository.findBySlug(any(String.class))).thenAnswer(invocation -> {
            String slug = invocation.getArgument(0);
            Game foundGame = games.stream().filter(game -> game.getSlug().equals(slug)).findFirst().orElse(null);
            return Optional.ofNullable(foundGame);
        });

        String gameSlug = "game1";
        Game game = gameUseCase.getGameBySlug(gameSlug);
        assertNotNull(game);
        assertEquals(gameSlug, game.getSlug());
        assertEquals("name1", game.getName());

        gameSlug = "game2";
        game = gameUseCase.getGameBySlug(gameSlug);
        assertNotNull(game);
        assertEquals(gameSlug, game.getSlug());
        assertEquals("name2", game.getName());

        gameSlug = "game3";
        game = gameUseCase.getGameBySlug(gameSlug);
        assertNotNull(game);
        assertEquals(gameSlug, game.getSlug());
        assertEquals("name3", game.getName());

        gameSlug = "game4";
        game = gameUseCase.getGameBySlug(gameSlug);
        assertNotNull(game);
        assertEquals(gameSlug, game.getSlug());
        assertEquals("name4", game.getName());

    }

    @Test
    void getGameBySlugThrowsExceptionForNonExistingGame() {
        Mockito.when(gameRepository.findBySlug(any(String.class))).thenAnswer(invocation -> {
            String slug = invocation.getArgument(0);
            Game foundGame = games.stream().filter(game -> game.getSlug().equals(slug)).findFirst().orElse(null);
            return Optional.ofNullable(foundGame);
        });
        String nonExistingSlug = "non-existing-game";
        assertThrows(EntityNotFoundException.class, () -> gameUseCase.getGameBySlug(nonExistingSlug));
    }
}