package com.example.back_end.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.back_end.custom_exceptions.InvalidCredentialsException;
import com.example.back_end.custom_exceptions.InvalidTokenException;
import com.example.back_end.custom_exceptions.ValidationException;
import com.example.back_end.dtos.ForgotPasswordRequest;
import com.example.back_end.dtos.LoginUser;
import com.example.back_end.dtos.ResetPasswordDTO;
import com.example.back_end.dtos.SignUpUser;
import com.example.back_end.models.ApiResponse;
import com.example.back_end.models.User;
import com.example.back_end.services.UserService;
import com.example.back_end.utils.ValidationUtil;

@Controller
@RequestMapping("/api/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
        logger.debug("Authenticated user: " + authentication.getName());
    } else {
        logger.debug("No authenticated user");
    }
    return "home";
    }

    // SignUp Page
    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        model.addAttribute("user", new SignUpUser());
        return "register";
    }

    // Login Page
    @GetMapping("/login-form")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new LoginUser());
        return "login";
    }

    // Register New User
    @PostMapping("/register")
    public String registerUser(@ModelAttribute SignUpUser user, Model model) {
        try {
            ValidationUtil.validateSignUpUser(user);
            ApiResponse response = userService.register(user);
            if (response.isSuccess()) {
                model.addAttribute("success", response.getMessage());
                return "redirect:/api/auth/login-form";
            }
            model.addAttribute("error", response.getMessage());
        } catch (ValidationException e) {
            logger.error("Validation error during registration: {}", e.getErrors());
            model.addAttribute("errors", e.getErrors());
        } catch (Exception e) {
            logger.error("Unexpected error during registration: {}", e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
        }
        return "register";
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginUser user) {
        logger.info("password is: "+ user.getPassword());
        logger.info("username is: "+ user.getUserName());
        try {
            ValidationUtil.validateLoginUser(user);
            logger.info("enter userService");
            ApiResponse apiResponse = userService.verifyUser(user);
            logger.info("exited userService");
            if (apiResponse.isSuccess()) {
                String jwtToken = apiResponse.getJwtToken();
                logger.error("JWT Token generated: {}", jwtToken);
                // Returning the JWT token as part of the JSON response
                return ResponseEntity.ok(new ApiResponse("success", true, jwtToken));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid login credentials", false, null));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid login credentials", false, null));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Validation error"+ e.getErrors(), false, null));
        } catch (Exception e) {
            logger.error("Unexpected error during login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An unexpected error occurred. Please try again.", false, null));
        }
    }


    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestHeader(value = "Authorization") String token) {

        logger.error("token is: {}", token);
        ApiResponse response = userService.verifyToken(token);

        boolean isValid = response.isSuccess();
        if (isValid) {
            return ResponseEntity.ok(ApiResponse.success("Token is valid"));
        } else {
            return ResponseEntity.status(401).body(ApiResponse.failure("Token is invalid or expired"));
        }
    }

    // Forgot Password Page
    @GetMapping("/forgot-password-form")
    public String getForgotPasswordPage(Model model) {
        model.addAttribute("user", new ForgotPasswordRequest());
        return "forgot-password";
    }

    // Forgot Password Process
    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute ForgotPasswordRequest request, Model model) {
        try {
            ValidationUtil.validatePhoneOrEmail(request);
            ApiResponse response = userService.requestPasswordReset(request);
            if (response.isSuccess()) {
                model.addAttribute("success", response.getMessage());
                return "redirect:/api/auth/reset-password-form";
            }
            model.addAttribute("error", response.getMessage());
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getErrors());
        } catch (Exception e) {
            logger.error("Unexpected error during password reset: {}", e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
        }
        return "forgot-password";
    }

    // Reset Password Page
    @GetMapping("/reset-password-form")
    public String getResetPasswordPage(Model model) {
        model.addAttribute("user", new ResetPasswordDTO());
        return "reset-password";
    }

    // Reset Password Process
    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordDTO user, Model model) {
        try {
            if (!user.getNewPassword().equals(user.getConfirmPassword())) {
                throw new ValidationException("Passwords do not match.", List.of("Passwords do not match."));
            }

            ValidationUtil.validatePassword(user.getNewPassword());
            ApiResponse response = userService.resetPassword(user);
            if (response.isSuccess()) {
                model.addAttribute("success", response.getMessage());
                return "redirect:/api/auth/login-form";
            }
            model.addAttribute("error", "Password reset failed. Please try again.");
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getErrors());
        } catch (InvalidTokenException e) {
            model.addAttribute("error", "Invalid or expired reset token. Please request a new one.");
        } catch (Exception e) {
            logger.error("Unexpected error during password reset: {}", e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
        }
        return "reset-password";
    }

    // Endpoint to get user information
    @GetMapping("/user-info")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        User user = userService.getUserByUserName(authHeader);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null); // User not found
        }
    }
}
