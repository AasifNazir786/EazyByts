package com.example.back_end.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.example.back_end.controllers.UserController;
import com.example.back_end.custom_exceptions.ValidationException;
import com.example.back_end.dtos.ForgotPasswordRequest;
import com.example.back_end.dtos.LoginUser;
import com.example.back_end.dtos.SignUpUser;


public class ValidationUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Helper method for accumulating errors
    private static void addError(String message, List<String> errors, Logger logger) {
        logger.error(message);
        errors.add(message);
    }

    public static void validateSignUpUser(SignUpUser user, Model model) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (user.getFirstName() == null || user.getFirstName().length() < 3) {
            addError("First name must be at least 3 characters long.", errors, logger);
        }
        if (user.getLastName() == null || user.getLastName().length() < 3) {
            addError("Last name must be at least 3 characters long.", errors, logger);
        }
        if (user.getUserName() == null || user.getUserName().length() < 3) {
            addError("Username must be at least 3 characters long.", errors, logger);
        }
        if (user.getPassword() == null || !isValidPassword(user.getPassword())) {
            addError("Password must be at least 8 characters long, contain one special character, and one digit.", errors, logger);
        }
        if (user.getEmail() == null || !isValidEmail(user.getEmail())) {
            addError("Invalid email format.", errors, logger);
        }
        if (user.getPhoneNumber() == null || !isValidPhoneNumber(user.getPhoneNumber())) {
            addError("Phone number must be 10 digits.", errors, logger);
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            throw new ValidationException("SignUp validation failed", errors);
        }

        logger.info("SignUpUser validation successful.");
    }

    public static void validateLoginUser(LoginUser user, Model model) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (user.getUserName() == null || user.getUserName().length() < 3) {
            addError("Username must be at least 3 characters long.", errors, logger);
        }
        if (user.getPassword() == null || !isValidPassword(user.getPassword())) {
            addError("Password must be at least 8 characters long, contain one special character, and one digit.", errors, logger);
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            throw new ValidationException("Login validation failed", errors);
        }

        logger.info("LoginUser validation successful.");
    }

    public static void validatePhoneOrEmail(ForgotPasswordRequest user, Model model) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (user.getEmailOrPhone() == null) {
            addError("Email or phone number is required.", errors, logger);
        } else if (user.getEmailOrPhone().contains("@")) {
            if (!isValidEmail(user.getEmailOrPhone())) {
                addError("Invalid email format.", errors, logger);
            }
        } else {
            if (!isValidPhoneNumber(user.getEmailOrPhone())) {
                addError("Phone number must be 10 digits.", errors, logger);
            }
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            throw new ValidationException("ForgotPassword validation failed", errors);
        }

        logger.info("ForgotPasswordRequest validation successful.");
    }

    public static void validatePassword(String password, Model model) throws ValidationException {
        if (password == null || !isValidPassword(password)) {
            String error = "Password must be at least 8 characters long, contain one special character, and one digit.";
            logger.error(error);
            model.addAttribute("error", error);
            throw new ValidationException("Password validation failed", List.of(error));
        }

        logger.info("Password validation successful.");
    }

    // Helper methods for validation
    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("\\s", "").matches("^[0-9]{10}$");
    }

    private static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-\\[\\]{}|:;\"'<>,.?/]).{8,}$");
    }
}
