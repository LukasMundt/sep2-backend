package de.haw.se2.speedrun.leaderboard.facade.impl;

import de.haw.se2.speedrun.leaderboard.facade.api.RssFeedFacade;
import de.haw.se2.speedrun.leaderboard.logic.api.usecase.RssFeedUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RssFeedFacadeImpl implements RssFeedFacade {

    private final RssFeedUseCase rssFeedUseCase;

    @Autowired
    public RssFeedFacadeImpl(RssFeedUseCase rssFeedUseCase) {
        this.rssFeedUseCase = rssFeedUseCase;
    }

    @Override
    public String getFeedUrl() {
        return rssFeedUseCase.getFeedUrl();
    }

    @Override
    public ModelAndView getFeedView(String id) {
        return rssFeedUseCase.getFeedView(id);
    }
}
