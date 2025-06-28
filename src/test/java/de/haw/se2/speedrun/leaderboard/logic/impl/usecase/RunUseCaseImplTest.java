package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RunUseCaseImplTest {

    @Mock
    private SpeedrunnerRepository speedrunnerRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private RunUseCaseImpl runUseCase;

    private Category category1;
    private Category category2;

    private Game game4;
    private List<Run> runs1;
    private List<Run> runs2;

    private Run run1;
    private Run run3;

    private Speedrunner speedrunner1;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setCategoryId("Any%");
        category1.setLabel("Any%");
        category2 = new Category();
        category2.setCategoryId("Any%_Glitchless");
        category2.setLabel("Any%_Glitchless");

        speedrunner1 = new Speedrunner();
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


        run1 = new Run();
        run1.setId(new UUID(123, 456));
        run1.setSpeedrunner(speedrunner1);
        run1.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        run1.setRuntime(new Runtime(0,0,0,10));
        run1.setVerified(true);

        Run run2 = new Run();
        run2.setId(new UUID(124, 457));
        run2.setSpeedrunner(speedrunner2);
        run2.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        run2.setRuntime(new Runtime(0,0,0,20));
        run2.setVerified(false);

        run3 = new Run();
        run3.setId(new UUID(125, 458));
        run3.setSpeedrunner(speedrunner3);
        run3.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        run3.setRuntime(new Runtime(0,0,0,30));
        run3.setVerified(true);

        runs1 = new ArrayList<>(Arrays.asList(run1, run2, run3));
        runs2 = new ArrayList<>(Collections.singletonList(run2));

        Leaderboard leaderboard1 = new Leaderboard();
        leaderboard1.setCategory(category1);
        leaderboard1.setRuns(runs1);
        Leaderboard leaderboard2 = new Leaderboard();
        leaderboard2.setCategory(category2);
        leaderboard2.setRuns(runs2);


        List<Leaderboard> leaderboards4 = new ArrayList<>(Arrays.asList(new Leaderboard[]{leaderboard1, leaderboard2}));

        game4 = new Game();
        String slug4 = "game4";
        String name4 = "name4";
        game4.setSlug(slug4);
        game4.setName(name4);
        game4.setLeaderboards(leaderboards4);

        when(gameRepository.findBySlug(any(String.class))).thenAnswer(invocation -> {
            String slug = invocation.getArgument(0);
            if (slug.equals(game4.getSlug())) {
                return Optional.of(game4);
            }
            return Optional.empty();
        });
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getVerifiedLeaderboardRuns() {
        List<Run> expectedRuns1 = new ArrayList<>(Arrays.asList(run1, run3));
        assertEquals(expectedRuns1, runUseCase.getVerifiedLeaderboardRuns("game4", "Any%"));

        List<Run> expectedRuns2 = new ArrayList<>();
        assertEquals(expectedRuns2, runUseCase.getVerifiedLeaderboardRuns("game4", "Any%_Glitchless"));
    }

    @Test
    void getVerifiedLeaderboardRunsWithInvalidCategory() {
        assertThrows(EntityNotFoundException.class, () -> {
            runUseCase.getVerifiedLeaderboardRuns("game4", "InvalidCategory");
        }
        );
    }

    @Test
    void addUnverifiedRun() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(speedrunner1.getEmail());

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        Mockito.when(speedrunnerRepository.findByEmail(any(String.class))).thenAnswer(invocation -> {
            String email = invocation.getArgument(0);
            if (email.equals(speedrunner1.getEmail())) {
                return Optional.of(speedrunner1);
            }
            return Optional.empty();
        });
        Runtime newRuntime = new Runtime(0, 0, 0, 5);
        assertFalse(runs1.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && run.getRuntime().equals(newRuntime)));
        assertFalse(runs1.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && !run.isVerified()));
        runUseCase.addUnverifiedRun("game4", category1.getCategoryId(), new Date(), "https://www.youtube.com/watch?v=dQw4w9WgXcQ", newRuntime);
        assertTrue(runs1.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && run.getRuntime().equals(newRuntime)));
        assertTrue(runs1.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && !run.isVerified()));

        assertFalse(runs2.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && run.getRuntime().equals(newRuntime)));
        assertFalse(runs2.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && !run.isVerified()));
        runUseCase.addUnverifiedRun("game4", category2.getCategoryId(), new Date(), "https://www.youtube.com/watch?v=dQw4w9WgXcQ", newRuntime);
        assertTrue(runs2.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && run.getRuntime().equals(newRuntime)));
        assertTrue(runs2.stream().anyMatch(run -> run.getSpeedrunner().equals(speedrunner1) && !run.isVerified()));
    }

    @Test
    void addUnverifiedRunSpeedrunnerNotFound() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(speedrunner1.getEmail());

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        Mockito.when(speedrunnerRepository.findByEmail(any(String.class))).thenAnswer(invocation -> Optional.empty());

        Runtime newRuntime = new Runtime(0, 0, 0, 5);
        assertThrows(EntityNotFoundException.class, () -> {
            runUseCase.addUnverifiedRun("game4", category1.getCategoryId(), new Date(), "https://www.youtube.com/watch?v=dQw4w9WgXcQ", newRuntime);
        });
    }

    @Test
    void addUnverifiedWithLowerTime() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(speedrunner1.getEmail());

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        Mockito.when(speedrunnerRepository.findByEmail(any(String.class))).thenAnswer(invocation -> {
            String email = invocation.getArgument(0);
            if (email.equals(speedrunner1.getEmail())) {
                return Optional.of(speedrunner1);
            }
            return Optional.empty();
        });

        Runtime newRuntime = new Runtime(0, 0, 0, 15);
        assertThrows(NotAcceptableStatusException.class, () -> {
            runUseCase.addUnverifiedRun("game4", category1.getCategoryId(), new Date(), "https://www.youtube.com/watch?v=dQw4w9WgXcQ", newRuntime);
        });
        run1.setVerified(false);
        assertThrows(NotAcceptableStatusException.class, () -> {
            runUseCase.addUnverifiedRun("game4", category1.getCategoryId(), new Date(), "https://www.youtube.com/watch?v=dQw4w9WgXcQ", newRuntime);
        });
    }

    @Test
    void gameSlugNotFound() {
        String gameSlug = "nonExistentGame";

        assertThrows(EntityNotFoundException.class, () -> {
            runUseCase.getVerifiedLeaderboardRuns(gameSlug, "Any%");
        });

        assertThrows(EntityNotFoundException.class, () -> {
            runUseCase.addUnverifiedRun(gameSlug,"Any%", new Date(), "https://www.youtube.com/watch?v=dQw4w9WgXcQ", new Runtime(0,0,0,0));}
        );
    }
}