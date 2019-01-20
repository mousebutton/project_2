package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.CoffeeOrder;
import nl.hhs.project.koffieapp.koffieapp.model.Department;
import nl.hhs.project.koffieapp.koffieapp.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<CoffeeOrder, Long> {

    List<CoffeeOrder> findAllByUserDepartmentAndFinishedIsFalseOrderByIdAsc(Department department);

    /**
     * Hoevaak heeft een gebruiker koffie besteld ?
     * @param userId
     * @return int
     */
    @Query(value = "select count(*) from coffee_order where user_user_id = ?1", nativeQuery = true)
    int getNumberOfCoffeeRequestsByUserId(long userId);



}
