package nl.hhs.project.koffieapp.koffieapp.controller.rest;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;
import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Chair;
import nl.hhs.project.koffieapp.koffieapp.model.Department;
import nl.hhs.project.koffieapp.koffieapp.model.payload.ApiResponse;
import nl.hhs.project.koffieapp.koffieapp.repository.CanvasRepository;
import nl.hhs.project.koffieapp.koffieapp.repository.DepartmentRepository;
import nl.hhs.project.koffieapp.koffieapp.service.CanvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/canvas")
@CrossOrigin(origins = "http://ec2-18-191-130-224.us-east-2.compute.amazonaws.com:4200")
public class CanvasController {

    @Autowired
    private CanvasRepository canvasRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CanvasService canvasService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Canvas> findAll() {
        return canvasRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public List<Department> test() {

        List<Department> departmentsToBind = departmentRepository.findAll();
        List<Canvas> canvasses = canvasRepository.findAll();

        for (Canvas canvas : canvasses) {

            Department department = canvas.getDepartment();

            if (department != null) {
                departmentsToBind.remove(department);
            }
        }
        return departmentsToBind;
    }

    @GetMapping("/{id}")
    public Optional<Canvas> findById(@PathVariable(value = "id") final long id) {
        return canvasRepository.findById(id);
    }

    @GetMapping("/department/{department}")
    public Canvas findByDepartmentName(@PathVariable(value = "department") final String department) {

        return canvasRepository.findByDepartment_Name(department);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public Canvas update(@RequestBody final Canvas canvas) {

        return canvasService.update(canvas);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public Canvas create(@RequestBody final Canvas canvas) {

        Department department = departmentRepository.findAllByNameEquals(canvas.getDepartment().getName());

        canvas.setDepartment(department);
        canvas.setChairs(null);
        canvas.setCoffeeMachine(null);

        return canvasRepository.save(canvas);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/chair")
    public ApiResponse deleteChairFromCanvas(
            @RequestParam(value = "chairId") final long chairId,
            @RequestParam(value = "canvasId") final long canvasId) {

        return canvasService.removeChair(chairId, canvasId);
    }
}
