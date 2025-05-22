package de.haw.se2.speedrun.leaderboard.logic.api.usecase;

public interface RssFeedUseCase {
    String getFeedUrl();
    String getFeedView(String id);
}
