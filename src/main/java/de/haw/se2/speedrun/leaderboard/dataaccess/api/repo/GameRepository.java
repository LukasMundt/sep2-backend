package de.haw.se2.speedrun.leaderboard.dataaccess.api.repo;

import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Game;
import de.haw.se2.speedrun.leaderboard.dataaccess.api.entity.Leaderboard;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findBySlug(@NonNull @NotNull String gameSlug);
    Optional<Game> findGameByLeaderboardsContaining(@NonNull @NotNull Leaderboard leaderboard);
}
