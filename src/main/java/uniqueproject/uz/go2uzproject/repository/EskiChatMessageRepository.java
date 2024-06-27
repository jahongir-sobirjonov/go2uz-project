package uniqueproject.uz.go2uzproject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uniqueproject.uz.go2uzproject.dto.request.ChatMessage;

import java.util.UUID;

public interface EskiChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
}
