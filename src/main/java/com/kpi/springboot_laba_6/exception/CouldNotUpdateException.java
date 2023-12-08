package com.kpi.springboot_laba_6.exception;


public class CouldNotUpdateException extends RuntimeException{
    public CouldNotUpdateException(String message) {
        super(message);
    }
}
