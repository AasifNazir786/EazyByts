package com.example.back_end.models;

public class ApiResponse {

    private String message;
    private boolean success;

    public ApiResponse(){}

    public ApiResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    // Factory method for success response
    public static ApiResponse success(String message) {
        return new ApiResponse(message, true);
    }

    // Factory method for failure response
    public static ApiResponse failure(String message) {
        return new ApiResponse(message, false);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
