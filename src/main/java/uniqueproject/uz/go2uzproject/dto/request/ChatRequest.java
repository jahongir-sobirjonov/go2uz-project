package uniqueproject.uz.go2uzproject.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatRequest {
    private String sender;
    private String text;
//    private LocalDateTime timestamp;
}
