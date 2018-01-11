package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class UserReservationNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public UserReservationNotExistException() {
		super();
	}

	public UserReservationNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserReservationNotExistException(final String message) {
		super(message);
	}

	public UserReservationNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.USER_RESERVATION_NOT_EXIST;
	}
}
