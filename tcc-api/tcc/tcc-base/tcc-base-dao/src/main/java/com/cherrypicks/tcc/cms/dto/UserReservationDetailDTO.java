package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserReservationDetailDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200304026174582717L;

	private Long id;
	private Long merchantId;
	private String merchantName;
	private String userName;
	private String giftName;
	private Date createdTime;
	private String campaignName;
	private Integer status;
	private Integer stampQty;
	private Double cashQty;
	private String storeAreaInfo;
	private String storeName;
	private String storeAddress;
	private String firstName;
	private String lastName;
	private String reservationCode;
	private String contactPhoneAreaCode;
	private String contactPhone;
	private Date expiredTime;
	private String storePhone;
	private String merchantTimeZone;
	private Date reservationExpiredTime;
	private String contactPerson;
	private Date pickupTime;
	private Date updatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getStoreAreaInfo() {
		return storeAreaInfo;
	}

	public void setStoreAreaInfo(String storeAreaInfo) {
		this.storeAreaInfo = storeAreaInfo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
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

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getContactPhoneAreaCode() {
		return contactPhoneAreaCode;
	}

	public void setContactPhoneAreaCode(String contactPhoneAreaCode) {
		this.contactPhoneAreaCode = contactPhoneAreaCode;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getStorePhone() {
		return storePhone;
	}

	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
