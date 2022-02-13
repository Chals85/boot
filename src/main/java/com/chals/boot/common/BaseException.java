package com.chals.boot.common;

import com.chals.boot.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException implements CustomException {
    protected String code;                  // 에러코드
    protected String msg;                   // 메시지
    protected HttpStatus status;            // 응답코드
}
