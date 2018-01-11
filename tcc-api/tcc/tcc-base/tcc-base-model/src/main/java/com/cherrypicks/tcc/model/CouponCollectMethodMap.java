package com.cherrypicks.tcc.model;

public class CouponCollectMethodMap extends BaseModel {

	private static final long serialVersionUID = 2330304132899874698L;

	private Long couponId;

	private Integer collectMethod;

	private Double collectMethodAmount;

	private Integer collectMethodQty;

	private String collectProductCode;

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

	public enum CouponCollectMethod {
		REGISTRATION(1),
		FREE_TO_COLLECT(2), 
		BEACON(3),
		COMPLETE_PROFILE(4),
		PURCHASE_PRODUCT(5),
		PURCHASE_AMOUNT(6),
		PURCHASE_PRODUCT_BRAND(7),
		PURCHASE_PRODUCT_AMOUNT(8),
		PURCHASE_PRODUCT_BRAND_AMOUNT(9),
		REFERRAL(10),
		BY_STAMP(11),
		BY_PIONTS(12);
		
		private final int value;

		private CouponCollectMethod(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}
	
}