package uniqueproject.uz.go2uzproject.dto.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignIn {
    private String email;
    private String password;
    private boolean rememberMe;
}
