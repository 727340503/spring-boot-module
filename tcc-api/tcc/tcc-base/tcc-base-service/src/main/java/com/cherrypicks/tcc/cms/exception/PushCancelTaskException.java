package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushCancelTaskException extends BaseException {

	private static final long serialVersionUID = -9205906270632942686L;

	public PushCancelTaskException() {
        super();
    }

    public PushCancelTaskException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PushCancelTaskException(final String message) {
        super(message);
    }

    public PushCancelTaskException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.PUSH_CANCEL_TASK_ERROR;
    }
}
