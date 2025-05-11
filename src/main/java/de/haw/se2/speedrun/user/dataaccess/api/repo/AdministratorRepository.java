package de.haw.se2.speedrun.user.dataaccess.api.repo;

import de.haw.se2.speedrun.user.dataaccess.api.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Optional<Administrator> findByUsername(String username);
}
