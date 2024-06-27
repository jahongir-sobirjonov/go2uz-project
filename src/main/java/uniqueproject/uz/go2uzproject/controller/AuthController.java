package uniqueproject.uz.go2uzproject.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniqueproject.uz.go2uzproject.config.security.JwtResponse;
import uniqueproject.uz.go2uzproject.dto.auth.SignUp;
import uniqueproject.uz.go2uzproject.dto.response.AuthDto;
import uniqueproject.uz.go2uzproject.entity.UserType;
import uniqueproject.uz.go2uzproject.service.AuthService;
import uniqueproject.uz.go2uzproject.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PermitAll
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUp signUp, UserType userType){
        return ResponseEntity.ok().body(authService.addUser(signUp, userType));
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@Valid @RequestBody AuthDto dto) {
        return authService.signIn(dto);
    }

    @PermitAll
    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}

