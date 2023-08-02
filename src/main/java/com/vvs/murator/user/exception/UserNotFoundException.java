package com.vvs.murator.user.exception;


import com.vvs.murator.common.error.BusinessException;
import com.vvs.murator.common.error.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String id) {
        super(id, ErrorCode.LOGIN_FAIL);
    }
}
