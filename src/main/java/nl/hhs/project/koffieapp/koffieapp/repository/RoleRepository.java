package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
