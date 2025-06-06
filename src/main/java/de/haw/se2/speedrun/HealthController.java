package de.haw.se2.speedrun;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Category;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.user.common.api.datatype.Right;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Administrator;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private static final String URL = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    private final Random rng = new Random();

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

    private List<Run> getEntrys(){
        Run run1 = new Run();
        Run run2 = new Run();
        Run run3 = new Run();
        run1.setVideoLink(URL);
        run2.setVideoLink(URL);
        run3.setVideoLink(URL);
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

        if (rng.nextBoolean()) {
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

    private Leaderboard addLeaderboard(String categoryId, String lable) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setCategory(new Category(categoryId, lable));
        leaderboard.setRuns(getEntrys());
        leaderboardRepository.save(leaderboard);
        return leaderboard;
    }

    private void addGames() {
        final String any_percent = "any_percent";
        final String any_percent_symbol = "Any %";

        Game game0 = new Game();
        game0.setName("Minecraft");
        game0.setSlug("minecraft");
        game0.setImageUrl("/games/minecraft.avif");
        game0.setLeaderboards(List.of(
                addLeaderboard(any_percent, any_percent_symbol),
                addLeaderboard("any_percent_glitchless", "Any % Glitchless"),
                addLeaderboard("all_achievements", "All Achievements")
        ));

        Game game1 = new Game();
        game1.setName("Cuphead");
        game1.setSlug("cuphead");
        game1.setImageUrl("/games/cuphead.png");
        game1.setLeaderboards(List.of(
                addLeaderboard(any_percent, any_percent_symbol),
                addLeaderboard("low_percent", "Low %"),
                addLeaderboard("all_flags", "All Flags"),
                addLeaderboard("full_clear", "Full Clear"),
                addLeaderboard("no_hit", "No Hit")
        ));

        Game game2 = new Game();
        game2.setName("Mario Kart 8 Deluxe");
        game2.setSlug("mario-kart-8-deluxe");
        game2.setImageUrl("/games/mario_kart_8_deluxe.png");
        game2.setLeaderboards(List.of(
                addLeaderboard("48_tracks", "48 Tracks"),
                addLeaderboard("48_tracks_dlc", "48 Tracks DLC"),
                addLeaderboard("96_tracks", "96 Tracks"),
                addLeaderboard("bonus_tracks", "Bonus Tracks")
        ));

        Game game3 = new Game();
        game3.setName("Hollow Knight");
        game3.setSlug("hollow-knight");
        game3.setImageUrl("/games/hollow_knight.png");
        game3.setLeaderboards(List.of(
                addLeaderboard(any_percent, any_percent_symbol),
                addLeaderboard("all_skills", "All Skills"),
                addLeaderboard("112_percent", "112%"),
                addLeaderboard("true_ending", "True Ending"),
                addLeaderboard("low_percent", "Low %")
        ));

        Game game4 = new Game();
        game4.setName("Portal 2");
        game4.setSlug("portal-2");
        game4.setImageUrl("/games/portal_2.png");
        game4.setLeaderboards(List.of(
                addLeaderboard("single_player", "Single Player"),
                addLeaderboard("coop", "Coop"),
                addLeaderboard("solo_coop", "Solo Co-Op"),
                addLeaderboard("least_portals", "Least Portals")

        ));

        Game game5 = new Game();
        game5.setName("The Legend of Zelda: Breath of the Wild");
        game5.setSlug("the-legend-of-zelda-breath-of-the-wild");
        game5.setImageUrl("/games/tlozbotw.png");
        game5.setLeaderboards(List.of(
                addLeaderboard(any_percent, any_percent_symbol),
                addLeaderboard("all_main_quests", "All Main Quests"),
                addLeaderboard("all_dungeons", "All Dungeons"),
                addLeaderboard("all_shrines", "All Shrines"),
                addLeaderboard("100_percent", "100%")
        ));

        Game game6 = new Game();
        game6.setName("The Simpsons: Hit & Run");
        game6.setSlug("the-simpsons-hit-and-run");
        game6.setImageUrl("/games/the_simpsons_hit_and_run.png");
        game6.setLeaderboards(List.of(
                addLeaderboard(any_percent, any_percent_symbol),
                addLeaderboard("100_percent", "100%"),
                addLeaderboard("all_story_missions", "All Story Missions"),
                addLeaderboard("no_mission_warps", "No Mission Warps")
        ));

        Game game7 = new Game();
        game7.setName("Super Mario 64");
        game7.setSlug("super-mario-64");
        game7.setImageUrl("/games/super_mario_64.png");
        game7.setLeaderboards(List.of(
                addLeaderboard("120_star", "120 Star"),
                addLeaderboard("70_star", "70 Star"),
                addLeaderboard("16_star", "16 Star"),
                addLeaderboard("1_star", "1 Star"),
                addLeaderboard("0_star", "0 Star")
        ));

        Game game8 = new Game();
        game8.setName("Dark Souls III");
        game8.setSlug("dark-souls-3");
        game8.setImageUrl("/games/dark_souls_3.png");
        game8.setLeaderboards(List.of(
                addLeaderboard(any_percent, any_percent_symbol),
                addLeaderboard("all_bosses", "All Bosses"),
                addLeaderboard("no_hit", "No Hit"),
                addLeaderboard("no_death", "No Death")
        ));

        Game game9 = new Game();
        game9.setName("Getting Over It with Bennett Foddy");
        game9.setSlug("getting-over-it-with-bennett-foddy");
        game9.setImageUrl("/games/goiwbf.png");
        game9.setLeaderboards(List.of(
                addLeaderboard("glitchless", "Glitchless"),
                addLeaderboard("snake", "Snake")
        ));

        gameRepository.saveAll(List.of(game0, game1, game2, game3, game4, game5, game6, game7, game8, game9));
    }
}