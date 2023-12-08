package com.kpi.springboot_laba_6.exception.handler;

import com.kpi.springboot_laba_6.exception.*;
import com.kpi.springboot_laba_6.exception.info.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ CouldNotDeleteException.class,
            CouldNotCreateException.class,
            CouldNotUpdateException.class,
            DataFormattingException.class})
    public ResponseEntity<ErrorInfo> handleRuntimeException(RuntimeException e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.setTimestamp(new Date().getTime());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundException(NotFoundException e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorInfo.setTimestamp(new Date().getTime());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(Exception e) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setTimestamp(new Date().getTime());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
