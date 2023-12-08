package com.kpi.springboot_laba_6.exception;

public class DataFormattingException extends RuntimeException{
    public DataFormattingException(String message) {
        super(message);
    }
}
