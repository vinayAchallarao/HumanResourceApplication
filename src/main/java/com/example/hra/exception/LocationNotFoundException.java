package com.example.hra.exception;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(String message)
    {
        super(message);
    }
}