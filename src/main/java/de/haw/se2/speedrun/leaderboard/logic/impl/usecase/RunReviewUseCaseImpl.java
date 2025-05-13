package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.RunReview;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunReviewUseCase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RunReviewUseCaseImpl implements RunReviewUseCase {

    private final GameRepository gameRepository;
    private final RunRepository runRepository;

    @Autowired
    public RunReviewUseCaseImpl(GameRepository gameRepository, RunRepository runRepository) {
        this.gameRepository = gameRepository;
        this.runRepository = runRepository;
    }

    @Override
    public List<RunReview> getUnreviewedRuns() {
        List<Game> games = gameRepository.findAll();

        List<RunReview> runReviews = new ArrayList<>();

        for(Game game : games) {
            List<Leaderboard> leaderboards = game.getLeaderboards();

            for(Leaderboard leaderboard : leaderboards) {
                List<Run> unverifiedRuns = leaderboard
                        .getRuns()
                        .stream()
                        .filter(r -> !r.isVerified())
                        .toList();

                for(Run run : unverifiedRuns) {
                    RunReview runReview = new RunReview();
                    runReview.setRun(run);
                    runReview.setUuid(run.getId());
                    runReview.setGameName(game.getName());
                    runReview.setCategoryLabel(leaderboard.getCategory().label());
                    runReviews.add(runReview);
                }
            }
        }

        return runReviews;
    }

    @Transactional
    @Override
    public void verifyRun(UUID runId) {
        Optional<Run> run = runRepository.getRunById(runId);

        if(run.isEmpty()){
            throw new EntityNotFoundException("Run with UUID " + runId + " not found");
        }

        run.get().setVerified(true);
    }
}
