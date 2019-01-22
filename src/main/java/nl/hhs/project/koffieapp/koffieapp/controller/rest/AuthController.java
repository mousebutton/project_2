package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.payload.ApiResponse;
import nl.hhs.project.koffieapp.koffieapp.model.payload.JwtAuthenticationResponse;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://ec2-18-191-130-224.us-east-2.compute.amazonaws.com:4200")
//@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "${co.url}")
class AuthController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        String jwtToken = userService.login(user);
        if (!StringUtils.hasText(jwtToken)) {
            return ResponseEntity.badRequest().build();
        }
        userService.pushNewUserOnlineNotification(user);
        return ResponseEntity.ok(
                new JwtAuthenticationResponse(userService.whoAmI(jwtToken), jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.register(user)) {
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
        }
        return ResponseEntity.badRequest().build();
    }
}
