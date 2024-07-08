package uniqueproject.uz.go2uzproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import uniqueproject.uz.go2uzproject.dto.response.UserResponse;
import uniqueproject.uz.go2uzproject.entity.enums.UserRole;
import uniqueproject.uz.go2uzproject.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/update-user-role/{userId}/role")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable UUID userId,
            @RequestParam UserRole role) {
        UserResponse userResponse = userService.updateUserRole(userId, role);
        return ResponseEntity.ok(userResponse);
    }


    @GetMapping("/get-role")
    public Object getCurrentUser2(Authentication authentication) {
        return authentication.getAuthorities();
    }


    @GetMapping("/get-id")
    public Object getCurrentUser4(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());

    }

}
