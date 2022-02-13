package com.chals.boot.exception;

import com.chals.boot.common.BaseException;
import com.chals.boot.common.ErrCode;

public class NotFoundCommentException extends BaseException {

    public NotFoundCommentException() {
        this.code = ErrCode.NOT_FOUND_COMMENT.getCode();
        this.msg = ErrCode.NOT_FOUND_COMMENT.getMsg();
        this.status = ErrCode.NOT_FOUND_COMMENT.getStatus();
    }
}
