package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.Department;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.repository.DepartmentRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Only allow requests from client with Admin role
 */

@RestController
@RequestMapping("api/admin/departments")
@CrossOrigin(origins = "http://localhost:4200")
//@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping(value = "/users")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/all")
    public List<Department> findAll() {

        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Department> findById(@PathVariable(value = "id") final long id){
        return departmentRepository.findById(id);
    }

    @GetMapping("/addUser")
    public ResponseEntity<String> addUserToDepartment(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "department") final long departmentId) {

        Optional<User> optional = userRepository.findUserByEmail(email);
        optional.ifPresent(user -> {
                    Department department = departmentRepository.getOne(departmentId);
                    user.setDepartment(department);

                    userRepository.save(user);
                }
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUserFromDepartment(
            @RequestParam(value = "user") final Long userId) {

        User user = userRepository.getOne(userId);
        user.setDepartment(null);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
