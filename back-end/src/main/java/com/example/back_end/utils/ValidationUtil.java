package com.example.back_end.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.back_end.custom_exceptions.ValidationException;
import com.example.back_end.dtos.ForgotPasswordRequest;
import com.example.back_end.dtos.LoginUser;
import com.example.back_end.dtos.SignUpUser;

public class ValidationUtil {

    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

    // Helper method for accumulating errors
    private static void addError(String message, List<String> errors) {
        errors.add(message);
    }

    public static void validateSignUpUser(SignUpUser user) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (isInvalid(user.getFirstName(), 3)) {
            addError("First name must be at least 3 characters long.", errors);
        }
        if (isInvalid(user.getLastName(), 3)) {
            addError("Last name must be at least 3 characters long.", errors);
        }
        if (isInvalid(user.getUserName(), 3)) {
            addError("Username must be at least 3 characters long.", errors);
        }
        if (user.getPassword() == null || !isValidPassword(user.getPassword())) {
            addError("Password must be at least 8 characters long, contain one special character, and one digit.", errors);
        }
        if (!isValidEmail(user.getEmail())) {
            addError("Invalid email format.", errors);
        }
        if (!isValidPhoneNumber(user.getPhoneNumber())) {
            addError("Phone number must be 10 digits.", errors);
        }

        if (!errors.isEmpty()) {
            logger.error("SignUp validation failed with errors: {}", errors);
            throw new ValidationException("SignUp validation failed", errors);
        }

        logger.info("SignUpUser validation successful.");
    }

    public static void validateLoginUser(LoginUser user) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (isInvalid(user.getUserName(), 3)) {
            addError("Username must be at least 3 characters long.", errors);
        }
        if (user.getPassword() == null || !isValidPassword(user.getPassword())) {
            addError("Password must be at least 8 characters long, contain one special character, and one digit.", errors);
        }

        if (!errors.isEmpty()) {
            logger.error("Login validation failed with errors: {}", errors);
            throw new ValidationException("Login validation failed", errors);
        }

        logger.info("LoginUser validation successful.");
    }

    public static void validatePhoneOrEmail(ForgotPasswordRequest request) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (request.getEmailOrPhone() == null) {
            addError("Email or phone number is required.", errors);
        } else if (request.getEmailOrPhone().contains("@")) {
            if (!isValidEmail(request.getEmailOrPhone())) {
                addError("Invalid email format.", errors);
            }
        } else {
            if (!isValidPhoneNumber(request.getEmailOrPhone())) {
                addError("Phone number must be 10 digits.", errors);
            }
        }

        if (!errors.isEmpty()) {
            logger.error("ForgotPassword validation failed with errors: {}", errors);
            throw new ValidationException("ForgotPassword validation failed", errors);
        }

        logger.info("ForgotPasswordRequest validation successful.");
    }

    public static void validatePassword(String password) throws ValidationException {
        if (password == null || !isValidPassword(password)) {
            String error = "Password must be at least 8 characters long, contain one special character, and one digit.";
            logger.error("Password validation failed: {}", error);
            throw new ValidationException("Password validation failed", List.of(error));
        }

        logger.info("Password validation successful.");
    }

    // Helper methods for validation
    private static boolean isInvalid(String value, int minLength) {
        return value == null || value.length() < minLength;
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.replaceAll("\\s", "").matches("^[0-9]{10}$");
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-\\[\\]{}|:;\"'<>,.?/]).{8,}$");
    }
}
