package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanvasRepository extends JpaRepository<Canvas, Long> {

    Canvas findByDepartment_Name(String department);
}
