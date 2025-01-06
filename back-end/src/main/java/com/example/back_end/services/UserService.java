package com.example.back_end.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.back_end.custom_exceptions.InvalidCredentialsException;
import com.example.back_end.custom_exceptions.UserAlreadyExistsException;
import com.example.back_end.custom_exceptions.UserNotFoundException;
import com.example.back_end.dtos.ForgotPasswordRequest;
import com.example.back_end.dtos.LoginUser;
import com.example.back_end.dtos.ResetPasswordDTO;
import com.example.back_end.dtos.SignUpUser;
import com.example.back_end.mappers.UserMapper;
import com.example.back_end.models.ApiResponse;
import com.example.back_end.models.User;
import com.example.back_end.repositories.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    // private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        // this.passwordEncoder = passwordEncoder;
    }

    // Register method
    @Transactional
    public ApiResponse register(SignUpUser userDto) {
        if (userRepository.existsByUserName(userDto.getUserName()) ||
            userRepository.existsByEmail(userDto.getEmail()) ||
            userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        
        User user = UserMapper.toUser(userDto);
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return new ApiResponse("User Registered Successfully", true);
    }
    
    // Login method
    public ApiResponse verifyUser(LoginUser loginUser) {
        User user = userRepository.findByUserName(loginUser.getUserName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!loginUser.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid login credentials");
        }
        
        return new ApiResponse("Login Successful", true);
    }

    // Password Reset Request method
    public ApiResponse requestPasswordReset(ForgotPasswordRequest request) {
        User user;

        if(request.getEmailOrPhone().contains("@")) {
            user = userRepository.findByEmail(request.getEmailOrPhone())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } else {
            user = userRepository.findByPhoneNumber(request.getEmailOrPhone())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(5)); // Token expires in 5 minutes

        userRepository.save(user); // Don't forget to save the user with reset token
        
        sendResetNotification(user);
        
        return new ApiResponse("Password reset link sent to your email/phone", true);
    }

    // Reset password method
    public ApiResponse resetPassword(ResetPasswordDTO dto) {
        User user = userRepository.findByResetToken(dto.getResetToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid reset token"));

        if (LocalDateTime.now().isAfter(user.getTokenExpiry())) {
            throw new IllegalArgumentException("Token expired");
        }

        user.setPassword(dto.getNewPassword());
        user.setResetToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);

        return new ApiResponse("Password reset successful", true);
    }

    // Send reset notification (Email/SMS)
    private void sendResetNotification(User user) {
        
        String message = "Hi " + user.getUserName() + ", here is your password reset link: " +
                "http://reset-password?token=" + user.getResetToken();
                
        System.out.println("Sending to " + user.getEmail() + ": " + message);
        System.out.println(user.getResetToken());
    }
}
