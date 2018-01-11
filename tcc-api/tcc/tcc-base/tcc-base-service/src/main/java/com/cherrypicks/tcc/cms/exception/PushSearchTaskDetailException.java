package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushSearchTaskDetailException extends BaseException {

	private static final long serialVersionUID = -965526249241892579L;

	public PushSearchTaskDetailException() {
        super();
    }

    public PushSearchTaskDetailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PushSearchTaskDetailException(final String message) {
        super(message);
    }

    public PushSearchTaskDetailException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return CmsCodeStatus.PUSH_SEARCH_TASK_DETAIL_ERROR;
    }
}
