package com.cherrypicks.tcc.cms.dto;

public class CouponCollectQuotaDTO extends BaseObject {

	private static final long serialVersionUID = -3707423548602834614L;

	private Long id;

	private Long couponId;

	private Integer collectQuotaType;

	private Double collectQuotaQty;

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

}