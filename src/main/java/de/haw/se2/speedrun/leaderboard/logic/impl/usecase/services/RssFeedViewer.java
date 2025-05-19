package de.haw.se2.speedrun.leaderboard.logic.impl.usecase.services;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.LeaderboardRepository;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.repo.RunRepository;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class RssFeedViewer extends AbstractAtomFeedView {

    private final SpeedrunnerRepository speedrunnerRepository;
    private final LeaderboardRepository leaderboardRepository;
    private final RunRepository runRepository;

    @Autowired
    public RssFeedViewer(SpeedrunnerRepository speedrunnerRepository, LeaderboardRepository leaderboardRepository, RunRepository runRepository) {
        this.speedrunnerRepository = speedrunnerRepository;
        this.leaderboardRepository = leaderboardRepository;
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
        List<Run> runsOfUser = getAllRuns(speedrunner);
        List<Leaderboard> leaderboards = getAllLeaderboards()
        feed.setTitle("Speedrun RSS Feed");
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
    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return List.of();
    }

    private List<Run> getAllRuns(Speedrunner speedrunner) {
        return runRepository.getRunsBySpeedrunnerIsAndVerifiedIsTrue(speedrunner.getId());
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
