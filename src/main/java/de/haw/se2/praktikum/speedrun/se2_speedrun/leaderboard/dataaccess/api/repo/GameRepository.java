package de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.repo;

import de.haw.se2.praktikum.speedrun.se2_speedrun.leaderboard.dataaccess.api.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
