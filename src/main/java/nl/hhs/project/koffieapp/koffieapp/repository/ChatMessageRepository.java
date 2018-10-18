package nl.hhs.project.koffieapp.koffieapp.repository;

import nl.hhs.project.koffieapp.koffieapp.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
