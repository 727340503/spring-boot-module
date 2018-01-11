package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserReservationReportDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200304026174582717L;

	private Long id;
	private String userName;
	private String giftName;
	private Date createdTime;
	private String campaignName;
	private String merchantName;
	private Integer status;
	private String merchantTimeZone;
	private Date reservationExpiredTime;
	private String externalStoreId;
	private String statusValue;
	private Integer stampQty;
	private Double cashQty;
	private Long exchangeTypeId;
	// private String exchangeTypeValue;
	private String externalRuleCode;
	private Date updatedTime;
	private Date pickupTime;
	private String phone;
	private String phoneAreaCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMerchantTimeZone() {
		return merchantTimeZone;
	}

	public void setMerchantTimeZone(String merchantTimeZone) {
		this.merchantTimeZone = merchantTimeZone;
	}

	public Date getReservationExpiredTime() {
		return reservationExpiredTime;
	}

	public void setReservationExpiredTime(Date reservationExpiredTime) {
		this.reservationExpiredTime = reservationExpiredTime;
	}

	public String getExternalStoreId() {
		return externalStoreId;
	}

	public void setExternalStoreId(String externalStoreId) {
		this.externalStoreId = externalStoreId;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public Integer getStampQty() {
		return stampQty;
	}

	public void setStampQty(Integer stampQty) {
		this.stampQty = stampQty;
	}

	public Double getCashQty() {
		return cashQty;
	}

	public void setCashQty(Double cashQty) {
		this.cashQty = cashQty;
	}

	public Long getExchangeTypeId() {
		return exchangeTypeId;
	}

	public void setExchangeTypeId(Long exchangeTypeId) {
		this.exchangeTypeId = exchangeTypeId;
	}

	public String getExternalRuleCode() {
		return externalRuleCode;
	}

	public void setExternalRuleCode(String externalRuleCode) {
		this.externalRuleCode = externalRuleCode;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneAreaCode() {
		return phoneAreaCode;
	}

	public void setPhoneAreaCode(String phoneAreaCode) {
		this.phoneAreaCode = phoneAreaCode;
	}

}
