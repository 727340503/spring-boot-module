package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class BannerNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public BannerNotExistException() {
		super();
	}

	public BannerNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BannerNotExistException(final String message) {
		super(message);
	}

	public BannerNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.BANNER_NOT_EXIST;
	}
}
