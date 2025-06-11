package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunUseCase;
import de.haw.se2.speedrun.leaderboard.logic.impl.usecase.utilities.Utilities;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.logic.api.usecase.SpeedrunnerUseCase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class RunUseCaseImpl implements RunUseCase {

    private final Utilities utilities;
    private final SpeedrunnerUseCase speedrunnerUseCase;

    @Override
    public List<Run> getVerifiedLeaderboardRuns(String gameSlug, String categoryId) {
        Game game = utilities.getGame(gameSlug);

        List<Leaderboard> categoryLeaderboards =  game.getLeaderboards()
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
    public void addUnverifiedRun(String gameSlug, String categoryId, Date date, String videoLink, Runtime runtime) {
        Game game = utilities.getGame(gameSlug);
        Leaderboard leaderboard = utilities.getLeaderboard(game, categoryId);
        Speedrunner speedrunner = getSpeedrunner();
        List<Run> runs = leaderboard.getRuns();

        //Does the speedrunner already have another run on this leaderboard?
        List<Run> otherRunsFromSpeedrunner = runs.stream()
                .filter(r -> r
                        .getSpeedrunner()
                        .getId().equals(speedrunner.getId()))
                .toList();

        if(otherRunsFromSpeedrunner.isEmpty()) {
            //Speedrunner never submitted a run
            addRun(leaderboard, speedrunner, date, videoLink, runtime);
            return;
        }

        Optional<Run> unsubmittedRun = otherRunsFromSpeedrunner.stream().filter(r -> !r.isVerified()).findFirst();

        if(otherRunsFromSpeedrunner.stream().anyMatch(r -> r.getRuntime().runDuration().compareTo(runtime.runDuration()) <= 0)) {
            // Any run on this leaderboard, submitted or not, is faster than this newly submitted run.
            throw new NotAcceptableStatusException("Speedrunner already has a faster time on the leaderboard or submitted a faster time!");
        } else {
            if(unsubmittedRun.isEmpty()){
                addRun(leaderboard, speedrunner, date, videoLink, runtime);
            } else {
                leaderboard.getRuns().remove(unsubmittedRun.get());
                addRun(leaderboard, speedrunner, date, videoLink, runtime);
            }
        }
    }

    @Override
    @Transactional
    public void deleteRun(UUID runId){
        Run run = utilities.getRun(runId);
        Leaderboard leaderboard = utilities.getLeaderboardByRun(run);
        leaderboard.getRuns().remove(run);
    }

    private void addRun(Leaderboard leaderboard, Speedrunner speedrunner, Date date, String videoLink, Runtime runtime) {
        Run runToAdd = new Run();
        runToAdd.setDate(date);
        runToAdd.setRuntime(runtime);
        runToAdd.setVerified(false);
        runToAdd.setSpeedrunner(speedrunner);
        runToAdd.setVideoLink(videoLink);

        leaderboard.getRuns().add(runToAdd);
    }

    private Speedrunner getSpeedrunner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return speedrunnerUseCase.getSpeedrunnerByEmail(authentication.getName());
    }
}