package com.kpi.springboot_laba_6.exception;

public class CouldNotCreateException extends RuntimeException{
    public CouldNotCreateException(String message) {
        super(message);
    }
}