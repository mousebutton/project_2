package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.CoffeeOrder;
import nl.hhs.project.koffieapp.koffieapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findAllByUserDepartmentAndFinishedIsFalse(Department department);
}
