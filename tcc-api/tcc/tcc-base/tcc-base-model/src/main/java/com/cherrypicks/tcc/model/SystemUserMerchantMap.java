package com.cherrypicks.tcc.model;

public class SystemUserMerchantMap extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852718769238519521L;
	private Long userId;
	private Long merchantId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

}
