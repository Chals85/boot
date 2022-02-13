package com.chals.boot.exception;

import com.chals.boot.common.BaseException;
import com.chals.boot.common.ErrCode;

public class NotFoundBoardException extends BaseException {

    public NotFoundBoardException() {
        this.code = ErrCode.NOT_FOUND_BOARD.getCode();
        this.msg = ErrCode.NOT_FOUND_BOARD.getMsg();
        this.status = ErrCode.NOT_FOUND_BOARD.getStatus();
    }
}
