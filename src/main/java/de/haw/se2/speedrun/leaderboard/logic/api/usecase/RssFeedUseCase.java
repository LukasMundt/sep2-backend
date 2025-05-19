package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import org.springframework.web.servlet.ModelAndView;

public interface RssFeedUseCase {
    String getFeedUrl();
    ModelAndView getFeedView(String id);
}
