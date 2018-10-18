package nl.hhs.project.koffieapp.koffieapp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_message_id")
    private long id;

    @OneToOne
    private User user;
    private String content;
    private LocalDateTime timestamp;

}
