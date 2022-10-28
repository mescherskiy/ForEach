package ua.com.foreach.jwtutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.services.CustomUserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = userService.findByLogin(username);
        if (customUser == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else {
            return new User(customUser.getUsername(),
                    customUser.getPassword(),
                    customUser.getAuthorities());
        }
    }
}

