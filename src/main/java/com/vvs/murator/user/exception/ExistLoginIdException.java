package com.vvs.murator.user.exception;


import com.vvs.murator.common.error.BusinessException;
import com.vvs.murator.common.error.ErrorCode;

public class ExistLoginIdException extends BusinessException {
    public ExistLoginIdException(String nickname) {
        super(nickname, ErrorCode.EXIST_USER_ID);
    }
}
