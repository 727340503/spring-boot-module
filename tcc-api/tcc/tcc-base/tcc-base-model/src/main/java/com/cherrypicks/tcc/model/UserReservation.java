package com.cherrypicks.tcc.model;

import java.util.Date;

public class UserReservation extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = -8438518074475675607L;

	public enum UserReservationStatus {
		CONFIRMED(1, "Confirmed"), AVAILABLE(2, "Available"), SETTLED(3, "Settled"), EXPIRED(4,
				"Expired"), IN_COMPLETED(5, "In_completed"), CANCELLED(6, "Cancelled");

		private final int value;
		private final String info;

		UserReservationStatus(final int value, final String info) {
			this.value = value;
			this.info = info;
		}

		public int toValue() {
			return this.value;
		}
		public String toInfo() {
			return this.info;
		}
	}

	public enum UserReservationReadStatus {
		UN_READ(0), READ(1);

		private final int value;

		UserReservationReadStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	/**  */
	private Long userId;

	private Long merchantId;

	private Long userStampCardId;

	private Integer status;

	/**  */
	private Integer type;

	/**  */
	private Long stampQty;

	/**  */
	private Double cashQty;

	/**  */
	private Long campaignGiftMapId;

	/**  */
	private Long storeId;

	/**  */
	private Integer isScan;

	/**  */
	private Integer scanStatus;

	/**  */
	private String msg;

	/**  */
	private String reservationCode;

	/**  */
	private String firstName;

	/**  */
	private String lastName;

	/**  */
	private String contactPhoneAreaCode;

	/**  */
	private String contactPhone;

	private Integer readStatus;

	private Date reservationExpiredTime;

	private Long exchangeTypeId;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * 设置
	 *
	 * @param userId
	 *
	 */
	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	/**
	 *
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 *
	 */
	public void setStatus(final Integer status) {
		this.status = status;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getStampQty() {
		return this.stampQty;
	}

	/**
	 * 设置
	 *
	 * @param stampQty
	 *
	 */
	public void setStampQty(final Long stampQty) {
		this.stampQty = stampQty;
	}

	public Long getCampaignGiftMapId() {
		return campaignGiftMapId;
	}

	public void setCampaignGiftMapId(final Long campaignGiftMapId) {
		this.campaignGiftMapId = campaignGiftMapId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getStoreId() {
		return this.storeId;
	}

	/**
	 * 设置
	 *
	 * @param storeId
	 *
	 */
	public void setStoreId(final Long storeId) {
		this.storeId = storeId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getReservationCode() {
		return reservationCode;
	}

	/**
	 * 设置
	 *
	 * @param reservationCode
	 *
	 */
	public void setReservationCode(final String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getContactPhoneAreaCode() {
		return contactPhoneAreaCode;
	}

	public void setContactPhoneAreaCode(final String contactPhoneAreaCode) {
		this.contactPhoneAreaCode = contactPhoneAreaCode;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(final String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Long getUserStampCardId() {
		return userStampCardId;
	}

	public void setUserStampCardId(final Long userStampCardId) {
		this.userStampCardId = userStampCardId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(final Integer type) {
		this.type = type;
	}

	public Double getCashQty() {
		return cashQty;
	}

	public void setCashQty(final Double cashQty) {
		this.cashQty = cashQty;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(final Integer readStatus) {
		this.readStatus = readStatus;
	}

	public Integer getIsScan() {
		return isScan;
	}

	public void setIsScan(final Integer isScan) {
		this.isScan = isScan;
	}

	public Integer getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(final Integer scanStatus) {
		this.scanStatus = scanStatus;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}

    public Date getReservationExpiredTime() {
        return reservationExpiredTime;
    }

    public void setReservationExpiredTime(final Date reservationExpiredTime) {
        this.reservationExpiredTime = reservationExpiredTime;
    }

    public Long getExchangeTypeId() {
        return exchangeTypeId;
    }

    public void setExchangeTypeId(final Long exchangeTypeId) {
        this.exchangeTypeId = exchangeTypeId;
    }

}