package com.cherrypicks.tcc.cms.dto;

public class CouponRedeemMethodDTO extends BaseObject {

	private static final long serialVersionUID = -7268817857891703922L;

	private Long id;

	private Long couponId;

	private Integer redeemMethod;

	private Double redeemMethodAmount;

	private Double redeemMethodQty;

	private String redeemProductCode;

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

	public Integer getRedeemMethod() {
		return redeemMethod;
	}

	public void setRedeemMethod(Integer redeemMethod) {
		this.redeemMethod = redeemMethod;
	}

	public Double getRedeemMethodAmount() {
		return redeemMethodAmount;
	}

	public void setRedeemMethodAmount(Double redeemMethodAmount) {
		this.redeemMethodAmount = redeemMethodAmount;
	}

	public Double getRedeemMethodQty() {
		return redeemMethodQty;
	}

	public void setRedeemMethodQty(Double redeemMethodQty) {
		this.redeemMethodQty = redeemMethodQty;
	}

	public String getRedeemProductCode() {
		return redeemProductCode;
	}

	public void setRedeemProductCode(String redeemProductCode) {
		this.redeemProductCode = redeemProductCode;
	}

}