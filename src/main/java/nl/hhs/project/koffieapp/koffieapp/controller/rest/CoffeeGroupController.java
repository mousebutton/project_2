package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/groups")
@CrossOrigin(origins = "http://localhost:4200")
public class CoffeeGroupController {
}
