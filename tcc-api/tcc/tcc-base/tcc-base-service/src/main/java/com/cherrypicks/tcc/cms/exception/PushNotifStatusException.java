package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class PushNotifStatusException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public PushNotifStatusException() {
		super();
	}

	public PushNotifStatusException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public PushNotifStatusException(final String message) {
		super(message);
	}

	public PushNotifStatusException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.PUSH_NOTIF_STATUS_EXCEPTION;
	}
}
