package com.example.back_end.dtos;

import jakarta.validation.constraints.NotNull;

public class LoginUser {
    
    @NotNull(message = "Username is required")
    private String userName;

    @NotNull(message = "Password is required")
    private String password;

    public LoginUser() {
    }

    public LoginUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
