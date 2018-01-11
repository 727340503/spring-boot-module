package com.cherrypicks.tcc.model;

public class BeaconCouponMap extends BaseModel {

	private static final long serialVersionUID = 4173289184423901246L;

	private Long beaconId;

	private Long couponId;

	public Long getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(Long beaconId) {
		this.beaconId = beaconId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

}