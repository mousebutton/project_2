package nl.hhs.project.koffieapp.koffieapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CoffeeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long id;

    private Boolean sugar;
    private Boolean milk;
    private String coffeeType;
}
