package com.example.bookstoreapp.exceptions;

public class UserRegistrationCustomException extends RuntimeException{
    public UserRegistrationCustomException(String message) {
        super(message);
    }
}
