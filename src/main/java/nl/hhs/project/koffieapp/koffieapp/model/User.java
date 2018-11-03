package nl.hhs.project.koffieapp.koffieapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Lob
    private String avatar;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_FK")
    @JsonBackReference
    private Department department;

    public User(User byEMail) {
        this.id = byEMail.getId();
        this.email = byEMail.getEmail();
        this.password = byEMail.getPassword();
        this.roles = byEMail.getRoles();
    }

}
