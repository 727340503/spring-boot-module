package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushMsgException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public PushMsgException() {
		super();
	}

	public PushMsgException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public PushMsgException(final String message) {
		super(message);
	}

	public PushMsgException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.PUSH_SEND_MSG_EXCEPTION;
	}
}
