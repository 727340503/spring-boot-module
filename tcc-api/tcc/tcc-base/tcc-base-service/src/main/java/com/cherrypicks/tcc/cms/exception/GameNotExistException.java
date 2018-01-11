package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class GameNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public GameNotExistException() {
		super();
	}

	public GameNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GameNotExistException(final String message) {
		super(message);
	}

	public GameNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.GAME_NOT_EXIST;
	}
}
