package de.haw.se2.speedrun.leaderboard.dataaccess.api.repo;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
}
