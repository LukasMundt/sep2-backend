package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
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

    private final SpeedrunnerRepository speedrunnerRepository;
    private final RunRepository runRepository;

    @Autowired
    public RssFeedViewer(SpeedrunnerRepository speedrunnerRepository, RunRepository runRepository) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.runRepository = runRepository;
    }

    @SneakyThrows
    public String buildFeed(String id) throws FeedException, IOException {
        Channel feed = new Channel("rss_2.0");
        Speedrunner speedrunner = getSpeedrunner(id);
        feed.setDescription("RSS Feed");
        feed.setLink("http//localhost");
        feed.setTitle("Hallo, " + speedrunner.getUsername());
        feed.setLanguage("DE");
        feed.setPubDate(new Date());
        feed.setItems(buildFeedEntries(id));

        WireFeedOutput output = new WireFeedOutput();
        StringWriter writer = new StringWriter();
        output.output(feed, writer);

        return writer.toString();
    }

    private List<Item> buildFeedEntries(String id) {
        Speedrunner speedrunner = getSpeedrunner(id);
        List<Run> runs = runRepository.findAllById(speedrunner.getNewFasterPlayers().stream().map(FasterInformation::runId).toList());
        List<Speedrunner> speedrunners = runs.stream().map(Run::getSpeedrunner).toList();

        List<Item> items = new ArrayList<>();
        for(int i = 0; i < speedrunners.size(); i++) {
            Run run = runs.get(i);
            Speedrunner sp = speedrunners.get(i);
            Item item = new Item();
            item.setTitle("Überboten von: " + sp.getUsername() + "mit einer Zeit von: " + run.getRuntime().runDuration().toString());
            item.setPubDate(new Date());
            items.add(item);
        }

        return items;
    }

    private Speedrunner getSpeedrunner(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Speed runner id cannot be null or empty");
        }

        Optional<Speedrunner> speedrunner = speedrunnerRepository.findById(UUID.fromString(id));
        if (speedrunner.isEmpty()) {
            throw new EntityNotFoundException("Speed runner with id " + id + " not found");
        }

        return speedrunner.get();
    }
}
