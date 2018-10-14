package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Base64;

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

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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
        return userService.whoAmI(request);
    }

    @PutMapping("/user")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/avatar")
    public void test(@RequestBody String imageData) {
        String[] data = imageData.split(",");
        String base64 = "data:image/jpeg;base64," + data[6].substring(0, data[6].length()-1);
        Long id = Long.valueOf(data[0].substring(data[0].length()-1));
        userService.updateAvatar(id, base64.getBytes());
    }
}
