package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.Drink;
import nl.hhs.project.koffieapp.koffieapp.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/drinks")
@CrossOrigin(origins = "http://ec2-18-191-130-224.us-east-2.compute.amazonaws.com:4200")
public class DrinkController {

    @Autowired
    private DrinkRepository drinkRepository;

//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/all")
    public List<Drink> findAllDrinks(){
        return drinkRepository.findAllByOrderByNameAsc();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add")
    public Drink addDrink(@RequestBody final Drink drink){
        return drinkRepository.save(drink);
    }
}
