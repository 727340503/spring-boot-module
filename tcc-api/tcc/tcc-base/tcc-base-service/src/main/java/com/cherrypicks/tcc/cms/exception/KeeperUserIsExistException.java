package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class KeeperUserIsExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public KeeperUserIsExistException() {
		super();
	}

	public KeeperUserIsExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public KeeperUserIsExistException(final String message) {
		super(message);
	}

	public KeeperUserIsExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.KEEPER_USER_IS_EXIST_EXCEPTION;
	}
}
