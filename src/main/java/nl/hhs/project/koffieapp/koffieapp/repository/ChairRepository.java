package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChairRepository extends JpaRepository<Chair, Long> {

    List<Chair> findChairByUser_Email(String email);
}
