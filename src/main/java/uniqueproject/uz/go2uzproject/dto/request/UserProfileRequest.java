package uniqueproject.uz.go2uzproject.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileRequest {
    private String name;
    private String surname;
    private String city;
    private String email;
    private String phoneNumber;
    private String telegramUsername;
    private String profilePhoto;
}
