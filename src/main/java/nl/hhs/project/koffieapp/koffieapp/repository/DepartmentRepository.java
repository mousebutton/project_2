package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
