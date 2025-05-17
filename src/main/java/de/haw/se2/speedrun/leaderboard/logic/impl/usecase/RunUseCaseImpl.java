package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunUseCase;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class RunUseCaseImpl implements RunUseCase {

    private final GameRepository gameRepository;
    private final SpeedrunnerRepository speedrunnerRepository;

    @Autowired
    public RunUseCaseImpl(GameRepository gameRepository, SpeedrunnerRepository speedrunnerRepository) {
        this.gameRepository = gameRepository;
        this.speedrunnerRepository = speedrunnerRepository;
    }

    @Override
    public List<Run> getVerifiedLeaderboardRuns(String gameSlug, String categoryId) {
        Optional<Game> leaderboardGame =  gameRepository.findBySlug(gameSlug);

        if (leaderboardGame.isEmpty()) {
            throw new EntityNotFoundException(gameSlug);
        }

        List<Leaderboard> categoryLeaderboards =  leaderboardGame
                .get()
                .getLeaderboards()
                .stream()
                .filter(g -> g.getCategory().getCategoryId().equalsIgnoreCase(categoryId))
                .toList();

        if (categoryLeaderboards.isEmpty()) {
            throw new EntityNotFoundException(categoryId);
        }

        return categoryLeaderboards
                .getFirst()
                .getRuns()
                .stream()
                .filter(Run::isVerified)
                .toList();
    }

    @Transactional
    @Override
    public void addUnverifiedRun(String gameSlug, String categoryId, String speedrunnerUsername, Date date, Runtime runtime) {
        Optional<Game> game = gameRepository.findBySlug(gameSlug);
        if(game.isEmpty()) {
            throw new EntityNotFoundException(String.format("Game '%s' not found", gameSlug));
        }

        Optional<Leaderboard> leaderboard = game.get()
                .getLeaderboards()
                .stream()
                .filter(l -> l.getCategory().getCategoryId().equalsIgnoreCase(categoryId))
                .findFirst();

        if(leaderboard.isEmpty()) {
            throw new EntityNotFoundException(String.format("Leaderboard '%s' not found", categoryId));
        }

        Optional<Speedrunner> speedrunner = speedrunnerRepository.findByUsername(speedrunnerUsername);

        if(speedrunner.isEmpty()) {
            throw new EntityNotFoundException(String.format("Speedrunner '%s' not found", speedrunnerUsername));
        }

        List<Run> runs = leaderboard.get().getRuns();

        //Does the speedrunner already have another run on this leaderboard?
        Optional<Run> otherRunFromSpeedrunner = runs.stream()
                .filter(r -> r
                        .getSpeedrunner()
                        .getId().equals(speedrunner.get().getId()))
                .findFirst();
        if(otherRunFromSpeedrunner.isEmpty()) {
            //Speedrunner never submitted a run before
            addRun(leaderboard.get(), speedrunner.get(), date, runtime);
        } else if(otherRunFromSpeedrunner.get().getRuntime().runDuration().compareTo(runtime.runDuration()) < 0) {
            //Speedrunner has a run on this leaderboard, but the newly submitted time is faster
            //Remove old run and add the new run
            removeRun(leaderboard.get(), otherRunFromSpeedrunner.get());
            addRun(leaderboard.get(), speedrunner.get(), date, runtime);
        }
    }

    private void removeRun(Leaderboard leaderboard, Run run) {
        leaderboard.getRuns().remove(run);
    }

    private void addRun(Leaderboard leaderboard, Speedrunner speedrunner, Date date, Runtime runtime) {
        Run runToAdd = new Run();
        runToAdd.setDate(date);
        runToAdd.setRuntime(runtime);
        runToAdd.setVerified(false);
        runToAdd.setSpeedrunner(speedrunner);

        leaderboard.getRuns().add(runToAdd);
    }
}