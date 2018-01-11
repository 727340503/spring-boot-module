package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class UserStampCardNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public UserStampCardNotExistException() {
		super();
	}

	public UserStampCardNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserStampCardNotExistException(final String message) {
		super(message);
	}

	public UserStampCardNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.USER_STAMP_CARD_NOT_EXIST;
	}
}
