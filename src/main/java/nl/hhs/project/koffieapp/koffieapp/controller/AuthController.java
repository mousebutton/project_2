package nl.hhs.project.koffieapp.koffieapp.controller;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.payload.ApiResponse;
import nl.hhs.project.koffieapp.koffieapp.model.payload.JwtAuthenticationResponse;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user, HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        System.out.println(token);
        String jwtToken = userService.login(user);
        if (!StringUtils.hasText(jwtToken)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.register(user)) {
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
        }
        return ResponseEntity.badRequest().build();
    }
}
