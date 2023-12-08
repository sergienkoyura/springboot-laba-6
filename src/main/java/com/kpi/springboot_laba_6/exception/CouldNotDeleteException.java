package com.kpi.springboot_laba_6.exception;

public class CouldNotDeleteException extends RuntimeException{
    public CouldNotDeleteException(String message) {
        super(message);
    }
}
