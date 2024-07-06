package uniqueproject.uz.go2uzproject.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uniqueproject.uz.go2uzproject.dto.request.UserProfileRequest;
import uniqueproject.uz.go2uzproject.service.UserProfileService;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile(Principal principal) {

        return ResponseEntity.ok(userProfileService.getUserProfile(UUID.fromString(principal.getName())));
    }


    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileRequest updatedUser, Principal principal) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(UUID.fromString(principal.getName()), updatedUser));
    }
}
