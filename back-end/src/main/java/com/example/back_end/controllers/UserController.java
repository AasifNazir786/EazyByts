package com.example.back_end.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.back_end.custom_exceptions.InvalidCredentialsException;
import com.example.back_end.custom_exceptions.InvalidTokenException;
import com.example.back_end.custom_exceptions.ValidationException;
import com.example.back_end.dtos.ForgotPasswordRequest;
import com.example.back_end.dtos.LoginUser;
import com.example.back_end.dtos.ResetPasswordDTO;
import com.example.back_end.dtos.SignUpUser;
import com.example.back_end.models.ApiResponse;
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
    public String homePage() {
        return "home";
    }

    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new SignUpUser());
        return "register";
    }

    @GetMapping("/login-form")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new LoginUser());
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute SignUpUser user, Model model) {
        try {
            model.addAttribute("user", user);
            ValidationUtil.validateSignUpUser(user, model);
            ApiResponse res = userService.register(user);
            if (res.isSuccess()) {
                model.addAttribute("success", res.getMessage());
                return "redirect:/api/auth/login-form";
            }
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register";
        } catch (ValidationException e) {
            logger.error("Validation errors during registration: {}", e.getErrors());
            model.addAttribute("errors", e.getErrors());
            return "register";
        } catch (Exception e) {
            logger.error("Unexpected error during registration: {}", e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "register";
        }
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginUser user, Model model) {
        try {
            logger.debug("Login attempt: {}", user);
            model.addAttribute("user", user);

            // Validate input
            ValidationUtil.validateLoginUser(user, model);
            logger.debug("Validation successful for user: {}", user.getUserName());

            // Verify credentials
            ApiResponse res = userService.verifyUser(user);
            logger.debug("Verification result: {}", res);

            if (res.isSuccess()) {
                model.addAttribute("success", res.getMessage());
                return "redirect:/api/auth/home";
            }

            model.addAttribute("error", "Invalid login credentials.");
            return "login";
        } catch (InvalidCredentialsException e) {
            logger.error("Invalid login attempt: {}", e.getMessage());
            model.addAttribute("error", "Invalid login credentials.");
            return "login";
        } catch (ValidationException e) {
            logger.error("Validation errors during login: {}", e.getErrors());
            model.addAttribute("errors", e.getErrors());
            return "login";
        } catch (Exception e) {
            logger.error("Unexpected error during login: {}", e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "login";
        }
    }

    @GetMapping("/forgot-password-form")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("user", new ForgotPasswordRequest());
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute ForgotPasswordRequest user, Model model) {
        try {
            // Validate the phone or email
            ValidationUtil.validatePhoneOrEmail(user, model);
            // Request password reset
            System.out.println("====================vnnn=========");
            ApiResponse res = userService.requestPasswordReset(user);
            System.out.println("---------------------svvd----------");
            // Handle success
            if (res.isSuccess()) {
                System.out.println("////////////////////////////////");
                model.addAttribute("success", res.getMessage());
                return "redirect:/api/auth/reset-password-form"; // Redirect to reset password form
            }

            // Handle failure response
            model.addAttribute("error", "Password reset request failed. Please try again.");
            return "forgot-password";
            
        } catch (ValidationException e) {
            // Handle validation errors
            logger.error("Validation errors during forgot password for user {}: {}", user, e.getErrors());
            model.addAttribute("errors", e.getErrors());
            return "forgot-password";
            
        } catch (Exception e) {
            // Handle unexpected errors
            logger.error("Unexpected error during password reset request for user {}: {}", user, e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "forgot-password";
        }
    }


    @GetMapping("/reset-password-form")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("user", new ResetPasswordDTO());
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordDTO user, Model model) {
        try {
            // Add user object to the model to pre-populate form fields after validation errors
            model.addAttribute("user", user);

            // Validate if passwords match
            if (!user.getNewPassword().equals(user.getConfirmPassword())) {
                throw new ValidationException("Passwords do not match.", List.of("Passwords do not match."));
            }

            // Validate the new password
            ValidationUtil.validatePassword(user.getNewPassword(), model);

            // Check if the reset token is valid
            ApiResponse res = userService.resetPassword(user);
            if (res.isSuccess()) {
                model.addAttribute("success", res.getMessage());
                return "redirect:/api/auth/login-form";  // Redirect to login form on success
            }

            // If password reset fails
            model.addAttribute("error", "Password reset failed. Please try again.");
            return "reset-password";
        } catch (ValidationException e) {
            logger.error("Validation errors during password reset: {}", e.getErrors());
            model.addAttribute("errors", e.getErrors());  // Add validation errors to model
            return "reset-password";  // Return back to the form
        } catch (InvalidTokenException e) {
            logger.error("Invalid token during password reset: {}", e.getMessage());
            model.addAttribute("error", "Invalid or expired reset token. Please request a new one.");
            return "reset-password";  // Return back to the form with error
        } catch (Exception e) {
            logger.error("Unexpected error during password reset: {}", e.getMessage(), e);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "reset-password";  // Return back to the form with error
        }
    }

}
