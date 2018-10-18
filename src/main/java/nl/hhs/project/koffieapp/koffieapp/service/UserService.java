package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    String login(User user);
    boolean register(User user);
    User whoAmI(HttpServletRequest request);
    User whoAmI(String jwtToken);
    void pushNewUserOnlineNotification(User user);
    User update(User user);
    User updateAvatar(User user);
}
