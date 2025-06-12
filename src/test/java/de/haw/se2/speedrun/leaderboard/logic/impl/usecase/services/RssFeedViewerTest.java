package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services;


import com.rometools.rome.io.FeedException;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.leaderboard.facade.api.BaseTest;
import de.haw.se2.speedrun.leaderboard.logic.impl.usecase.utilities.Utilities;
import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;


import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RssFeedViewerTest extends BaseTest {

    private SpeedrunnerRepository speedrunnerRepository;
    private RunRepository runRepository;
    private LeaderboardRepository leaderboardRepository;
    private GameRepository gameRepository;
    private RssFeedViewer rssFeedViewer;

    @Autowired
    public RssFeedViewerTest(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository, GameRepository gameRepository, LeaderboardRepository leaderboardRepository, RunRepository runRepository, PasswordEncoder passwordEncoder, RssFeedViewer rssFeedViewer) {
        super(speedrunnerRepository, administratorRepository, gameRepository, leaderboardRepository, runRepository, passwordEncoder);
        this.rssFeedViewer = rssFeedViewer;
    }

    @BeforeEach
    void setUp() {
        speedrunnerRepository = mock(SpeedrunnerRepository.class);
        runRepository = mock(RunRepository.class);
        leaderboardRepository = mock(LeaderboardRepository.class);
        gameRepository = mock(GameRepository.class);
    }
    @Test
    void buildFeed_returnsFeed_whenRunsExist() throws FeedException, IOException {
        UUID speedrunnerId = UUID.randomUUID();
        Speedrunner speedrunner = new Speedrunner();
        speedrunner.setId(speedrunnerId);
        speedrunner.setUsername("TestUser");

        Run fasterRun = new Run();
        fasterRun.setId(UUID.randomUUID());

        FasterInformation fasterInfo = new FasterInformation(speedrunnerId, fasterRun.getId());
        speedrunner.setNewFasterPlayers(List.of(fasterInfo));


        Speedrunner otherRunner = new Speedrunner();
        otherRunner.setId(UUID.randomUUID());
        otherRunner.setUsername("FasterUser");
        fasterRun.setSpeedrunner(otherRunner);
        fasterRun.setRuntime(new Runtime(java.time.Duration.ofSeconds(100)));

        Run ownRun = new Run();
        ownRun.setId(UUID.randomUUID());
        ownRun.setSpeedrunner(speedrunner);
        ownRun.setRuntime(new Runtime(java.time.Duration.ofSeconds(120)));

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(new Category("Any%","Any%"));
        leaderboard.setRuns(List.of(fasterRun, ownRun));

        Game game = new Game();
        game.setName("TestGame");
        game.setLeaderboards(List.of(leaderboard));

        when(speedrunnerRepository.findById(speedrunnerId)).thenReturn(Optional.of(speedrunner));
        when(runRepository.findAllById(any())).thenReturn(List.of(fasterRun));
        when(leaderboardRepository.findLeaderboardByRunsContaining(fasterRun)).thenReturn(Optional.of(leaderboard));
        when(gameRepository.findGameByLeaderboardsContaining(leaderboard)).thenReturn(Optional.of(game));

        String feed = rssFeedViewer.buildFeed(speedrunnerId.toString());

        assertNotNull(feed);
        assertTrue(feed.contains("Du wurdest überboten!"));
        assertTrue(feed.contains("TestGame"));
        assertTrue(feed.contains("TestUser"));
        assertTrue(feed.contains("FasterUser"));
    }

    @Test
    void buildFeed_returnsEmptyFeed_whenNoRuns() throws FeedException, IOException {
        UUID speedrunnerId = UUID.randomUUID();
        Speedrunner speedrunner = new Speedrunner();
        speedrunner.setId(speedrunnerId);
        speedrunner.setNewFasterPlayers(Collections.emptyList());

        when(speedrunnerRepository.findById(speedrunnerId)).thenReturn(Optional.of(speedrunner));
        when(runRepository.findAllById(any())).thenReturn(Collections.emptyList());

        String feed = rssFeedViewer.buildFeed(speedrunnerId.toString());

        assertNotNull(feed);
        assertTrue(feed.contains("Updates about your Runs"));
    }

    @Test
    void buildFeed_throwsException_whenSpeedrunnerNotFound() {
        UUID speedrunnerId = UUID.randomUUID();
        when(speedrunnerRepository.findById(speedrunnerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> rssFeedViewer.buildFeed(speedrunnerId.toString()));
    }

    @Test
    void buildFeed_throwsException_whenSpeedrunnerIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> rssFeedViewer.buildFeed(null));
    }

    @Test
    void buildFeed_throwsException_whenSpeedrunnerIdIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> rssFeedViewer.buildFeed(""));
    }
}