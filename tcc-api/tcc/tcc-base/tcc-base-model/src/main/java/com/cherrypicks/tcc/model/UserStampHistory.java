package com.cherrypicks.tcc.model;

import java.util.Date;

public class UserStampHistory extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = -8438518074475675607L;

	public enum UserStampHistoryType {
		COLLECT_STAMPS(1, "Collect stamps"), TRANSFER_OUT_STAMPS(2, "Transfer out stamps"), TRANSFER_IN_STAMPS(3,
				"Transfer in stamps"), REDEEM(4, "Redemption"), RESERVATION(5, "Reservation"), SYSTEM_IN_STAMPS(6,
						"System in stamps"), SYSTEM_OUT_STAMPS(7, "System out stamps"), GRANT(8, "Free stamps");
		private final int value;
		private final String name;

		UserStampHistoryType(final int value, final String name) {
			this.value = value;
			this.name = name;
		}

		public int toValue() {
			return this.value;
		}

		public String toName() {
			return this.name;
		}
	}

	public enum UserStampHistoryReadStatus {
		UN_READ(0), READ(1);

		private final int value;

		UserStampHistoryReadStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	/**  */
	private Long userId;

	/**  */
	private Long merchantId;

	/**  */
	private Long userStampCardId;

	/** 1-Collect stamps 2-Transfer stamps */
	private Integer type;

	/**  */
	private Long stampQty;

	/**  */
	private Long transUserId;

	private Long campaignGiftMapId;

	private Integer exchangeType;

	private Double cashQty;

	private String transNo;

	private Date transDateTime;

	private String externalStoreId;

	private Integer readStatus;

	private String redeemCode;

	private String remarks;

	private Long exchangeTypeId;

	private Integer redeemReadStatus;

	private Boolean isFromReservation = false;

	private Date reservationPickupDate;

	private Double transAmt;

	private String relatedUserCoupon;

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
	 * 获取
	 *
	 * @return
	 */
	public Long getUserStampCardId() {
		return this.userStampCardId;
	}

	/**
	 * 设置
	 *
	 * @param userStampCardId
	 *
	 */
	public void setUserStampCardId(final Long userStampCardId) {
		this.userStampCardId = userStampCardId;
	}

	/**
	 * 获取1-Collect stamps 2-Transfer stamps 3-Redemption
	 *
	 * @return 1-Collect stamps 2-Transfer stamps 3-Redemption
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * 设置1-Collect stamps 2-Transfer stamps 3-Redemption
	 *
	 * @param type
	 *            1-Collect stamps 2-Transfer stamps 3-Redemption
	 */
	public void setType(final Integer type) {
		this.type = type;
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

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getTransUserId() {
		return this.transUserId;
	}

	/**
	 * 设置
	 *
	 * @param transUserId
	 *
	 */
	public void setTransUserId(final Long transUserId) {
		this.transUserId = transUserId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getCampaignGiftMapId() {
		return campaignGiftMapId;
	}

	public void setCampaignGiftMapId(final Long campaignGiftMapId) {
		this.campaignGiftMapId = campaignGiftMapId;
	}

	public Integer getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(final Integer exchangeType) {
		this.exchangeType = exchangeType;
	}

	public Double getCashQty() {
		return cashQty;
	}

	public void setCashQty(final Double cashQty) {
		this.cashQty = cashQty;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(final String transNo) {
		this.transNo = transNo;
	}

	public Date getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(final Date transDateTime) {
		this.transDateTime = transDateTime;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(final Integer readStatus) {
		this.readStatus = readStatus;
	}

	public String getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(final String redeemCode) {
		this.redeemCode = redeemCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	public String getExternalStoreId() {
		return externalStoreId;
	}

	public void setExternalStoreId(final String externalStoreId) {
		this.externalStoreId = externalStoreId;
	}

	public Long getExchangeTypeId() {
		return exchangeTypeId;
	}

	public void setExchangeTypeId(final Long exchangeTypeId) {
		this.exchangeTypeId = exchangeTypeId;
	}

	public Integer getRedeemReadStatus() {
		return redeemReadStatus;
	}

	public void setRedeemReadStatus(final Integer redeemReadStatus) {
		this.redeemReadStatus = redeemReadStatus;
	}

	public Boolean getIsFromReservation() {
		return isFromReservation;
	}

	public void setIsFromReservation(final Boolean isFromReservation) {
		this.isFromReservation = isFromReservation;
	}

	public Date getReservationPickupDate() {
		return reservationPickupDate;
	}

	public void setReservationPickupDate(final Date reservationPickupDate) {
		this.reservationPickupDate = reservationPickupDate;
	}

	public Double getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(Double transAmt) {
		this.transAmt = transAmt;
	}

	public String getRelatedUserCoupon() {
		return relatedUserCoupon;
	}

	public void setRelatedUserCoupon(String relatedUserCoupon) {
		this.relatedUserCoupon = relatedUserCoupon;
	}

}