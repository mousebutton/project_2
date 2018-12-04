package nl.hhs.project.koffieapp.koffieapp.model;

import lombok.Data;

@Data
public class CoffeeRequest {
    private String coffee;
    private Boolean milk;
    private Boolean sugar;
}
