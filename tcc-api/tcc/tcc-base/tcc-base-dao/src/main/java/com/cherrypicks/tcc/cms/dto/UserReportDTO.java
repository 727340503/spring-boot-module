package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserReportDTO extends BaseObject {

	private static final long serialVersionUID = -6446194093037111796L;
	private String userName;
	private String merchantName;
	private String merchantTimeZone;
	private Date createdTime;
	private String email;
	private String phone;
	private String gender;
	private Date birthday;
	private String birthdayValue;
	private Boolean isMarketingInfo;
	private String phoneAreaCode;
	private Boolean isEmailValidation;
	private String snsId;
	private Date lastMobileVerifyTime;
	private Integer totalSendSmsQty;

	// private Integer userType;
	// private Integer status;
	// private Integer snsType;
	// private String userTypeValue;
	// private String statusValue;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthdayValue() {
		return birthdayValue;
	}

	public void setBirthdayValue(String birthdayValue) {
		this.birthdayValue = birthdayValue;
	}

	public Boolean getIsMarketingInfo() {
		return isMarketingInfo;
	}

	public void setIsMarketingInfo(Boolean isMarketingInfo) {
		this.isMarketingInfo = isMarketingInfo;
	}

	public String getPhoneAreaCode() {
		return phoneAreaCode;
	}

	public void setPhoneAreaCode(String phoneAreaCode) {
		this.phoneAreaCode = phoneAreaCode;
	}

	public Boolean getIsEmailValidation() {
		return isEmailValidation;
	}

	public void setIsEmailValidation(Boolean isEmailValidation) {
		this.isEmailValidation = isEmailValidation;
	}

	public String getSnsId() {
		return snsId;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public Date getLastMobileVerifyTime() {
		return lastMobileVerifyTime;
	}

	public void setLastMobileVerifyTime(Date lastMobileVerifyTime) {
		this.lastMobileVerifyTime = lastMobileVerifyTime;
	}

	public Integer getTotalSendSmsQty() {
		return totalSendSmsQty;
	}

	public void setTotalSendSmsQty(Integer totalSendSmsQty) {
		this.totalSendSmsQty = totalSendSmsQty;
	}

}
