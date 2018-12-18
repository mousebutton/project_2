package nl.hhs.project.koffieapp.koffieapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CoffeeRound {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coffee_round_id")
    private long id;

    @ManyToOne
    private User user;

    @OneToMany
    private List<CoffeeOrder> orders;

    @CreationTimestamp
    private LocalDateTime orderDate;

}
