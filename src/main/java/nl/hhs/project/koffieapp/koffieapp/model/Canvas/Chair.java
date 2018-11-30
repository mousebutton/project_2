package nl.hhs.project.koffieapp.koffieapp.model.Canvas;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hhs.project.koffieapp.koffieapp.model.User;
import org.hibernate.annotations.Fetch;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Data
@NoArgsConstructor
public class Chair extends CanvasObject {

    @OneToOne
    @Nullable
    private User user;

}
