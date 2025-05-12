package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.leaderboard.facade.api.ReviewingFacade;
import de.haw.se2.speedrun.openapitools.model.RunReview;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class ReviewingFacadeImpl implements ReviewingFacade {

    @Override
    public ResponseEntity<List<RunReview>> restApiReviewsUnreviewedAllGet() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> restApiReviewsVerifyPost(String body){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
