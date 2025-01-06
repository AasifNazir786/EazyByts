package com.example.back_end.dtos;

public class ForgotPasswordRequest {
    
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
