package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class SubUserStampsException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public SubUserStampsException() {
		super();
	}

	public SubUserStampsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SubUserStampsException(final String message) {
		super(message);
	}

	public SubUserStampsException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.SUB_USER_STAMPS_EXCEPTION;
	}
}
