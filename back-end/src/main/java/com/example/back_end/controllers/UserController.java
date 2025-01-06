package com.example.back_end.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.back_end.custom_exceptions.InvalidCredentialsException;
import com.example.back_end.dtos.ForgotPasswordRequest;
import com.example.back_end.dtos.LoginUser;
import com.example.back_end.dtos.ResetPasswordDTO;
import com.example.back_end.dtos.SignUpUser;
import com.example.back_end.models.ApiResponse;
import com.example.back_end.services.UserService;

@Controller
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

        model.addAttribute("user", user);
        try {
            
            if(validatedUser(user, model)) {
                userService.register(user);
                System.out.println("User is valid");
                return "redirect:/api/auth/login-form";
                
            }
            System.out.println("User is not valid");
            return "register";

        } catch (Exception e) {

            System.out.println("User is not valid-> catch block");
            return "redirect:/register";
        }
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginUser user, Model model) {
        model.addAttribute("user", user);
        try {
            if (validatedUser(user, model)) {
                userService.verifyUser(user);
                return "redirect:/chat-room";
            }
            model.addAttribute("error", "Invalid login credentials.");
            return "login";

        } catch (InvalidCredentialsException e) {
            model.addAttribute("error", "Invalid login credentials.");
            return "redirect:/login";
        }
    }

    @GetMapping("/forgot-password-form")
    public String showForgotPasswordForm(Model model) {

        model.addAttribute("user", new ForgotPasswordRequest());

        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute ForgotPasswordRequest user, Model model) {

        model.addAttribute("user", user);
        try {

            if(validatedPhoneOrEmail(user, model)) {
                ApiResponse res = userService.requestPasswordReset(user);
                if(res.isSuccess()) {

                    return "redirect:/api/auth/reset-password-form";
                }
            }
        } catch (Exception e) {
            System.out.println("User is not valid -> catch block");
            return "redirect:/api/auth/forgot-password";
        }
        System.out.println("User is not valid");
        return "redirect:/api/auth/forgot-password-form";
    }

    @GetMapping("/reset-password-form")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("user", new ForgotPasswordRequest());
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordDTO user, Model model) {
        
        model.addAttribute("user", user);
        try {
            if(!user.getNewPassword().equals(user.getConfirmPassword())){
                model.addAttribute("error", "please enter same password");
            }
            if(validatePassword(user.getNewPassword(), model)){
                ApiResponse res = userService.resetPassword(user);
                System.out.println(res);
                if(res.isSuccess()) {
                    model.addAttribute("success", res.getMessage());
                    return "redirect:/api/auth/login-form";
                }
            }
            model.addAttribute("error", "Invalid reset token.");
            return "reset-password";
            
        } catch (Exception e) {
            model.addAttribute("error", "Invalid reset token.");
            return "redirect:/api/auth/reset-password-form";
        }
    }

    // Helper method to validate user input
    private boolean validatedUser(SignUpUser user, Model model) {

        if (user.getFirstName() == null || user.getFirstName().length() < 3) {
            model.addAttribute("error", "First name must be at least 3 characters long.");
            return false;
        }
        if (user.getLastName() == null || user.getLastName().length() < 3) {
            model.addAttribute("error", "Last name must be at least 3 characters long.");
            return false;
        }
        if (user.getUserName() == null || user.getUserName().length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters long.");
            return false;
        }
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-\\[\\]{}|:;\"'<>,.?/]).{8,}$")) {
            model.addAttribute("error", "Password must contain at least one special character and one digit.");
            return false;
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            model.addAttribute("error", "Invalid email format.");
            return false;
        }
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().replaceAll("\\s", "").matches("^[0-9]{10}$")) {
            model.addAttribute("error", "Phone number must be 10 digits.");
            return false;
        }
        return true;
    }

    // Helper method to validate user input
    private boolean validatedUser(LoginUser user, Model model) {

        if (user.getUserName() == null || user.getUserName().length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters long.");
            return false;
        }
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-\\[\\]{}|:;\"'<>,.?/]).{8,}$")) {
            model.addAttribute("error", "Password must contain at least one special character and one digit.");
            return false;
        }
        return true;
    }

    // Helper method to validate user input
    private boolean validatedPhoneOrEmail(ForgotPasswordRequest user, Model model) {

        if(user.getEmailOrPhone().contains("@")) {
            if (user.getEmailOrPhone() == null || !user.getEmailOrPhone().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                model.addAttribute("error", "Invalid email format.");
                return false;
            }
        }else{
            if (user.getEmailOrPhone() == null || !user.getEmailOrPhone().replaceAll("\\s", "").matches("^[0-9]{10}$")) {
                model.addAttribute("error", "Phone number must be 10 digits.");
                return false;
            }
        }
        return true;
    }


    private boolean validatePassword(String password, Model model){
        if (password == null || !password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+=\\-\\[\\]{}|:;\"'<>,.?/]).{8,}$")) {
            model.addAttribute("error", "Password must contain at least one special character and one digit.");
            return false;
        }
        return true;
    }
}
