package de.haw.se2.speedrun;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Entry;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardEntryRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class HealthController {

    private final Random rng;

    @Autowired
    public HealthController(SpeedrunnerRepository speedrunnerRepository, GameRepository gameRepository,
                            LeaderboardRepository leaderboardRepository, LeaderboardEntryRepository leaderboardEntryRepository) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.gameRepository = gameRepository;
        this.leaderboardRepository = leaderboardRepository;
        this.leaderboardEntryRepository = leaderboardEntryRepository;
        this.rng = new Random();
    }

    @GetMapping("/up")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("OK");
    }

    private final LeaderboardEntryRepository leaderboardEntryRepository;

    private final SpeedrunnerRepository speedrunnerRepository;

    private final GameRepository gameRepository;

    final
    LeaderboardRepository leaderboardRepository;

    @GetMapping("/insertSampleData")
    public ResponseEntity<String> test() {
        addThreeUsers();
        addGames();
        return ResponseEntity.ok("OK");
    }

    private Entry getEntry(){
        Entry entry = new Entry();
        entry.setDate(new Date());
        entry.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));
        entry.setSpeedrunner(rng.nextDouble() > 0.5 ? speedrunnerRepository.findByUsername("Speedrunner 1").get() : speedrunnerRepository.findByUsername("Speedrunner 2").get());
        leaderboardEntryRepository.save(entry);
        return entry;
    }

    private Leaderboard addLeaderboards() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory("ANY_PERCENT");
        leaderboard.setRuns(List.of(getEntry(), getEntry(), getEntry()));
        leaderboardRepository.save(leaderboard);
        return leaderboard;
    }

    private void addGames() {
        Game game1 = new Game();
        game1.setName("Minecraft");
        game1.setSlug("minecraft");
        game1.setImageUrl("testurl");
        game1.setLeaderboards(List.of(addLeaderboards()));
        gameRepository.save(game1);
    }

    private void addThreeUsers(){
        Speedrunner speedrunner1 = new Speedrunner();
        Speedrunner speedrunner2 = new Speedrunner();
        Speedrunner speedrunner3 = new Speedrunner();
        speedrunner1.setUsername("Speedrunner 1");
        speedrunner2.setUsername("Speedrunner 2");
        speedrunner3.setUsername("Speedrunner 3");
        speedrunner1.setPassword("speedrunner1");
        speedrunner2.setPassword("speedrunner2");
        speedrunner3.setPassword("speedrunner3");
        speedrunner1.setEmail("speedrunner1@gmail.com");
        speedrunner2.setEmail("speedrunner2@gmail.com");
        speedrunner3.setEmail("speedrunner3@gmail.com");
        speedrunner1.setRight(Right.SPEEDRUNNER);
        speedrunner2.setRight(Right.SPEEDRUNNER);
        speedrunner3.setRight(Right.SPEEDRUNNER);
        speedrunnerRepository.saveAll(List.of(speedrunner1, speedrunner2, speedrunner3));
    }
}