package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.pojo.RunReview;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunReviewUseCase;
import de.haw.se2.speedrun.leaderboard.logic.impl.usecase.utilities.Utilities;
import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RunReviewUseCaseImpl implements RunReviewUseCase {

    private final Utilities utilities;

    @Autowired
    public RunReviewUseCaseImpl(Utilities utilities) {
        this.utilities = utilities;
    }

    @Override
    public List<RunReview> getUnreviewedRuns(String gameSlug, String categoryId) {
        Game game = utilities.getGame(gameSlug);
        Leaderboard leaderboard = utilities.getLeaderboard(game, categoryId);

        return leaderboard.getRuns()
                .stream()
                .filter(r -> !r.isVerified())
                .map(r -> {
                    RunReview runReview = new RunReview();
                    runReview.setRun(r);
                    runReview.setGameName(gameSlug);
                    runReview.setCategoryLabel(categoryId);
                    return runReview;
                })
                .sorted(Comparator.comparingLong(rr -> rr.getRun()
                        .getDate()
                        .getTime()))
                .toList();
    }

    @Transactional
    @Override
    public void verifyRun(UUID runId) {
        Run run = utilities.getRun(runId);
        Leaderboard leaderboard = utilities.getLeaderboardByRun(run);

        Optional<Run> previousRunToDelete = leaderboard
                .getRuns()
                .stream()
                .filter(r -> r.getSpeedrunner()
                        .getId()
                        .equals(run.getSpeedrunner().getId())
                        &&
                        r.isVerified())
                .findFirst();

        previousRunToDelete.ifPresent(previousRun -> leaderboard.getRuns().remove(previousRun));

        addThisFasterRunToSlowerRuns(run.getSpeedrunner().getId(), run, leaderboard);

        run.setVerified(true);
    }

    private void addThisFasterRunToSlowerRuns(UUID speedrunnerId, Run run, Leaderboard leaderboard) {
        List<Run> runs = leaderboard.getRuns();
        runs.stream()
                .filter(r -> r.getRuntime().runDuration().compareTo(run.getRuntime().runDuration()) > 0 && r.isVerified())
                .limit(50)
                .forEach(r -> r.getSpeedrunner()
                        .getNewFasterPlayers()
                        .add(new FasterInformation(speedrunnerId, run.getId()))
                );
    }
}
