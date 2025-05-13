package de.haw.se2.speedrun.security;

import de.haw.se2.speedrun.user.dataaccess.api.entity.Administrator;
import de.haw.se2.speedrun.user.dataaccess.api.entity.Speedrunner;
import de.haw.se2.speedrun.user.dataaccess.api.repo.AdministratorRepository;
import de.haw.se2.speedrun.user.dataaccess.api.repo.SpeedrunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private SpeedrunnerRepository speedrunnerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Administrator> administrator = administratorRepository.findByUsername(username);
        if (administrator.isPresent()) {
            return new CustomUserDetails(administrator.get());
        }
        else {
            Optional<Speedrunner> speedrunner = speedrunnerRepository.findByUsername(username);
            if (speedrunner.isPresent()) {
                return new CustomUserDetails(speedrunner.get());
            }
            else {
                throw new UsernameNotFoundException("Username not found");
            }
        }
    }
}
