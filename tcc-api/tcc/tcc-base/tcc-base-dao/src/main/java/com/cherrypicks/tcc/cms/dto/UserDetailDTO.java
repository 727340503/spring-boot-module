package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserDetailDTO extends BaseObject {

	private static final long serialVersionUID = -6446194093037111796L;
	private Long id;
	private String userName;
	private String firstName;
	private String lastName;
	private String merchantName;
	private Long merchantId;
//	private String contactEmail;
	private String phone;
	private String gender;
	private Date birthday;
//	private Integer userType;
	private String email;
//	private Integer status;
//	private Integer snsType;
	private Boolean isMarketingInfo;
	private String phoneAreaCode;
	private Boolean isEmailValidation;
	private String facebookAccount;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

//	public String getContactEmail() {
//		return contactEmail;
//	}
//
//	public void setContactEmail(String contactEmail) {
//		this.contactEmail = contactEmail;
//	}

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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getFacebookAccount() {
		return facebookAccount;
	}

	public void setFacebookAccount(String facebookAccount) {
		this.facebookAccount = facebookAccount;
	}
}
