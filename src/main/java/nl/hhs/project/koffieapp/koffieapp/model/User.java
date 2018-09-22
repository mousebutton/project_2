package nl.hhs.project.koffieapp.koffieapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Column(unique = true)
    private String email;

    private String password;

}
