package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushRegisterDeviceTokenException extends BaseException {

    private static final long serialVersionUID = -1930525565285514887L;

    public PushRegisterDeviceTokenException() {
        super();
    }

    public PushRegisterDeviceTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PushRegisterDeviceTokenException(final String message) {
        super(message);
    }

    public PushRegisterDeviceTokenException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.REGISTER_DEVICE_TOKEN_ERROR;
    }
}
