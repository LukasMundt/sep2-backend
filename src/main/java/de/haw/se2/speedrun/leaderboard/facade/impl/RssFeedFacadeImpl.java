package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.leaderboard.facade.api.RssFeedFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RssFeedUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void illegalArgumentException() {
        //Redefine the exception handler here, because only here we know a IllegalArgumentException comes from a bad
        //UUID
    }
}
