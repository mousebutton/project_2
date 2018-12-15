package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.*;
import nl.hhs.project.koffieapp.koffieapp.repository.DepartmentRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.OrderRepository;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Only allow requests from client with User and / or Admin role
 */

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:4200")
//@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/who")
    public UserDTO whoAmI(HttpServletRequest request) {
        return userService.whoAmI(request);
    }

    @PutMapping("/user")
    public UserDTO update(@RequestBody UserDTO userDTO) {
        return new UserDTO(userService.update(userDTO));
    }

    @GetMapping("/order")
    public List<CoffeeOrder> getOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(value = "/makeorder")
    public CoffeeOrder addOrder(@RequestBody CoffeeRequest coffeeRequest) {

        CoffeeOrder coffeeOrder = new CoffeeOrder();
        User user = userService.findById(coffeeRequest.getUserId());

        coffeeOrder.setUser(user);
        coffeeOrder.setCoffeeType(coffeeRequest.getCoffee());
        coffeeOrder.setMilk(coffeeRequest.getMilk());
        coffeeOrder.setSugar(coffeeRequest.getSugar());

        template.convertAndSend("/global-message/newOrder",
                Arrays.asList(user.getEmail(), coffeeRequest.getCoffee(),
                        LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));

        return orderRepository.save(coffeeOrder);
    }

    @GetMapping(value = "/getRequests/{departmentId}")
    public List<CoffeeOrder> getCoffeeRequestsByDepartment(
            @PathVariable(value = "departmentId") final  String departmentId) {
        Department department = departmentRepository.findAllByNameEquals(departmentId);
        List<CoffeeOrder> orders = orderRepository.findAllByUserDepartmentAndFinishedIsFalse(department);
        return orders;
    }

    @PostMapping("/avatar")
    public UserDTO uploadNewAvatar(@RequestBody UserDTO userDTO) {
        return new UserDTO(userService.updateAvatar(userDTO));
    }
}
