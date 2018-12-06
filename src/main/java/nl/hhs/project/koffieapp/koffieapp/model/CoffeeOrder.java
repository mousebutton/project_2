package nl.hhs.project.koffieapp.koffieapp.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class CoffeeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long id;

    @ManyToOne
    private User user;

    private Boolean sugar;
    private Boolean milk;
    private String coffeeType;

    @CreationTimestamp
    private LocalDateTime orderDate;

}
