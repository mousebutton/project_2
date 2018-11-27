package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;
import nl.hhs.project.koffieapp.koffieapp.model.Department;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import nl.hhs.project.koffieapp.koffieapp.model.payload.ApiResponse;
import nl.hhs.project.koffieapp.koffieapp.repository.CanvasRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.DepartmentRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;


/**
 * Only allow requests from client with Admin role
 */

@RestController
@RequestMapping("api/admin/departments")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CanvasRepository canvasRepository;

    /**
     * Only get users that are not in a department
     *
     * @return
     */
    @GetMapping(value = "/users")
    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/all")
    public List<Department> findAll() {

        return departmentRepository.findAll();
    }


    @GetMapping("/test")
    public List<Department> test() {

        return departmentRepository.findAll();
    }

    @PostMapping("/add")
    public Department addDepartment(
            @RequestParam(value = "departmentName", required = false) final String departmentName) {

        return departmentRepository.save(new Department(departmentName));
    }

    @GetMapping("/addUser")
    public ApiResponse addUserToDepartment(
            @RequestParam(value = "email") final String email,
            @RequestParam(value = "department") final long departmentId) {

        Optional<User> user = userRepository.findUserByEmail(email);
        Department department = departmentRepository.getOne(departmentId);

        return user.map((u) -> {
            u.setDepartment(department);
            userRepository.save(u);
            return new ApiResponse(true, "User " + email + " added to department " + department.getName());
        })
                .orElse(new ApiResponse(false, "User with email " + email + " not found"));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUserFromDepartment(
            @RequestParam(value = "user") final Long userId) {

        User user = userRepository.getOne(userId);
        user.setDepartment(null);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Only delete the department if it is not bound to a canvas
     *
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteDepartment(
            @PathVariable(value = "id") final long id) {

        Department department = departmentRepository.getOne(id);
        Canvas canvas = canvasRepository.findByDepartment_Name(department.getName());

        if (canvas == null) {
            // safe to delete
            departmentRepository.delete(department);
            return new ApiResponse(true, "Deleted department " + department.getName());
        } else {
            // cannot delete
            return new ApiResponse(false, "Cannot delete department " + department.getName() +
                    ", still bound to canvas with id: " + canvas.getId());
        }
    }
}
