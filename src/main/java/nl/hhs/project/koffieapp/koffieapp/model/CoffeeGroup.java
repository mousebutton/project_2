package nl.hhs.project.koffieapp.koffieapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CoffeeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    @JsonIgnore
    private long id;

    private String name;

    @ManyToMany(mappedBy = "coffeeGroups")
    @JsonIgnore
    private List<User> users;

}
