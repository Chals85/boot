package com.chals.boot.exception;

import org.springframework.http.HttpStatus;

public interface CustomException {
    String getCode();
    String getMsg();
    HttpStatus getStatus();
}
