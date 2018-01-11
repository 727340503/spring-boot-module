package com.cherrypicks.tcc.cms.dto;

public class KeeperUserDetailDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3723913173369649945L;

	private String userName;
	private String staffId;
	private String externalStoreId;
	private Integer status;
	private Long merchantId;
	private String merchantName;
	private Long storeId;
	private String storeName;
	private String mobile;
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getExternalStoreId() {
		return externalStoreId;
	}

	public void setExternalStoreId(String externalStoreId) {
		this.externalStoreId = externalStoreId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
