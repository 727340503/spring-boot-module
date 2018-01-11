package com.cherrypicks.tcc.cms.exception;

import com.cherrypicks.tcc.util.CmsCodeStatus;

public class CouponQuotaQtyLittleException extends BaseException {


	private static final long serialVersionUID = -7574359076312016068L;

	public CouponQuotaQtyLittleException() {
		super();
	}

	public CouponQuotaQtyLittleException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CouponQuotaQtyLittleException(final String message) {
		super(message);
	}

	public CouponQuotaQtyLittleException(final Throwable cause) {
		super(cause);
	}

	@Override
	public int getErrorCode() {
		return CmsCodeStatus.COUPON_QUOTA_QTY_LITTLE;
	}
}
