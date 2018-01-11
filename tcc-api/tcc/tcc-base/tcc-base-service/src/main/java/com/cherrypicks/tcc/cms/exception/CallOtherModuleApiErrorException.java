package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CallOtherModuleApiErrorException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CallOtherModuleApiErrorException() {
		super();
	}

	public CallOtherModuleApiErrorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CallOtherModuleApiErrorException(final String message) {
		super(message);
	}

	public CallOtherModuleApiErrorException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.CALL_OTHER_MODULE_ERROR;
	}
}
