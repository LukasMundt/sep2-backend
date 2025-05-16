package de.haw.se2.speedrun.leaderboard.dataaccess.api.repo;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Run;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RunRepository extends JpaRepository<Run, UUID> {
    Optional<Run> getRunById(UUID id);
}
