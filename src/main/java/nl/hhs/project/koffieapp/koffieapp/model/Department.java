package nl.hhs.project.koffieapp.koffieapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    @JsonIgnore
    private long id;

    private String name;

    @ManyToMany(mappedBy = "departments")
    @JsonIgnore
    private List<User> users;

    public Department(String name){
        this.name = name;
    }

}
