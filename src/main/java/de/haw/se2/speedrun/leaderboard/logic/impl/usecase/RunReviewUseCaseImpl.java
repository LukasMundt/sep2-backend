package de.haw.se2.speedrun.leaderboard.logic.impl.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.pojo.RunReview;
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
    public List<RunReview> getUnreviewedRuns(String gameSlug, String categoryId) {
        Optional<Game> game = gameRepository.findBySlug(gameSlug);
        if (game.isEmpty()){
            throw new EntityNotFoundException(String.format("Game '%s' not found", gameSlug));
        }

        Optional<Leaderboard> leaderboard = game.get()
                .getLeaderboards()
                .stream()
                .filter(l -> l.getCategory()
                        .getCategoryId()
                        .equalsIgnoreCase(categoryId))
                .findFirst();

        if(leaderboard.isEmpty()){
            throw new EntityNotFoundException(String.format("Leaderboard of category '%s' not found", categoryId));
        }

        return leaderboard.get()
                .getRuns()
                .stream()
                .filter(r -> !r.isVerified())
                .map(r -> {
                    RunReview runReview = new RunReview();
                    runReview.setRun(r);
                    runReview.setUuid(r.getId());
                    runReview.setGameName(gameSlug);
                    runReview.setCategoryLabel(categoryId);
                    return runReview;
                }).toList();
    }

    @Transactional
    @Override
    public void verifyRun(UUID runId) {
        Optional<Run> run = runRepository.getRunById(runId);

        if(run.isEmpty()){
            throw new EntityNotFoundException("Run with UUID " + runId + " not found");
        }

        run.get()
                .setVerified(true);
    }
}
