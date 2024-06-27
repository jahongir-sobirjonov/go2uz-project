package uniqueproject.uz.go2uzproject.dto.request;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequest {

    @NotBlank(message = "Review content cannot be blank")
    @Size(max = 2000, message = "Review content cannot exceed 2000 characters")
    private String content;

    @NotNull(message = "Tour ID cannot be null")
    private UUID tourId;

}
