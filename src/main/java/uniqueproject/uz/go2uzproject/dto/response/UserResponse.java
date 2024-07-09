package uniqueproject.uz.go2uzproject.dto.response;

import lombok.*;
import uniqueproject.uz.go2uzproject.entity.UserType;
import uniqueproject.uz.go2uzproject.entity.enums.UserRole;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String surname;
    private String city;
    private String email;
    private String profilePhoto;
    private String phoneNumber;
    private String telegramUsername;
    private String password;
    private UserRole role;
}
