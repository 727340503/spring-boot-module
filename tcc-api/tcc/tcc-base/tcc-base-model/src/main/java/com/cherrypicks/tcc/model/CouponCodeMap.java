package com.cherrypicks.tcc.model;

public class CouponCodeMap extends BaseModel {

	private static final long serialVersionUID = -153937261156557105L;

	private Long merchantId;

	private Long couponId;

	private String externalCouponCode;

	private String couponCode;

	private Integer status;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getExternalCouponCode() {
		return externalCouponCode;
	}

	public void setExternalCouponCode(String externalCouponCode) {
		this.externalCouponCode = externalCouponCode;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public enum CouponCodeMapStatus {
		
		ACTIVE(1),INACTIVE(2),USED(3); 
		
		private final int value;

		private CouponCodeMapStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}
}