package com.example.back_end.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.back_end.custom_exceptions.InvalidCredentialsException;
import com.example.back_end.custom_exceptions.TokenExpiredException;
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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    /**
     * Register a new user.
     */
    @Transactional
    public ApiResponse register(SignUpUser signUpUser) {
        validateUserUniqueness(signUpUser);

        User user = UserMapper.toUser(signUpUser);
        user.setPassword(signUpUser.getPassword()); // Encrypt password
        userRepository.save(user);

        logger.info("User registered successfully: {}", user.getUserName());
        return ApiResponse.success("User registered successfully");
    }

    /**
     * Validate user login credentials.
     */
    public ApiResponse verifyUser(LoginUser loginUser) {
        User user = userRepository.findByUserName(loginUser.getUserName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Verify password
        if (!loginUser.getPassword().equals(user.getPassword())) {
            logger.error("Invalid login attempt for username: {}", loginUser.getUserName());
            throw new InvalidCredentialsException("Invalid login credentials");
        }

        logger.info("User logged in successfully: {}", loginUser.getUserName());
        return ApiResponse.success("Login successful");
    }

    /**
     * Request password reset (via email or phone).
     */
    public ApiResponse requestPasswordReset(ForgotPasswordRequest request) {
        User user = findUserByEmailOrPhone(request.getEmailOrPhone());

        String resetToken = generateResetToken();
        user.setResetToken(resetToken);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(5)); // Token validity: 5 minutes
        userRepository.save(user);

        sendResetNotification(user);

        logger.info("Password reset requested for user: {}", user.getUserName());
        return ApiResponse.success("Password reset link sent to your email/phone");
    }

    /**
     * Reset the user's password.
     */
    @Transactional
    public ApiResponse resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userRepository.findByResetToken(resetPasswordDTO.getResetToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid reset token"));

        validateResetTokenExpiry(user);

        user.setPassword(resetPasswordDTO.getNewPassword()); // Encrypt password
        clearResetToken(user);

        userRepository.save(user);

        logger.info("Password reset successful for user: {}", user.getUserName());
        return ApiResponse.success("Password reset successful");
    }

    /**
     * Helper Methods.
     */

    private void validateUserUniqueness(SignUpUser signUpUser) {
        if (userRepository.existsByUserName(signUpUser.getUserName()) ||
            userRepository.existsByEmail(signUpUser.getEmail()) ||
            userRepository.existsByPhoneNumber(signUpUser.getPhoneNumber())) {
            logger.error("User already exists: {}", signUpUser.getUserName());
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    private User findUserByEmailOrPhone(String emailOrPhone) {
        if (emailOrPhone.contains("@")) {
            return userRepository.findByEmail(emailOrPhone)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } else {
            return userRepository.findByPhoneNumber(emailOrPhone)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        }
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    private void validateResetTokenExpiry(User user) {
        if (LocalDateTime.now().isAfter(user.getTokenExpiry())) {
            logger.error("Reset token expired for user: {}", user.getUserName());
            throw new TokenExpiredException("Reset token expired");
        }
    }

    private void clearResetToken(User user) {
        user.setResetToken(null);
        user.setTokenExpiry(null);
    }

    private void sendResetNotification(User user) {
        String message = "Hi " + user.getUserName() + ", here is your password reset link: " +
                "http://reset-password?token=" + user.getResetToken();

        // Example: Send Email/SMS (mocked as a print statement)
        logger.info("Notification sent to {}: {}", user.getEmail(), message);
    }
}
