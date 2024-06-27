package uniqueproject.uz.go2uzproject.dto.response;

import lombok.*;

@Getter
@Setter
public class PasswordResetDto {
    private String token;
    private String newPassword;
}