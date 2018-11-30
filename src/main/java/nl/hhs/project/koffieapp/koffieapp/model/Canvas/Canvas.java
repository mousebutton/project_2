package nl.hhs.project.koffieapp.koffieapp.model.Canvas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hhs.project.koffieapp.koffieapp.model.Department;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@JsonSerialize
@NoArgsConstructor
public class Canvas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "canvas_id")
    private long id;

    private String description;

    @OneToOne
    private Department department;

    @OneToOne
    private CoffeeMachine coffeeMachine;

    @OneToMany
    private List<Chair> chairs;

    public void addChair(Chair chair) {
        chairs.add(chair);
    }

    public void removeChair(Chair chair) {
        chairs.remove(chair);
    }



}
