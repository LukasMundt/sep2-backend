package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import de.haw.se2.speedrun.leaderboard.common.api.datatype.Runtime;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.GameRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

@Component
public class RssFeedViewer {

    public static final String NOT_FOUND = " not found";
    private final SpeedrunnerRepository speedrunnerRepository;
    private final RunRepository runRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final GameRepository gameRepository;

    @Autowired
    public RssFeedViewer(SpeedrunnerRepository speedrunnerRepository, RunRepository runRepository,
                         LeaderboardRepository leaderboardRepository, GameRepository gameRepository) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.runRepository = runRepository;
        this.leaderboardRepository = leaderboardRepository;
        this.gameRepository = gameRepository;
    }

    @SneakyThrows
    public String buildFeed(String id) {
        Speedrunner speedrunner = getSpeedrunner(id);
        List<Run> otherRuns = getOtherRuns(speedrunner);
        if(otherRuns.isEmpty()) {
            return getEmptyFeed();
        }

        Channel feed = buildFeedMetaData();
        List<Item> items = buildFeedEntries(speedrunner, otherRuns);
        feed.setItems(items);

        return writeFeed(feed);
    }

    @SneakyThrows
    private String getEmptyFeed() {
        Channel feed = buildFeedMetaData();
        return writeFeed(feed);
    }

    private Channel buildFeedMetaData() {
        Channel feed = new Channel("rss_2.0");
        feed.setDescription("Updates about your Runs");
        feed.setLink("http//localhost");//TODO: Add permanent address to our frontend!
        feed.setEncoding("UTF-8");
        feed.setTitle("Updates about your Runs");
        feed.setLanguage("de");
        feed.setPubDate(new Date());
        return feed;
    }

    private List<Item> buildFeedEntries(Speedrunner speedrunner, List<Run> otherRuns) {
        List<Item> items = new ArrayList<>();
        for (Run fasterRun : otherRuns) {
            try {
                Leaderboard leaderboard = getLeaderboard(fasterRun);
                Run ownSlowerRun = getOwnRun(speedrunner, leaderboard);
                Game game = getGame(leaderboard);
                items.add(formatItem(fasterRun, ownSlowerRun, game, leaderboard, speedrunner));
            } catch (Exception ignored) {
                // Skip old Leaderboards/Run/Games that aren't found in DB. Probably already deleted
            }
        }

        return items;
    }

    private Item formatItem(Run fasterRun, Run ownSlowerRun, Game game, Leaderboard leaderboard, Speedrunner speedrunner) {
        Item item = new Item();

        item.setTitle("Du wurdest überboten!");

        String contentString = String.format("%s/%s: %s, Deine Zeit von %s wurde von %s mit einer Zeit von: %s überboten. Das ist eine Differenz von %s",
                game.getName(),
                leaderboard.getCategory().getLabel(),
                speedrunner.getUsername(),
                ownSlowerRun.getRuntime(),
                fasterRun.getSpeedrunner().getUsername(),
                fasterRun.getRuntime(),
                new Runtime(ownSlowerRun.getRuntime().runDuration().minus(fasterRun.getRuntime().runDuration()))
                );

        Content content = new Content();
        content.setValue(contentString);
        item.setContent(content);
        item.setAuthor("Speedruns.com");//TODO: Dürfen wir das?
        item.setPubDate(new Date());
        return item;
    }

    private String writeFeed(Channel feed) throws IOException, FeedException {
        WireFeedOutput output = new WireFeedOutput();
        StringWriter writer = new StringWriter();
        output.output(feed, writer);
        return writer.toString();
    }

    private Speedrunner getSpeedrunner(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Speed runner id cannot be null or empty");
        }

        Optional<Speedrunner> speedrunner = speedrunnerRepository.findById(UUID.fromString(id));
        if (speedrunner.isEmpty()) {
            throw new EntityNotFoundException("Speed runner with id " + id + NOT_FOUND);
        }

        return speedrunner.get();
    }

    private Leaderboard getLeaderboard(Run run) {
        Optional<Leaderboard> leaderboard = leaderboardRepository.findLeaderboardByRunsContaining(run);
        if (leaderboard.isEmpty()) {
            throw new EntityNotFoundException("Run with id " + run.getId() + NOT_FOUND);
        }

        return leaderboard.get();
    }

    private Game getGame(Leaderboard leaderboard) {
        Optional<Game> game = gameRepository.findGameByLeaderboardsContaining(leaderboard);
        if (game.isEmpty()) {
            throw new EntityNotFoundException("Game of Leaderboard with id " + leaderboard.getId() + NOT_FOUND);
        }

        return game.get();
    }

    private List<Run> getOtherRuns(Speedrunner speedrunner) {
        return runRepository.findAllById(speedrunner.getNewFasterPlayers()
                .stream()
                .map(FasterInformation::runId)
                .toList());
    }

    private Run getOwnRun(Speedrunner speedrunner, Leaderboard leaderboard) {
        Optional<Run> run = leaderboard.getRuns()
                .stream()
                .filter(r -> r.getSpeedrunner().getId().equals(speedrunner.getId()))
                .findFirst();

        if(run.isPresent()) {
            return run.get();
        }

        throw new EntityNotFoundException("Run from Speedrunner " + speedrunner.getId() + NOT_FOUND);
    }
}
