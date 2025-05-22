package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.leaderboard.facade.api.RssFeedFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RssFeedUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class RssFeedFacadeImpl implements RssFeedFacade {

    private final RssFeedUseCase rssFeedUseCase;

    @Autowired
    public RssFeedFacadeImpl(RssFeedUseCase rssFeedUseCase) {
        this.rssFeedUseCase = rssFeedUseCase;
    }

    @Override
    public ResponseEntity<String> getFeedUrl() {
        return new ResponseEntity<>(rssFeedUseCase.getFeedUrl(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getFeedView(String id) {
        return new ResponseEntity<>(rssFeedUseCase.getFeedView(id), HttpStatus.OK);
    }
}
