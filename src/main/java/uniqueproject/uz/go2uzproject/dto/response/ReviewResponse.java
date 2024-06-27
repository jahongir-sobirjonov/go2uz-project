package uniqueproject.uz.go2uzproject.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {
    private UUID id;
    private String authorName;
    private String content;

//    private List<ReviewResponse> replies; // Replies as nested reviews
}
