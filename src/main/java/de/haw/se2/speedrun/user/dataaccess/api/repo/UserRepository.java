package de.haw.se2.speedrun.user.dataaccess.api.repo;

import de.haw.se2.speedrun.user.dataaccess.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
}
