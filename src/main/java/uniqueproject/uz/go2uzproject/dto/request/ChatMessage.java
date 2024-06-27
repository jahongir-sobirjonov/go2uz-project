package uniqueproject.uz.go2uzproject.dto.request;

import jakarta.persistence.Entity;
import lombok.*;
import uniqueproject.uz.go2uzproject.entity.BaseEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity

public class ChatMessage extends BaseEntity {
    private String sender;
    private String text;
    private LocalDateTime timestamp;
}

