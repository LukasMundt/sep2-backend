package de.haw.se2.speedrun;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class HealthController {

    private final Random rng;

    @Autowired
    public HealthController(SpeedrunnerRepository speedrunnerRepository, GameRepository gameRepository,
                            LeaderboardRepository leaderboardRepository, RunRepository runRepository) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.gameRepository = gameRepository;
        this.leaderboardRepository = leaderboardRepository;
        this.runRepository = runRepository;
        this.rng = new Random();
    }

    @GetMapping("/up")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("OK");
    }

    private final RunRepository runRepository;

    private final SpeedrunnerRepository speedrunnerRepository;

    private final GameRepository gameRepository;

    final LeaderboardRepository leaderboardRepository;

    @GetMapping("/insertSampleData")
    public ResponseEntity<String> insertSampleData() {
        addThreeUsers();
        addGames();
        return ResponseEntity.ok("OK");
    }

    private Run getEntry(){
        Run run = new Run();
        run.setDate(new Date());
        run.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));

        Optional<Speedrunner> spr1 = speedrunnerRepository.findByUsername("Speedrunner 1");
        Optional<Speedrunner> spr2 = speedrunnerRepository.findByUsername("Speedrunner 2");
        if(spr1.isEmpty() || spr2.isEmpty()){
            throw new EntityNotFoundException("Fetter fehler lol");
        }

        run.setSpeedrunner(rng.nextDouble() > 0.5 ? spr1.get() : spr2.get());
        run.setVerified(rng.nextDouble() < 0.7);
        runRepository.save(run);
        return run;
    }

    private Leaderboard addLeaderboards() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(new Category("any_percent", "Any %"));
        leaderboard.setRuns(List.of(getEntry(), getEntry(), getEntry(), getEntry(), getEntry(), getEntry(), getEntry()));
        leaderboardRepository.save(leaderboard);
        return leaderboard;
    }

    private void addGames() {
        Game game1 = new Game();
        game1.setName("Minecraft");
        game1.setSlug("minecraft");
        game1.setImageUrl("/games/minecraft.avif");
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