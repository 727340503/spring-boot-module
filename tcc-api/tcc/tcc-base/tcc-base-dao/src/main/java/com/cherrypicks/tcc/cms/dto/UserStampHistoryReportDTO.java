package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserStampHistoryReportDTO extends BaseObject {

	private static final long serialVersionUID = -6733666889175097328L;

	private String merchantName;
	private String campaignName;
	private Date createdTime;
	private Integer stampQty;
	private Double cashQty;
	private Date transDateTime;
	private String giftName;
	private String merchantTimeZone;
	private String externalStoreId;
	private String userName;
	private String receiverUserName;
	private String redeemCode;
	private Long exchangeTypeId;
	private String transNo;
	private String externalRuleCode;
	private Date reservationTime;
	private Integer type;
	private String typeValue;
	private String userPhoneAreaCode;
	private String userPhone;
	private String receiverUserPhone;
	private String receiverUserPhoneAreaCode;
	private Double transAmt;
	private Boolean isUsedCoupon;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getStampQty() {
		return stampQty;
	}

	public void setStampQty(Integer stampQty) {
		this.stampQty = stampQty;
	}

	public Date getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(Date transDateTime) {
		this.transDateTime = transDateTime;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getMerchantTimeZone() {
		return merchantTimeZone;
	}

	public void setMerchantTimeZone(String merchantTimeZone) {
		this.merchantTimeZone = merchantTimeZone;
	}

	public String getExternalStoreId() {
		return externalStoreId;
	}

	public void setExternalStoreId(String externalStoreId) {
		this.externalStoreId = externalStoreId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
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

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getExternalRuleCode() {
		return externalRuleCode;
	}

	public void setExternalRuleCode(String externalRuleCode) {
		this.externalRuleCode = externalRuleCode;
	}

	public String getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}

	public Date getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getUserPhoneAreaCode() {
		return userPhoneAreaCode;
	}

	public void setUserPhoneAreaCode(String userPhoneAreaCode) {
		this.userPhoneAreaCode = userPhoneAreaCode;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getReceiverUserPhone() {
		return receiverUserPhone;
	}

	public void setReceiverUserPhone(String receiverUserPhone) {
		this.receiverUserPhone = receiverUserPhone;
	}

	public String getReceiverUserPhoneAreaCode() {
		return receiverUserPhoneAreaCode;
	}

	public void setReceiverUserPhoneAreaCode(String receiverUserPhoneAreaCode) {
		this.receiverUserPhoneAreaCode = receiverUserPhoneAreaCode;
	}

	public Double getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(Double transAmt) {
		this.transAmt = transAmt;
	}

	public Boolean getIsUsedCoupon() {
		return isUsedCoupon;
	}

	public void setIsUsedCoupon(Boolean isUsedCoupon) {
		this.isUsedCoupon = isUsedCoupon;
	}

}
