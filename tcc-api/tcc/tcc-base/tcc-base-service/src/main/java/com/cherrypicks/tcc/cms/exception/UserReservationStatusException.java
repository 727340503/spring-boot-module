package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class UserReservationStatusException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public UserReservationStatusException() {
		super();
	}

	public UserReservationStatusException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserReservationStatusException(final String message) {
		super(message);
	}

	public UserReservationStatusException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.USER_RESERVATION_STATUS;
	}
}
