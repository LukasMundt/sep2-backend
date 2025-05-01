package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.repo;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardEntryRepository extends JpaRepository<LeaderboardEntry, Long> {
}
