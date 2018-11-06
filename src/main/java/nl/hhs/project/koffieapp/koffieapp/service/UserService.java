package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.UserDTO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    String login(User user);
    boolean register(User user);
    UserDTO whoAmI(HttpServletRequest request);
    UserDTO whoAmI(String jwtToken);
    void pushNewUserOnlineNotification(User user);
    User update(UserDTO userDTO);
    User updateAvatar(UserDTO userDTO);
}
