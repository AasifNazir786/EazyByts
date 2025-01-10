package com.example.back_end.mappers;

import com.example.back_end.dtos.SignUpUser;
import com.example.back_end.models.User;

public class UserMapper {

    // Map User entity to SignUpUserDTO
    public static SignUpUser toUserDto(User user) {
        SignUpUser signUpUser = new SignUpUser();
        signUpUser.setId(user.getId());
        signUpUser.setFirstName(user.getFirstName());
        signUpUser.setLastName(user.getLastName());
        signUpUser.setUserName(user.getUserName());
        signUpUser.setEmail(user.getEmail());
        signUpUser.setPhoneNumber(user.getPhoneNumber());
        
        // Convert LocalDate to String for DTO
        signUpUser.setDateOfBirth(user.getDateOfBirth() != null ? user.getDateOfBirth() : null);
        
        signUpUser.setAccountStatus(user.getAccountStatus());
        signUpUser.setRole(user.getRole());
        return signUpUser;
    }

    // Map SignUpUserDTO to User entity
    public static User toUser(SignUpUser userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        // Convert dateOfBirth from String (DTO) to LocalDate (Entity)
        if (userDto.getDateOfBirth() != null && !userDto.getDateOfBirth().toString().isEmpty()) {
            user.setDateOfBirth(userDto.getDateOfBirth());
        }

        user.setAccountStatus(userDto.getAccountStatus());
        user.setRole(userDto.getRole());
        
        return user;
    }
}
