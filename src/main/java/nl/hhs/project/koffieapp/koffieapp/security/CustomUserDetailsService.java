package nl.hhs.project.koffieapp.koffieapp.security;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load the user by Email, get his details and roles and store in the Spring Security session
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        userByEmail
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
        return userByEmail
                .map(CustomUserDetails::new).get();
    }
}
