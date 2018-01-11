package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class KeeperUserStaffIdIsExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public KeeperUserStaffIdIsExistException() {
		super();
	}

	public KeeperUserStaffIdIsExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public KeeperUserStaffIdIsExistException(final String message) {
		super(message);
	}

	public KeeperUserStaffIdIsExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.KEEPER_USER_STAFF_ID_IS_EXIST_EXCEPTION;
	}
}
