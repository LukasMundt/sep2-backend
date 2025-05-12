package de.haw.se2.speedrun.leaderboard.dataaccess.api.repo;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run, Long> {
}
