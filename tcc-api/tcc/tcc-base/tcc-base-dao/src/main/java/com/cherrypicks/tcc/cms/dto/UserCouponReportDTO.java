package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserCouponReportDTO extends BaseObject {

	private static final long serialVersionUID = -6446194093037111796L;
	private String merchantName;
	private String campaignName;
	private String merchantTimeZone;
	private Date createdTime;
	private String userName;
	private String couponName;
	private Integer type;
	private String typeValue;
	private String phoneAreaCode;
	private String phone;
	private Double rewardQty;
	private Date redeemedDate;
	private Integer status;
	private String statusValue;
	private Boolean isAfterCollect;
	private Integer endDaysAfterCollect;
	private Date redeemEndTime;
	private Integer couponStatus;

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

	public String getMerchantTimeZone() {
		return merchantTimeZone;
	}

	public void setMerchantTimeZone(String merchantTimeZone) {
		this.merchantTimeZone = merchantTimeZone;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
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

	public String getPhoneAreaCode() {
		return phoneAreaCode;
	}

	public void setPhoneAreaCode(String phoneAreaCode) {
		this.phoneAreaCode = phoneAreaCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getRewardQty() {
		return rewardQty;
	}

	public void setRewardQty(Double rewardQty) {
		this.rewardQty = rewardQty;
	}

	public Date getRedeemedDate() {
		return redeemedDate;
	}

	public void setRedeemedDate(Date redeemedDate) {
		this.redeemedDate = redeemedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsAfterCollect() {
		return isAfterCollect;
	}

	public void setIsAfterCollect(Boolean isAfterCollect) {
		this.isAfterCollect = isAfterCollect;
	}

	public Integer getEndDaysAfterCollect() {
		return endDaysAfterCollect;
	}

	public void setEndDaysAfterCollect(Integer endDaysAfterCollect) {
		this.endDaysAfterCollect = endDaysAfterCollect;
	}

	public Date getRedeemEndTime() {
		return redeemEndTime;
	}

	public void setRedeemEndTime(Date redeemEndTime) {
		this.redeemEndTime = redeemEndTime;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public Integer getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Integer couponStatus) {
		this.couponStatus = couponStatus;
	}

}
