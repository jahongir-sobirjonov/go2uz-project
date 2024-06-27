package uniqueproject.uz.go2uzproject.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {
    private UUID id;
    private String name;
    private String surname;
    private String city;
    private String email;
    private String phoneNumber;
    private String telegramUsername;
    private String profilePhoto;
}
