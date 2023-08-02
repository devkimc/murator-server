package com.vvs.murator.user.exception;


import com.vvs.murator.common.error.BusinessException;
import com.vvs.murator.common.error.ErrorCode;

public class LoginFailException extends BusinessException {
    public LoginFailException(String loginId) {
        super(loginId, ErrorCode.LOGIN_FAIL);
    }
}
