package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.exception.AppException;
import nl.hhs.project.koffieapp.koffieapp.model.Role;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.UserDTO;
import nl.hhs.project.koffieapp.koffieapp.repository.OrderRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.RoleRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import nl.hhs.project.koffieapp.koffieapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    // We will use this template to send push notifications to the Clients
    @Autowired
    private SimpMessagingTemplate template;


    /**
     * Handle login request and set the user in the security context.
     *
     * @param user
     * @return Jwt token
     */
    @Override
    public String login(User user) {
        Optional<User> byEmail = userRepository.findUserByEmail(user.getEmail());
        if (byEmail != null) {
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
     *
     * @param user
     * @return boolean result of the registration request
     */
    @Override
    public boolean register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }

        List<Role> roles = new ArrayList();

        User newUser = new User(user);

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRole("USER")
                .orElseThrow(() -> new AppException("User Role not set."));
        roles.add(userRole);

        newUser.setRoles(roles);
        userRepository.save(newUser);

        if (user.getAdminRole() == "true") {
            Role adminRole = roleRepository.findByRole("ADMIN")
                    .orElseThrow(() -> new AppException("User Role not set."));
            roles.add(adminRole);
            newUser.setRoles(roles);
            userRepository.save(newUser);
        }
        return true;
    }

    /**
     * Return User based on the security token
     *
     * @param request
     * @return User
     */
    @Override
    public UserDTO whoAmI(HttpServletRequest request) {
        User user = userRepository.findUserByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request))).get();
        return new UserDTO(user);
    }

    @Override
    public UserDTO whoAmI(String jwtToken) {
        User user = userRepository.findUserByEmail(jwtTokenProvider.getUsername(jwtToken)).get();
        return new UserDTO(user);
    }

    /**
     * Update the User details
     *
     * @param userDTO
     * @return updated User
     */
    @Override
    public User update(UserDTO userDTO) {
        User toUpdate = userRepository.getOne(userDTO.getId());
        toUpdate.setFirstName(userDTO.getFirstName());
        toUpdate.setLastName(userDTO.getLastName());
        return userRepository.save(toUpdate);
    }

    @Override
    public User updateAvatar(UserDTO userDTO) {
        User toUpdate = userRepository.getOne(userDTO.getId());
        toUpdate.setAvatar(userDTO.getAvatar());
        return userRepository.save(toUpdate);
    }

    @Override
    public User findById(long id) {
        return userRepository.getOne(id);
    }
    

    /**
     * When a user comes online, notify the other users.
     * All Clients should be subscribed to this channel.
     */
    //TODO change so only users from the same department will receive the messages
    @Override
    public void pushNewUserOnlineNotification(User user) {
        template.convertAndSend("/global-message/user", user.getEmail() + " is online");
    }


}
