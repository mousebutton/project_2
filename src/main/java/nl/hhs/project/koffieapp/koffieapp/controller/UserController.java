package nl.hhs.project.koffieapp.koffieapp.controller;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<User> findAllUsers(){
        return userRepository.findAll();

    }

    @GetMapping(value = "/users/{id}")
    public Optional<User> findUserById(@PathVariable(value = "id") final Long id){
        return userRepository.findById(id);
    }

    @PostMapping(value = "/user")
    public User createUser(@RequestBody final User user){
        return userRepository.save(user);
    }


}
