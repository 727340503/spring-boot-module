package com.cherrypicks.tcc.model;

public class CouponCollectQuotaMap extends BaseModel {

	private static final long serialVersionUID = -3707423548602834614L;

	private Long couponId;

	private Integer collectQuotaType;

	private Double collectQuotaQty;

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getCollectQuotaType() {
		return collectQuotaType;
	}

	public void setCollectQuotaType(Integer collectQuotaType) {
		this.collectQuotaType = collectQuotaType;
	}

	public Double getCollectQuotaQty() {
		return collectQuotaQty;
	}

	public void setCollectQuotaQty(Double collectQuotaQty) {
		this.collectQuotaQty = collectQuotaQty;
	}

	public enum CouponCollectQuota {
		DAY(1), MONTH(2), YEAR(3), CAMPAIGN(4), COUPON_PERIOD(5), ONCE(6);

		private final int value;

		private CouponCollectQuota(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

}