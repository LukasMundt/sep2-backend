package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.leaderboard.facade.api.ReviewingFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RunReviewUseCase;
import de.haw.se2.speedrun.openapitools.model.RunReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class ReviewingFacadeImpl implements ReviewingFacade {

    private final RunReviewUseCase runReviewUseCase;
    private final CustomizedModelMapper mapper;

    @Autowired
    public ReviewingFacadeImpl(RunReviewUseCase runReviewUseCase, CustomizedModelMapper customizedModelMapper) {
        this.runReviewUseCase = runReviewUseCase;
        this.mapper = customizedModelMapper;
    }

    @Override
    public ResponseEntity<List<RunReview>> restApiReviewsUnreviewedGameSlugCategoryIdGet(String gameSlug, String categoryId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> restApiReviewsVerifyPatch(String body) {
        runReviewUseCase.verifyRun(UUID.fromString(body));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
