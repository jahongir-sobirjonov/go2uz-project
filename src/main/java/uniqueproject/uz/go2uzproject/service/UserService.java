package uniqueproject.uz.go2uzproject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.response.UserResponse;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.entity.enums.UserRole;
import uniqueproject.uz.go2uzproject.exception.DataNotFoundException;
import uniqueproject.uz.go2uzproject.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uniqueproject.uz.go2uzproject.entity.enums.UserRole.*;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponse updateUserRole(UUID userId, UserRole role) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new DataNotFoundException("User not found"));
            user.setRole(role);
            UserEntity updatedUserEntity = userRepository.save(user);
           return modelMapper.map(updatedUserEntity, UserResponse.class);
        }


    public  <T> T me(Principal principal) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(principal.getName()))
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        UserRole role = userEntity.getRole();
        if (role == AGENCY){
            UserResponse adminResponse = modelMapper.map(userEntity, UserResponse.class);
            adminResponse.setRole(AGENCY);
            return (T) adminResponse;
        } else if (role == USER) {
            UserResponse userResponse =  modelMapper.map(userEntity,UserResponse.class);
            userResponse.setRole(USER);
            return (T)userResponse;
        } else if (role == MANAGER) {
            UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
            userResponse.setRole(MANAGER);
            return (T) userResponse;
        } else if (role == SUPER_ADMIN) {
                UserResponse userResponse =  modelMapper.map(userEntity,UserResponse.class);
                userResponse.setRole(SUPER_ADMIN);
                return (T)userResponse;
            }

        return (T) modelMapper.map(userEntity,UserResponse.class);
    }


    public List<UserResponse> getAll() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (UserEntity userEntity : userRepository.findAll()) {
            UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
            userResponse.setId(userEntity.getId());
            userResponse.setRole(userEntity.getRole());
            userResponses.add(userResponse);
        }
        return userResponses;
    }
}
