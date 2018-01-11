package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushNotifNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public PushNotifNotExistException() {
		super();
	}

	public PushNotifNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public PushNotifNotExistException(final String message) {
		super(message);
	}

	public PushNotifNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.PUSH_NOTIF_NOT_EXIST;
	}
}
