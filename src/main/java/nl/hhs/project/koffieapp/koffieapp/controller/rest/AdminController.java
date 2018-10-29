package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.Department;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.repository.DepartmentRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Only allow requests from client with Admin role
 */

@RestController
@RequestMapping("api/admin")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping(value = "/users")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/user/{id}")
    public Optional<User> findUserById(@PathVariable(value = "id") final Long id) {
        return userRepository.findById(id);
    }

    @GetMapping("/departments")
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
