package com.chals.boot.exception.handler;

import com.chals.boot.common.BaseController;
import com.chals.boot.common.ErrCode;
import com.chals.boot.dto.ResponseDto;
import com.chals.boot.exception.CustomException;
import com.chals.boot.exception.NotFoundBoardException;
import com.chals.boot.exception.NotFoundCommentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({
            NotFoundBoardException.class,
            NotFoundCommentException.class
    })
    private ResponseEntity<ResponseDto> handlerCustomerException(CustomException e) {
        return exceptionResponse(
                e.getCode(),
                e.getMsg(),
                e.getStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ResponseDto> handlerException(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        log.error("Internal Server Error. {}", writer);

        if(writer.toString().contains("Validation failed")) {
            return exceptionResponse(
                    ErrCode.VALIDATION_ERROR.getCode(),
                    ErrCode.VALIDATION_ERROR.getMsg(),
                    ErrCode.VALIDATION_ERROR.getStatus()
            );
        }

        return exceptionResponse(
                ErrCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrCode.INTERNAL_SERVER_ERROR.getMsg(),
                ErrCode.INTERNAL_SERVER_ERROR.getStatus()
        );
    }
}
