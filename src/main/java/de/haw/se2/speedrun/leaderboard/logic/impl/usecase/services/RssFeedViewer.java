package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Feed buildFeed(String id) {
        Feed feed = new Feed();
        Speedrunner speedrunner = getSpeedrunner(id);
        feed.setTitle("Hallo, " + speedrunner.getUsername());
        feed.setLanguage("DE");
        feed.setModified(new Date());
        feed.setEntries(buildFeedEntries(id));
        return feed;
    }

    private List<Entry> buildFeedEntries(String id) {
        Speedrunner speedrunner = getSpeedrunner(id);
        List<Run> runs = runRepository.findAllById(speedrunner.getNewFasterPlayers().stream().map(FasterInformation::runId).toList());
        List<Speedrunner> speedrunners = runs.stream().map(Run::getSpeedrunner).toList();

        List<Entry> entries = new ArrayList<>();
        for(int i = 0; i < speedrunners.size(); i++) {
            Run run = runs.get(i);
            Speedrunner sp = speedrunners.get(i);
            Entry Entry = new Entry();
            Entry.setTitle("Überboten von: " + sp.getUsername() + "mit einer Zeit von: " + run.getRuntime().runDuration().toString());
            Entry.setUpdated(new Date());
            entries.add(Entry);
        }

        return entries;
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
