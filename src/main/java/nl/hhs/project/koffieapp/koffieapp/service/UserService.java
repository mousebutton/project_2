package nl.hhs.project.koffieapp.koffieapp.service;

import nl.hhs.project.koffieapp.koffieapp.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    String login(User user);
    boolean register(User user);
    void delete(User user);
    User whoAmI(HttpServletRequest request);
    User update(User user);
}
