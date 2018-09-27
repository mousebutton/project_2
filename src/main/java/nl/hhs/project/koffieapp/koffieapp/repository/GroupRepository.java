package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.CoffeeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<CoffeeGroup, Long> {
}
