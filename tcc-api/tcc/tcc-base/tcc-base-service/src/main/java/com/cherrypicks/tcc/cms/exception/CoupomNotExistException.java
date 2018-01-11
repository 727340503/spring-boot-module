package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CoupomNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CoupomNotExistException() {
		super();
	}

	public CoupomNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CoupomNotExistException(final String message) {
		super(message);
	}

	public CoupomNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.COUPON_NOT_EXIST;
	}
}
