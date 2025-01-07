package com.example.back_end.custom_exceptions;

public class TokenExpiredException extends RuntimeException{
    
    public TokenExpiredException(String message){
        super(message);
    }
}
