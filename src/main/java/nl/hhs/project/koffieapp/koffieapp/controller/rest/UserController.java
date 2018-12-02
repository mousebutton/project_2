package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.CoffeeOrder;
import nl.hhs.project.koffieapp.koffieapp.model.CoffeeRequest;
import nl.hhs.project.koffieapp.koffieapp.model.UserDTO;
import nl.hhs.project.koffieapp.koffieapp.repository.OrderRepository;
import nl.hhs.project.koffieapp.koffieapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        coffeeOrder.setCoffeeType(coffeeRequest.getCoffee());
        coffeeOrder.setMilk(coffeeRequest.getMilk());
        coffeeOrder.setSugar(coffeeRequest.getSugar());

        return orderRepository.save(coffeeOrder);
    }

    @PostMapping("/avatar")
    public UserDTO uploadNewAvatar(@RequestBody UserDTO userDTO) {
        return new UserDTO(userService.updateAvatar(userDTO));
    }
}
