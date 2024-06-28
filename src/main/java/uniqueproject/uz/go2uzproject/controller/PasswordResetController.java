package uniqueproject.uz.go2uzproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import uniqueproject.uz.go2uzproject.dto.request.PasswordResetRequest;
import uniqueproject.uz.go2uzproject.dto.response.PasswordResetDto;
import uniqueproject.uz.go2uzproject.service.PasswordResetService;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        passwordResetService.sendPasswordResetLink(request.getEmail());
        return ResponseEntity.ok("Password reset link sent.");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        passwordResetService.resetPassword(passwordResetDto.getToken(), passwordResetDto.getNewPassword());
        return ResponseEntity.ok("Password has been reset.");
    }


}
