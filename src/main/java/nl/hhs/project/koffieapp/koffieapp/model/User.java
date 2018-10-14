package nl.hhs.project.koffieapp.koffieapp.model;

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
    private byte[] avatar;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles;

    @ManyToMany
    @JoinTable(name = "coffee_group_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<CoffeeGroup> coffeeGroups;

    public User(User byEMail){
        this.id = byEMail.getId();
        this.email = byEMail.getEmail();
        this.password = byEMail.getPassword();
        this.roles = byEMail.getRoles();
    }

}
