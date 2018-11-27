package nl.hhs.project.koffieapp.koffieapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hhs.project.koffieapp.koffieapp.model.Canvas.Canvas;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    @JsonManagedReference
    private List<User> users;

    public Department(String name) {
        this.name = name;
        this.users = new ArrayList<>();
    }

}
