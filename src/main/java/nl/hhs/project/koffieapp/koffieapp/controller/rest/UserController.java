package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.UserDTO;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/who")
    public UserDTO whoAmI(HttpServletRequest request){
        return userService.whoAmI(request);
    }

    @PutMapping("/user")
    public UserDTO update(@RequestBody UserDTO userDTO) {
        return new UserDTO(userService.update(userDTO));
    }

    @PostMapping("/avatar")
    public UserDTO uploadNewAvatar(@RequestBody UserDTO userDTO) {
        return new UserDTO(userService.updateAvatar(userDTO));
    }
}
