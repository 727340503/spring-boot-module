package com.cherrypicks.tcc.model;

public class CouponRedeemMethodMap extends BaseModel {

	private static final long serialVersionUID = -7268817857891703922L;

	private Long couponId;

	private Integer redeemMethod;

	private Double redeemMethodAmount;

	private Double redeemMethodQty;

	private String redeemProductCode;

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
	
	public enum CouponRedeemMethod {
		
		NO_LIMIT(0),
		BY_PURCHASE_PRODUCT(1),
		BY_PURCHASE_AMOUNT(2),
		BY_PURCHASE_PRODUCT_BRAND(3),
		BY_PURCHASE_PRODUCT_AMOUNT(4),
		BY_PURCHASE_PRODUCT_BRAND_AMOUNT(5);
		
		private final int value;

		private CouponRedeemMethod(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

}