package com.cherrypicks.tcc.model;

public class StampAdjustReason extends BaseModel {

	private static final long serialVersionUID = 7578885776662574856L;
	
	private Long merchantId;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

}