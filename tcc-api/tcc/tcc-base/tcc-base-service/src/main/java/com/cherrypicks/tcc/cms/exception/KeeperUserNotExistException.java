package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class KeeperUserNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public KeeperUserNotExistException() {
		super();
	}

	public KeeperUserNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public KeeperUserNotExistException(final String message) {
		super(message);
	}

	public KeeperUserNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.KEEPER_USER_NOT_EXIST_EXCEPTION;
	}
}
