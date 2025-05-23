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
import de.haw.se2.speedrun.user.dataaccess.api.entity.Administrator;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public HealthController(SpeedrunnerRepository speedrunnerRepository, AdministratorRepository administratorRepository,
                            GameRepository gameRepository, LeaderboardRepository leaderboardRepository,
                            RunRepository runRepository, PasswordEncoder passwordEncoder) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.administratorRepository = administratorRepository;
        this.gameRepository = gameRepository;
        this.leaderboardRepository = leaderboardRepository;
        this.runRepository = runRepository;
        this.rng = new Random();
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/up")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("OK");
    }

    private final RunRepository runRepository;

    private final SpeedrunnerRepository speedrunnerRepository;
    private final AdministratorRepository administratorRepository;

    private final PasswordEncoder passwordEncoder;

    private final GameRepository gameRepository;

    final LeaderboardRepository leaderboardRepository;

    @GetMapping("/insertSampleData")
    public ResponseEntity<String> insertSampleData() {
        addThreeUsers();
        addGames();
        return ResponseEntity.ok("OK");
    }

    //Scary deletion block. Use at own risk ( ⚆ _ ⚆ )
    public void deleteSampleData() {
        runRepository.deleteAll();
        leaderboardRepository.deleteAll();
        gameRepository.deleteAll();
        administratorRepository.deleteAll();
        speedrunnerRepository.deleteAll();
    }

    private List<Run> getEntrys(){
        Run run1 = new Run();
        Run run2 = new Run();
        run1.setDate(new Date());
        run2.setDate(new Date());
        run1.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));
        run2.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));

        Optional<Speedrunner> fastJoe = speedrunnerRepository.findByUsername("Fast Joe");
        Optional<Speedrunner> slowBob = speedrunnerRepository.findByUsername("Slow Bob");
        if(fastJoe.isEmpty() || slowBob.isEmpty()){
            throw new EntityNotFoundException("Fetter fehler lol");
        }

        run1.setSpeedrunner(fastJoe.get());
        run2.setSpeedrunner(slowBob.get());
        run1.setVerified(true);
        run2.setVerified(true);
        runRepository.saveAll(List.of(run1, run2));
        return runRepository.findAll();
    }

    private Leaderboard addLeaderboards() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(new Category("any_percent", "Any %"));
        leaderboard.setRuns(getEntrys());
        leaderboardRepository.save(leaderboard);
        return leaderboard;
    }

    private void addGames() {
        Game game1 = new Game();
        game1.setName("Minecraft");
        game1.setSlug("minecraft");
        game1.setImageUrl("/games/minecraft.avif");
        game1.setLeaderboards(leaderboardRepository.findAll());
        gameRepository.save(game1);
    }

    private void addThreeUsers(){
        String passwordEncrypted = passwordEncoder.encode("123456Aa");

        Speedrunner speedrunner1 = new Speedrunner();
        Speedrunner speedrunner2 = new Speedrunner();
        Administrator admin = new Administrator();
        speedrunner1.setUsername("Fast Joe");
        speedrunner2.setUsername("Slow Bob");
        admin.setUsername("The admin");
        speedrunner1.setPassword(passwordEncrypted);
        speedrunner2.setPassword(passwordEncrypted);
        admin.setPassword(passwordEncrypted);
        speedrunner1.setEmail("fastjoe@gmail.com");
        speedrunner2.setEmail("slowbob@gmail.com");
        admin.setEmail("admin@admin.de");
        speedrunner1.setRight(Right.SPEEDRUNNER);
        speedrunner2.setRight(Right.SPEEDRUNNER);
        admin.setRight(Right.ADMIN);
        speedrunnerRepository.saveAll(List.of(speedrunner1, speedrunner2));
        administratorRepository.save(admin);
    }
}