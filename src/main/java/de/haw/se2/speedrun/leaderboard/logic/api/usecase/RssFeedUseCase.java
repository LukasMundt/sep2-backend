package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

import org.springframework.web.servlet.View;

public interface RssFeedUseCase {
    String getFeedUrl();
    View getFeedView(String id);
}
