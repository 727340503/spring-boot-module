package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class ReportDatePeriodException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public ReportDatePeriodException() {
		super();
	}

	public ReportDatePeriodException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ReportDatePeriodException(final String message) {
		super(message);
	}

	public ReportDatePeriodException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.REPORT_DATE_PERIOD_EXCEPTION;
	}
}
