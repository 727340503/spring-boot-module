package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushUnbindUserException extends BaseException {

    private static final long serialVersionUID = 4137450763127253479L;

    public PushUnbindUserException() {
        super();
    }

    public PushUnbindUserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PushUnbindUserException(final String message) {
        super(message);
    }

    public PushUnbindUserException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.PUSH_UNBIND_USER_ERROR;
    }
}
