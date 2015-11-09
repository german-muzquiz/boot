package com.gmr.boot.services;


import com.gmr.boot.BootException;
import org.springframework.http.HttpStatus;


public class LogicException extends BootException {
    public LogicException(HttpStatus httpStatusResponse, String message) {
        super(httpStatusResponse, message);
    }

    public LogicException(HttpStatus httpStatusResponse, String message, Throwable cause) {
        super(httpStatusResponse, message, cause);
    }
}
