package com.cherrypicks.tcc.model;

public class CouponRedeemQuotaMap extends BaseModel {

	private static final long serialVersionUID = 8881118109869885804L;

	private Long couponId;

	private Integer redeemQuotaType;

	private Double redeemQuotaQty;

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getRedeemQuotaType() {
		return redeemQuotaType;
	}

	public void setRedeemQuotaType(Integer redeemQuotaType) {
		this.redeemQuotaType = redeemQuotaType;
	}

	public Double getRedeemQuotaQty() {
		return redeemQuotaQty;
	}

	public void setRedeemQuotaQty(Double redeemQuotaQty) {
		this.redeemQuotaQty = redeemQuotaQty;
	}

	public enum CouponRedeemQuota {
		DAY(1), MONTH(2), YEAR(3), CAMPAIGN(4), COUPON_PERIOD(5), ONCE(6);

		private final int value;

		private CouponRedeemQuota(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

}