package nl.hhs.project.koffieapp.koffieapp.model.Canvas;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class CoffeeMachine extends CanvasObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coffee_machine_id")
    private long id;
}
