package de.haw.se2.security.common.pojo;

import de.haw.se2.speedrun.user.dataaccess.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static de.haw.se2.security.config.SecurityConfig.ADMIN_ROLE;
import static de.haw.se2.security.config.SecurityConfig.USER_ROLE;
import static de.haw.se2.speedrun.user.common.api.datatype.Right.ADMIN;


public class OurUserDetails implements UserDetails {

    private final transient User user;

    public OurUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(user.getRight() == ADMIN){
            return List.of(new SimpleGrantedAuthority(ADMIN_ROLE), new SimpleGrantedAuthority(USER_ROLE));
        }

        return List.of(new SimpleGrantedAuthority(USER_ROLE));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        // Email is used as unique identifier for login, as an email cant contain something like spaces
        // or too crazy special characters (usually xD)
        return user.getEmail();
    }
}
