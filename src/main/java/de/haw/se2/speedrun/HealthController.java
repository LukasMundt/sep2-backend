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

import java.util.*;

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

    private final Random random = new Random();

    final LeaderboardRepository leaderboardRepository;

    @GetMapping("/insertSampleData")
    public ResponseEntity<String> insertSampleData() {
        addThreeUsers();
        addGames();
        return ResponseEntity.ok("OK");
    }

    private List<Run> getEntrys(){
        Run run1 = new Run();
        Run run2 = new Run();
        Run run3 = new Run();
        run1.setDate(new Date());
        run2.setDate(new Date());
        run3.setDate(new Date());
        run1.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));
        run2.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));
        run3.setRuntime(new Runtime(rng.nextInt(0, 4), rng.nextInt(0, 59), rng.nextInt(0, 59), rng.nextInt(0, 1000)));

        Optional<Speedrunner> fastJoe = speedrunnerRepository.findByUsername("Fast Joe");
        Optional<Speedrunner> slowBob = speedrunnerRepository.findByUsername("Slow Bob");
        Optional<Speedrunner> averageTom = speedrunnerRepository.findByUsername("Average Tom");
        if(fastJoe.isEmpty() || slowBob.isEmpty() || averageTom.isEmpty()) {
            throw new EntityNotFoundException("Fetter fehler lol");
        }

        run1.setSpeedrunner(fastJoe.get());
        run2.setSpeedrunner(slowBob.get());
        run3.setSpeedrunner(averageTom.get());
        run1.setVerified(true);
        run2.setVerified(true);
        run3.setVerified(true);

        //Randomly selects 2 or 3 runs to save
        List<Run> runs = new ArrayList<>(List.of(run1, run2, run3));
        Collections.shuffle(runs);

        if (random.nextBoolean()) {
            runRepository.saveAll(runs.subList(0, 2));
            return runs.subList(0, 2);
        } else {
            runRepository.saveAll(runs);
            return runs;
        }
    }

    private void addThreeUsers(){
        String passwordEncrypted = passwordEncoder.encode("123456Aa");

        Speedrunner speedrunner1 = new Speedrunner();
        Speedrunner speedrunner2 = new Speedrunner();
        Speedrunner speedrunner3 = new Speedrunner();
        Administrator admin = new Administrator();
        speedrunner1.setUsername("Fast Joe");
        speedrunner2.setUsername("Slow Bob");
        speedrunner3.setUsername("Average Tom");
        admin.setUsername("The admin");
        speedrunner1.setPassword(passwordEncrypted);
        speedrunner2.setPassword(passwordEncrypted);
        speedrunner3.setPassword(passwordEncrypted);
        admin.setPassword(passwordEncrypted);
        speedrunner1.setEmail("fastjoe@gmail.com");
        speedrunner2.setEmail("slowbob@gmail.com");
        speedrunner3.setEmail("averagetom@gmail.com");
        admin.setEmail("admin@admin.de");
        speedrunner1.setRight(Right.SPEEDRUNNER);
        speedrunner2.setRight(Right.SPEEDRUNNER);
        speedrunner3.setRight(Right.SPEEDRUNNER);
        admin.setRight(Right.ADMIN);
        speedrunnerRepository.saveAll(List.of(speedrunner1, speedrunner2, speedrunner3));
        administratorRepository.save(admin);
    }

    private Leaderboard addLeaderboards(String categoryId, String lable) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(new Category(categoryId, lable));
        leaderboard.setRuns(getEntrys());
        leaderboardRepository.save(leaderboard);
        return leaderboard;
    }

    private void addGames() {
        Game game0 = new Game();
        game0.setName("Minecraft");
        game0.setSlug("minecraft");
        game0.setImageUrl("/games/minecraft.avif");
        game0.setLeaderboards(List.of(
                addLeaderboards("any_percent", "Any %"),
                addLeaderboards("any_percent_glitchless", "Any % Glitchless"),
                addLeaderboards("all_achievements", "All Achievements")
        ));

        Game game1 = new Game();
        game1.setName("Cuphead");
        game1.setSlug("cuphead");
        game1.setImageUrl("/games/cuphead.png");
        game1.setLeaderboards(List.of(
                addLeaderboards("any_percent", "Any %"),
                addLeaderboards("low_percent", "Low %"),
                addLeaderboards("all_flags", "All Flags"),
                addLeaderboards("full_clear", "Full Clear"),
                addLeaderboards("no_hit", "No Hit")
        ));

        Game game2 = new Game();
        game2.setName("Mario Kart 8 Deluxe");
        game2.setSlug("mario-kart-8-deluxe");
        game2.setImageUrl("/games/marioKart8Deluxe.png");
        game2.setLeaderboards(List.of(
                addLeaderboards("48_tracks", "48 Tracks"),
                addLeaderboards("48_tracks_dlc", "48 Tracks DLC"),
                addLeaderboards("96_tracks", "96 Tracks"),
                addLeaderboards("bonus_tracks", "Bonus Tracks")
        ));

        Game game3 = new Game();
        game3.setName("Hollow Knight");
        game3.setSlug("hollow-knight");
        game3.setImageUrl("/games/hollowKnight.png");
        game3.setLeaderboards(List.of(
                addLeaderboards("any_percent", "Any %"),
                addLeaderboards("all_skills", "All Skills"),
                addLeaderboards("112_percent", "112%"),
                addLeaderboards("true_ending", "True Ending"),
                addLeaderboards("low_percent", "Low %")
        ));

        Game game4 = new Game();
        game4.setName("Portal 2");
        game4.setSlug("portal-2");
        game4.setImageUrl("/games/portal2.png");
        game4.setLeaderboards(List.of(
                addLeaderboards("single_player", "Single Player"),
                addLeaderboards("coop", "Coop"),
                addLeaderboards("solo_coop", "Solo Co-Op"),
                addLeaderboards("least_portals", "Least Portals")

        ));

        Game game5 = new Game();
        game5.setName("The Legend of Zelda: Breath of the Wild");
        game5.setSlug("the-legend-of-zelda-breath-of-the-wild");
        game5.setImageUrl("/games/tlozbotw.png");
        game5.setLeaderboards(List.of(
                addLeaderboards("any_percent", "Any %"),
                addLeaderboards("all_main_quests", "All Main Quests"),
                addLeaderboards("all_dungeons", "All Dungeons"),
                addLeaderboards("all_shrines", "All Shrines"),
                addLeaderboards("100_percent", "100%")
        ));

        Game game6 = new Game();
        game6.setName("The Simpsons: Hit & Run");
        game6.setSlug("the-simpsons-hit-and-run");
        game6.setImageUrl("/games/theSimpsonsHitAndRun.png");
        game6.setLeaderboards(List.of(
                addLeaderboards("any_percent", "Any %"),
                addLeaderboards("100_percent", "100%"),
                addLeaderboards("all_story_missions", "All Story Missions"),
                addLeaderboards("no_mission_warps", "No Mission Warps")
        ));

        Game game7 = new Game();
        game7.setName("Super Mario 64");
        game7.setSlug("super-mario-64");
        game7.setImageUrl("/games/superMario64.png");
        game7.setLeaderboards(List.of(
                addLeaderboards("120_star", "120 Star"),
                addLeaderboards("70_star", "70 Star"),
                addLeaderboards("16_star", "16 Star"),
                addLeaderboards("1_star", "1 Star"),
                addLeaderboards("0_star", "0 Star")
        ));

        Game game8 = new Game();
        game8.setName("Dark Souls III");
        game8.setSlug("dark-souls-3");
        game8.setImageUrl("/games/darkSouls3.png");
        game8.setLeaderboards(List.of(
                addLeaderboards("any_percent", "Any %"),
                addLeaderboards("all_bosses", "All Bosses"),
                addLeaderboards("no_hit", "No Hit"),
                addLeaderboards("no_death", "No Death")
        ));

        Game game9 = new Game();
        game9.setName("Getting Over It with Bennett Foddy");
        game9.setSlug("getting-over-it-with-bennett-foddy");
        game9.setImageUrl("/games/goiwbf.png");
        game9.setLeaderboards(List.of(
                addLeaderboards("glitchless", "Glitchless"),
                addLeaderboards("snake", "Snake")
        ));

        gameRepository.saveAll(List.of(game0, game1, game2, game3, game4, game5, game6, game7, game8, game9));
    }
}