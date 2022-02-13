package com.chals.boot.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrCode {

    VALIDATION_ERROR("VALIDATION_ERROR", "요청하신 파라미터가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_BOARD("NOT_FOUND_BOARD", "존재하지 않는 게시글입니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_COMMENT("NOT_FOUND_COMMENT", "존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND),
    FORBIDDEN_REQUEST("FORBIDDEN_REQUEST", "허용되지 않은 요청입니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "내부 시스템 서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private String code;            // 에러코드
    private String msg;             // 메시지
    private HttpStatus status;      // 읍답코드

    ErrCode(String code, String msg, HttpStatus status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}
