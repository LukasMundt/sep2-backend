package de.haw.se2.speedrun.leaderboard.dataaccess.api.repo;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    Optional<Leaderboard> findLeaderboardByRunsContaining(@NonNull Run run);

    List<Leaderboard> findLeaderboardsByRunsContaining(List<Run> runs);
}
