package com.example.back_end.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ForgotPasswordRequest {
    
    @NotBlank(message = "Email or phone is required")
    @Pattern(
        regexp = "^(\\d{10}|[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$",
        message = "Provide a valid email address or 10-digit phone number"
    )
    private String emailOrPhone;

    public ForgotPasswordRequest() {}

    public ForgotPasswordRequest(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public String getEmailOrPhone() {
        return this.emailOrPhone;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    @Override
    public String toString() {
        return "{" +
            " emailOrPhone='" + getEmailOrPhone() + "'" +
            "}";
    }
}
