package de.haw.se2.praktikum.speedrun.se2_speedrun.user.dataaccess.api.repo;

import de.haw.se2.praktikum.speedrun.se2_speedrun.user.dataaccess.api.entity.Speedrunner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeedrunnerRepository extends JpaRepository<Speedrunner, Long> {
}
