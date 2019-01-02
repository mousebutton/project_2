package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.*;
import nl.hhs.project.koffieapp.koffieapp.repository.CoffeeRoundRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.DepartmentRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.DrinkRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.OrderRepository;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Only allow requests from client with User and / or Admin role
 */

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private CoffeeRoundRepository coffeeRoundRepository;

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
        orderRepository.save(coffeeOrder);
        coffeeOrder.setId(coffeeOrder.getId());
        // Send the order back over the websocket
        template.convertAndSend("/global-message/newOrder/" + user.getDepartment().getName(), coffeeOrder);
        return coffeeOrder;
    }

    @GetMapping(value = "/getRequests/{departmentId}")
    public List<CoffeeOrder> getCoffeeRequestsByDepartment(
            @PathVariable(value = "departmentId") final String departmentId) {
        Department department = departmentRepository.findAllByNameEquals(departmentId);
        List<CoffeeOrder> orders = orderRepository.findAllByUserDepartmentAndFinishedIsFalseOrderByIdAsc(department);
        return orders;
    }

    @PostMapping("/coffeeRound")
    public CoffeeRound newCoffeeRound(
            @RequestParam(value = "userId") final long userId,
            @RequestParam(value = "orders") final long[] orderIds) {
        User user = userService.findById(userId);
        List<CoffeeOrder> orders = new ArrayList<>();
        CoffeeRound coffeeRound = new CoffeeRound();
        coffeeRound.setUser(user);
        // Update CoffeeOrders to finished and add to the CoffeeRound
        for (long orderId : orderIds) {
            CoffeeOrder coffeeOrder = orderRepository.getOne(orderId);
            coffeeOrder.setFinished(true);
            orders.add(coffeeOrder);
            orderRepository.save(coffeeOrder);
        }
        // Save CoffeeRound and push notification over the websocket
        coffeeRound.setOrders(orders);
        template.convertAndSend("/global-message/coffeeRound/" + user.getDepartment().getName(), user.getEmail());
        return coffeeRoundRepository.save(coffeeRound);
    }

    @PostMapping("/avatar")
    public UserDTO uploadNewAvatar(@RequestBody UserDTO userDTO) {
        return new UserDTO(userService.updateAvatar(userDTO));
    }
}
