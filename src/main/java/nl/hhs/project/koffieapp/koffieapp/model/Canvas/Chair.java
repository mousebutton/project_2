package nl.hhs.project.koffieapp.koffieapp.model.Canvas;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hhs.project.koffieapp.koffieapp.model.User;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Data
@NoArgsConstructor
public class Chair extends CanvasObject {

    @OneToOne
    @Null
    private User user;

}
