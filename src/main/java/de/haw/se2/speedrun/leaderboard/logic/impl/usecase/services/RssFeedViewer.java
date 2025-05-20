package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.user.common.api.datatype.FasterInformation;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import java.util.*;

@Component
public class RssFeedViewer extends AbstractAtomFeedView {

    private final SpeedrunnerRepository speedrunnerRepository;
    private final RunRepository runRepository;

    @Autowired
    public RssFeedViewer(SpeedrunnerRepository speedrunnerRepository, RunRepository runRepository) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.runRepository = runRepository;
    }

    /**
     * @param model   the model, in case meta information must be populated from it
     * @param feed    the feed being populated
     * @param request in case we need locale etc. Shouldn't look at attributes.
     */
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        Speedrunner speedrunner = getSpeedrunner(model.get("id").toString());
        feed.setTitle("Hallo, " + speedrunner.getUsername());
        feed.setLanguage("DE");
        feed.setModified(new Date());
    }

    /**
     * Subclasses must implement this method to build feed entries, given the model.
     * <p>Note that the passed-in HTTP response is just supposed to be used for
     * setting cookies or other HTTP headers. The built feed itself will automatically
     * get written to the response after this method returns.
     *
     * @param model    the model Map
     * @param request  in case we need locale etc. Shouldn't look at attributes.
     * @param response in case we need to set cookies. Shouldn't write to it.
     * @return the feed entries to be added to the feed
     * @throws Exception any exception that occurred during document building
     * @see Entry
     */
    @Transactional
    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Speedrunner speedrunner = getSpeedrunner(model.get("id").toString());
        List<Run> runs = runRepository.findAllById(speedrunner.getNewFasterPlayers().stream().map(FasterInformation::runId).toList());
        List<Speedrunner> speedrunners = runs.stream().map(Run::getSpeedrunner).toList();

        List<Entry> entries = new ArrayList<>();
        for(int i = 0; i < speedrunners.size(); i++) {
            Run run = runs.get(i);
            Speedrunner sp = speedrunners.get(i);
            Entry entry = new Entry();
            entry.setTitle("Überboten von: " + sp.getUsername() + "mit einer Zeit von: " + run.getRuntime().runDuration().toString());
            entry.setModified(new Date());
            entries.add(entry);
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
