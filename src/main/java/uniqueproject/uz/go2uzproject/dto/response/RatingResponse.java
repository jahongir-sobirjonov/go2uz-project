package uniqueproject.uz.go2uzproject.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingResponse {
    private UUID id;
    private String authorName;
    private Integer rating;

}

