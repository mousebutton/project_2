package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Only allow requests from client with User and / or Admin role
 */

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/user")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/avatar")
    public User uploadNewAvatar(@RequestBody User user) {
        return userService.updateAvatar(user);
    }
}
