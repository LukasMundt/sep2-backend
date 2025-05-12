package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import de.haw.se2.speedrun.leaderboard.common.api.datatype.RunReview;

import java.util.List;

public interface RunReviewUseCase {
    List<RunReview> getUnreviewedRuns();
}
