package nl.hhs.project.koffieapp.koffieapp.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.UserDTO;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import nl.hhs.project.koffieapp.koffieapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class JwtAuthenticationResponse {

    /**
     * After a login attempt the JwtAuthenticationResponse will be returned to the client
     * The response includes the user and the accessToken
     */
    @JsonIgnore
    @Autowired
    private UserService userService = new UserServiceImpl();

    private UserDTO user;
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(UserDTO user, String accessToken){
        this.user = user;
        this.accessToken = accessToken;
    }
}
