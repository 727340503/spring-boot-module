package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushSearchTaskException extends BaseException {

	private static final long serialVersionUID = 289534216205327984L;

	public PushSearchTaskException() {
        super();
    }

    public PushSearchTaskException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PushSearchTaskException(final String message) {
        super(message);
    }

    public PushSearchTaskException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.PUSH_SEARCH_TASK_ERROR;
    }
}
