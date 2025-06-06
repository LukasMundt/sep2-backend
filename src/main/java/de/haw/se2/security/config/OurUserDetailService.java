package de.haw.se2.security.config;

import de.haw.se2.security.common.pojo.OurUserDetails;
import de.haw.se2.speedrun.user.dataaccess.api.entity.User;
import de.haw.se2.speedrun.user.dataaccess.api.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OurUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return new OurUserDetails(user.get());
    }
}
