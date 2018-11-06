package nl.hhs.project.koffieapp.koffieapp.model.Canvas;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hhs.project.koffieapp.koffieapp.model.User;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Data
@NoArgsConstructor
public class Chair {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chair_id")
    private long id;
    private int topPos;
    private int leftPos;
    private int rotation;
    @OneToOne
    @Null
    private User user;

}
