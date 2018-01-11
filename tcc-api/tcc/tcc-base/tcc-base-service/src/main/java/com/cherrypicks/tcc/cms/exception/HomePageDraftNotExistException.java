package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class HomePageDraftNotExistException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public HomePageDraftNotExistException() {
		super();
	}

	public HomePageDraftNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public HomePageDraftNotExistException(final String message) {
		super(message);
	}

	public HomePageDraftNotExistException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.HOME_PAGE_DRAFT_NOT_EXIST;
	}
}
