package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.Se2SpeedrunApplication;
import org.junit.jupiter.api.Test;


import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.common.api.pojo.RunReview;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Se2SpeedrunApplication.class)
class RunReviewUseCaseImplTest {

    private GameRepository gameRepository;
    private LeaderboardRepository leaderboardRepository;
    private RunRepository runRepository;
    private RunReviewUseCaseImpl runReviewUseCase;

    @BeforeEach
    void setUp() {
        gameRepository = mock(GameRepository.class);
        leaderboardRepository = mock(LeaderboardRepository.class);
        runRepository = mock(RunRepository.class);
        runReviewUseCase = new RunReviewUseCaseImpl(gameRepository, leaderboardRepository, runRepository);
    }

    @Test
    void getUnreviewedRuns_returnsUnverifiedRuns() {
        String gameSlug = "testgame";
        String categoryId = "any%";
        Game game = new Game();
        game.setLeaderboards(new ArrayList<>());
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(new Category(categoryId, "Any%"));
        Run run1 = new Run();
        run1.setId(UUID.randomUUID());
        run1.setVerified(false);
        run1.setDate(new Date(1000));
        Run run2 = new Run();
        run2.setId(UUID.randomUUID());
        run2.setVerified(true);
        run2.setDate(new Date(2000));
        leaderboard.setRuns(List.of(run1, run2));
        game.getLeaderboards().add(leaderboard);

        when(gameRepository.findBySlug(gameSlug)).thenReturn(Optional.of(game));

        List<RunReview> result = runReviewUseCase.getUnreviewedRuns(gameSlug, categoryId);

        assertEquals(1, result.size());
        assertEquals(run1.getId(), result.getFirst().getUuid());
    }

    @Test
    void getUnreviewedRuns_throwsException_whenGameNotFound() {
        when(gameRepository.findBySlug("notfound")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> runReviewUseCase.getUnreviewedRuns("notfound", "any%"));
    }

    @Test
    void getUnreviewedRuns_throwsException_whenLeaderboardNotFound() {
        Game game = new Game();
        game.setLeaderboards(Collections.emptyList());
        when(gameRepository.findBySlug("testgame")).thenReturn(Optional.of(game));
        assertThrows(EntityNotFoundException.class, () -> runReviewUseCase.getUnreviewedRuns("testgame", "any%"));
    }

    @Test
    void verifyRun_setsRunVerifiedAndRemovesPrevious() {
        UUID runId = UUID.randomUUID();
        UUID speedrunnerId = UUID.randomUUID();
        Run run = new Run();
        run.setId(runId);
        run.setVerified(false);
        Speedrunner speedrunner = new Speedrunner();
        speedrunner.setId(speedrunnerId);
        speedrunner.setNewFasterPlayers(new ArrayList<>());
        run.setSpeedrunner(speedrunner);
        run.setRuntime(new Runtime(java.time.Duration.ofSeconds(100)));
        Run previousRun = new Run();
        previousRun.setId(UUID.randomUUID());
        previousRun.setVerified(true);
        previousRun.setSpeedrunner(speedrunner);
        previousRun.setRuntime(new Runtime(java.time.Duration.ofSeconds(120)));
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setRuns(new ArrayList<>(List.of(previousRun, run)));
        when(runRepository.getRunById(runId)).thenReturn(Optional.of(run));
        when(leaderboardRepository.findLeaderboardByRunsContaining(run)).thenReturn(Optional.of(leaderboard));

        runReviewUseCase.verifyRun(runId);

        assertTrue(run.isVerified());
        assertFalse(leaderboard.getRuns().contains(previousRun));
    }

    @Test
    void verifyRun_throwsException_whenRunNotFound() {
        UUID runId = UUID.randomUUID();
        when(runRepository.getRunById(runId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> runReviewUseCase.verifyRun(runId));
    }

    @Test
    void verifyRun_throwsException_whenLeaderboardNotFound() {
        UUID runId = UUID.randomUUID();
        Run run = new Run();
        run.setId(runId);
        when(runRepository.getRunById(runId)).thenReturn(Optional.of(run));
        when(leaderboardRepository.findLeaderboardByRunsContaining(run)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> runReviewUseCase.verifyRun(runId));
    }
}