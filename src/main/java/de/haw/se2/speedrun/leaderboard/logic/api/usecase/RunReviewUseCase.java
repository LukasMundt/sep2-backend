package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.pojo.RunReview;

import java.util.List;
import java.util.UUID;

public interface RunReviewUseCase {
    List<RunReview> getUnreviewedRuns(String gameSlug, String categoryId);
    void verifyRun(UUID runId);
    void deleteUnreviewedRun(UUID uuid);
}
