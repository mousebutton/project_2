package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;
import nl.hhs.project.koffieapp.koffieapp.repository.CanvasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/canvas")
@CrossOrigin(origins = "http://localhost:4200")
public class CanvasController {

    @Autowired
    private CanvasRepository canvasRepository;

    @GetMapping("/all")
    public List<Canvas> findAll(){
        return canvasRepository.findAll();
    }

    /**
     * Find one Canvas so the admin can edit it
     * @param id
     * @return Canvas
     */
    @GetMapping("/{id}")
    public Optional<Canvas> findById(@PathVariable(value = "id") final long id){
        return canvasRepository.findById(id);
    }

    /**
     * Find the Canvas based on the department the user is in
     * @param department
     * @return Canvas
     */
    @GetMapping("/department/{department}")
    public Canvas findByDepartmentName(@PathVariable(value = "department") final String department){
        return canvasRepository.findByDepartment_Name(department);
    }
}
