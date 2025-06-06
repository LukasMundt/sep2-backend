package de.haw.se2.speedrun.user.dataaccess.api.repo;

import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpeedrunnerRepository extends JpaRepository<Speedrunner, UUID> {
    Optional<Speedrunner> findByUsername(String name);
    Optional<Speedrunner> findByEmail(String email);
}
