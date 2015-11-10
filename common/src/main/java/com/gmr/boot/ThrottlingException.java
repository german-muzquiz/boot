package com.gmr.boot;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Maximum limit of requests reached")
public class ThrottlingException extends BootException {

    public ThrottlingException() {
        super(HttpStatus.FORBIDDEN, "Maximum limit of requests reached");
    }

}
