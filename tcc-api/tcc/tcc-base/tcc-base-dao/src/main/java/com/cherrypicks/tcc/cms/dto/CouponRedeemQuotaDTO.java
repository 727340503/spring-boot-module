package com.cherrypicks.tcc.cms.dto;

public class CouponRedeemQuotaDTO extends BaseObject {

	private static final long serialVersionUID = 8881118109869885804L;

	private Long id;

	private Long couponId;

	private Integer redeemQuotaType;

	private Double redeemQuotaQty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
}