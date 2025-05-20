package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.pojo.RunReview;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunReviewUseCase;
import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RunReviewUseCaseImpl implements RunReviewUseCase {

    private final GameRepository gameRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final RunRepository runRepository;

    @Autowired
    public RunReviewUseCaseImpl(GameRepository gameRepository, LeaderboardRepository leaderboardRepository, RunRepository runRepository) {
        this.gameRepository = gameRepository;
        this.leaderboardRepository = leaderboardRepository;
        this.runRepository = runRepository;
    }

    @Override
    public List<RunReview> getUnreviewedRuns(String gameSlug, String categoryId) {
        Game game = getGame(gameSlug);
        Leaderboard leaderboard = getLeaderboard(game, categoryId);

        return leaderboard.getRuns()
                .stream()
                .filter(r -> !r.isVerified())
                .map(r -> {
                    RunReview runReview = new RunReview();
                    runReview.setRun(r);
                    runReview.setUuid(r.getId());
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
        Run run = getRun(runId);
        Leaderboard leaderboard = getLeaderboardByRun(run);

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

    private Game getGame(String gameSlug) {
        Optional<Game> game = gameRepository.findBySlug(gameSlug);
        if (game.isEmpty()){
            throw new EntityNotFoundException(String.format("Game '%s' not found", gameSlug));
        }

        return game.get();
    }

    private Leaderboard getLeaderboard(Game game, String categoryId) {
        Optional<Leaderboard> leaderboard = game.getLeaderboards()
                .stream()
                .filter(l -> l.getCategory()
                        .getCategoryId()
                        .equalsIgnoreCase(categoryId))
                .findFirst();

        if(leaderboard.isEmpty()){
            throw new EntityNotFoundException(String.format("Leaderboard of category '%s' not found", categoryId));
        }

        return leaderboard.get();
    }

    private Leaderboard getLeaderboardByRun(Run run) {
        Optional<Leaderboard> leaderboard = leaderboardRepository.findLeaderboardByRunsContaining(run);
        if(leaderboard.isEmpty()){
            throw new EntityNotFoundException(String.format("Leaderboard of run '%s' is not found", run.getId().toString()));
        }

        return leaderboard.get();
    }

    private Run getRun(UUID runId){
        Optional<Run> run = runRepository.getRunById(runId);

        if(run.isEmpty()){
            throw new EntityNotFoundException("Run with UUID " + runId + " not found");
        }

        return run.get();
    }
}
