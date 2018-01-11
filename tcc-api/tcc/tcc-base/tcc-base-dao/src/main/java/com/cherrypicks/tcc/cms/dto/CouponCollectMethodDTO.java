package com.cherrypicks.tcc.cms.dto;

public class CouponCollectMethodDTO extends BaseObject {

	private static final long serialVersionUID = 2330304132899874698L;

	private Long id;

	private Long couponId;

	private Integer collectMethod;

	private Double collectMethodAmount;

	private Integer collectMethodQty;

	private String collectProductCode;

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

	public Integer getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(Integer collectMethod) {
		this.collectMethod = collectMethod;
	}

	public Double getCollectMethodAmount() {
		return collectMethodAmount;
	}

	public void setCollectMethodAmount(Double collectMethodAmount) {
		this.collectMethodAmount = collectMethodAmount;
	}

	public Integer getCollectMethodQty() {
		return collectMethodQty;
	}

	public void setCollectMethodQty(Integer collectMethodQty) {
		this.collectMethodQty = collectMethodQty;
	}

	public String getCollectProductCode() {
		return collectProductCode;
	}

	public void setCollectProductCode(String collectProductCode) {
		this.collectProductCode = collectProductCode;
	}
}