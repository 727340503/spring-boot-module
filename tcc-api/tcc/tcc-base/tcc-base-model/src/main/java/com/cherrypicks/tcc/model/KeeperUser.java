package com.cherrypicks.tcc.model;

public class KeeperUser extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 6533940700918384914L;

	public enum KeeperUserStatus {
		IN_ACTIVE(0), ACTIVE(1);

		private final int value;

		KeeperUserStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	/**  */
	private Long merchantId;

	/**  */
	private Long storeId;

	/**  */
	private String userName;

	/**  */
	private String password;

	/**  */
	private String session;

	/**  */
	private Integer status;

	/**  */
	private String icon;

	private String staffId;

	private String mobile;

	private String email;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getMerchantId() {
		return this.merchantId;
	}

	/**
	 * 设置
	 *
	 * @param merchantId
	 *
	 */
	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 设置
	 *
	 * @param password
	 *
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getSession() {
		return this.session;
	}

	/**
	 * 设置
	 *
	 * @param session
	 *
	 */
	public void setSession(final String session) {
		this.session = session;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(final Long storeId) {
		this.storeId = storeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(final String icon) {
		this.icon = icon;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(final String staffId) {
		this.staffId = staffId;
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