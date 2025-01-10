package com.example.back_end.models;

public class ApiResponse {

    private String message;
    private boolean success;
    private String jwtToken;  // Changed name to indicate it's a JWT token

    public ApiResponse() {}

    public ApiResponse(String jwtToken){
        this.jwtToken = jwtToken;
    }

    public ApiResponse(String message, boolean success, String jwtToken) {
        this.message = message;
        this.success = success;
        this.jwtToken = jwtToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    // Factory method for success response with JWT token
    public static ApiResponse success(String message, String jwtToken) {
        return new ApiResponse(message, true, jwtToken);
    }

    // Factory method for success response without JWT token
    public static ApiResponse success(String message) {
        return new ApiResponse(message, true, null);
    }

    // Factory method for failure response
    public static ApiResponse failure(String message) {
        return new ApiResponse(message, false, null);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                (jwtToken != null ? ", jwtToken='" + jwtToken + '\'' : "") +
                '}';
    }
}
