package com.chals.boot.exception;

import com.chals.boot.common.BaseException;
import com.chals.boot.common.ErrCode;

public class ForbiddenRequestException extends BaseException {

    public ForbiddenRequestException() {
        this.code = ErrCode.FORBIDDEN_REQUEST.getCode();
        this.msg = ErrCode.FORBIDDEN_REQUEST.getMsg();
        this.status = ErrCode.FORBIDDEN_REQUEST.getStatus();
    }
}
