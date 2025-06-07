package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.facade.api.ReviewingFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunReviewUseCase;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunUseCase;
import de.haw.se2.speedrun.openapitools.model.RunReview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
@RequiredArgsConstructor
public class ReviewingFacadeImpl implements ReviewingFacade {

    private final RunReviewUseCase runReviewUseCase;
    private final CustomizedModelMapper mapper;
    private final RunUseCase runUseCase;

    @Override
    public ResponseEntity<List<RunReview>> restApiReviewsUnreviewedGameSlugCategoryIdGet(String gameSlug, String categoryId) {
        List<de.haw.se2.speedrun.leaderboard.common.api.pojo.RunReview> runs = runReviewUseCase.getUnreviewedRuns(gameSlug, categoryId);
        List<RunReview> dtos = runs
                .stream()
                .map(run -> mapper.map(run, RunReview.class))
                .toList();

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> restApiReviewsUnreviewedUuidDelete(String uuid) {
        runUseCase.deleteRun(UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> restApiReviewsUnreviewedUuidPatch(String uuid) {
        runReviewUseCase.verifyRun(UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
