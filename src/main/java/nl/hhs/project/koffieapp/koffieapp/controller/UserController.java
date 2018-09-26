package nl.hhs.project.koffieapp.koffieapp.controller;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import nl.hhs.project.koffieapp.koffieapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<User> findUserById(@PathVariable(value = "id") final Long id) {
        return userRepository.findById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/user")
    public String getUser() {
        return "welcome user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin")
    public String getAdmin() {
        return "welcome admin";
    }

    @GetMapping("/who")
    public User who(HttpServletRequest request) {
        System.out.println(request.toString());
        return userService.whoAmI(request);
    }

}
