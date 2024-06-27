package uniqueproject.uz.go2uzproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.request.UserProfileRequest;
import uniqueproject.uz.go2uzproject.dto.response.UserProfileResponse;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.exception.DataNotFoundException;
import uniqueproject.uz.go2uzproject.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponse getUserProfile(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .city(user.getCity())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .telegramUsername(user.getTelegramUsername())
                .profilePhoto(user.getProfilePhoto())
                .build();
    }

    public UserProfileResponse updateUserProfile(UUID userId, UserProfileRequest profileRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

//        // Check if the current password matches the user's password
//        if (!passwordEncoder.matches(profileRequest.getCurrentPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("Invalid current password");
//        }

        if (profileRequest.getName() != null) {
            user.setName(profileRequest.getName());
        }
        if (profileRequest.getSurname() != null) {
            user.setSurname(profileRequest.getSurname());
        }
        if (profileRequest.getCity() != null) {
            user.setCity(profileRequest.getCity());
        }
        if (profileRequest.getEmail() != null) {
            user.setEmail(profileRequest.getEmail());
        }
        if (profileRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(profileRequest.getPhoneNumber());
        }
        if (profileRequest.getTelegramUsername() != null) {
            user.setTelegramUsername(profileRequest.getTelegramUsername());
        }
        if (profileRequest.getProfilePhoto() != null) {
            user.setProfilePhoto(profileRequest.getProfilePhoto());
        }

        // Save the updated user entity
        UserEntity updatedUser = userRepository.save(user);

        // Return the updated user profile response
        return UserProfileResponse.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .surname(updatedUser.getSurname())
                .city(updatedUser.getCity())
                .email(updatedUser.getEmail())
                .phoneNumber(updatedUser.getPhoneNumber())
                .telegramUsername(updatedUser.getTelegramUsername())
                .profilePhoto(updatedUser.getProfilePhoto())
                .build();
    }

}
