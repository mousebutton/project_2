package nl.hhs.project.koffieapp.koffieapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "department")
    @JsonManagedReference
    private List<User> users;


}
