package nl.hhs.project.koffieapp.koffieapp.model.Canvas;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hhs.project.koffieapp.koffieapp.model.Department;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Canvas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "canvas_id")
    private long id;

    @OneToOne
    private Department department;

    @OneToMany
    private List<Chair> chairs;
}
