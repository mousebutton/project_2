package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.exception.AppException;
import nl.hhs.project.koffieapp.koffieapp.model.Role;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.payload.JwtAuthenticationResponse;
import nl.hhs.project.koffieapp.koffieapp.repository.RoleRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import nl.hhs.project.koffieapp.koffieapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.OneToMany;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;


    /**
     * Handle login request and set the user in the security context.
     * @param user
     * @return Jwt token
     */
    @Override
    public String login(User user){
        Optional<User> byEmail = userRepository.findUserByEmail(user.getEmail());
        if(byEmail != null){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtTokenProvider.createToken(byEmail.get().getEmail(), byEmail.get().getRoles());
            return jwtToken;
        }
        return null;
    }

    /**
     * Validate new user registration.
     * If the email already exists return false
     * Else register the user and encrypt the password.
     * @param user
     * @return boolean result of the registration request
     */
    @Override
    public boolean register(User user){
        if(userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRole("USER")
                .orElseThrow(() -> new AppException("User Role not set."));
        newUser.setRoles(Arrays.asList(userRole));

        userRepository.save(newUser);
        return true;
    }

    @Override
    public void delete(User user) {
       userRepository.delete(user);
    }

    @Override
    public User whoAmI(HttpServletRequest request) {
        return userRepository.findUserByEmail(
                jwtTokenProvider
                        .getUsername(jwtTokenProvider
                        .resolveToken(request)))
                        .get();
    }

}
