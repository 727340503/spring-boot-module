package com.cherrypicks.tcc.model;

import java.util.Date;

public class Coupon extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4696593090687497538L;

	/**
	 * 发放总数无限制 常量
	 */
	public final static Integer NO_LIMIT_TOTAL_QTY = -1;
	/**
	 * 默认一次可以collect的coupon数量
	 */
	public final static Integer DEFAULT_ONCE_COLLECT_QTY = 1;

	private Long merchantId;

	private Long campaignId;

	private Date collectStartTime;

	private Date collectEndTime;

	private Date redeemStartTime;

	private Date redeemEndTime;

	private Boolean isAfterCollect = false;

	private Integer endDaysAfterCollect;

	private Integer totalQty;

	private Integer issuedQty;

	// private Integer remainderQty;

	private Boolean isUserTier = false;

	private Long userMemberTierId;

	private Integer type;

	private Integer issuedQuotaType;

	private Integer issuedQuotaQty;

	private Double rewardQty;

	private Double maxRewardQty;

	private Boolean isUseWithSameCoupon;

	// private Boolean isUseSameCalculateCoupon = false;

	private Boolean isUseWithOtherCoupon = false;

	// private Boolean isGrouping = true;

	private Boolean isTransferrable = false;

	private Integer sortOrder;

	private Integer status;

	public enum IssuedQuotaType {
		NO_LIMIT(0), DAY(1), MONTH(2), YEAR(3), CAMPAIGN(4), COUPON_PERIOD(5);

		private final int value;

		private IssuedQuotaType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	/**
	 * 获取0-Inactive 1-Active 2-Terminated 3-Out of stock 4-Expired
	 *
	 * @return 0-Inactive 1-Active 2-Terminated 3-Out of stock 4-Expired
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置0-Inactive 1-Active 2-Terminated 3-Out of stock 4-Expired
	 *
	 * @param status
	 *            0-Inactive 1-Active 2-Terminated 3-Out of stock 4-Expired
	 */
	public void setStatus(final Integer status) {
		this.status = status;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * 设置
	 *
	 * @param campaignId
	 *
	 */
	public void setCampaignId(final Long campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * 设置
	 *
	 * @param sortOrder
	 *
	 */
	public void setSortOrder(final Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	public Date getCollectEndTime() {
		return collectEndTime;
	}

	public void setCollectEndTime(final Date collectEndTime) {
		this.collectEndTime = collectEndTime;
	}

	public Date getCollectStartTime() {
		return collectStartTime;
	}

	public void setCollectStartTime(final Date collectStartTime) {
		this.collectStartTime = collectStartTime;
	}

	public Date getRedeemEndTime() {
		return redeemEndTime;
	}

	public void setRedeemEndTime(final Date redeemEndTime) {
		this.redeemEndTime = redeemEndTime;
	}

	public Date getRedeemStartTime() {
		return redeemStartTime;
	}

	public void setRedeemStartTime(final Date redeemStartTime) {
		this.redeemStartTime = redeemStartTime;
	}

	public Boolean getIsAfterCollect() {
		return isAfterCollect;
	}

	public void setIsAfterCollect(final Boolean isAfterCollect) {
		this.isAfterCollect = isAfterCollect;
	}

	public Integer getEndDaysAfterCollect() {
		return endDaysAfterCollect;
	}

	public void setEndDaysAfterCollect(final Integer endDaysAfterCollect) {
		this.endDaysAfterCollect = endDaysAfterCollect;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(final Integer totalQty) {
		this.totalQty = totalQty;
	}

	public Integer getIssuedQty() {
		return issuedQty;
	}

	public void setIssuedQty(Integer issuedQty) {
		this.issuedQty = issuedQty;
	}

	public Boolean getIsUserTier() {
		return isUserTier;
	}

	public void setIsUserTier(final Boolean isUserTier) {
		this.isUserTier = isUserTier;
	}

	public Long getUserMemberTierId() {
		return userMemberTierId;
	}

	public void setUserMemberTierId(final Long userMemberTierId) {
		this.userMemberTierId = userMemberTierId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(final Integer type) {
		this.type = type;
	}

	public Integer getIssuedQuotaQty() {
		return issuedQuotaQty;
	}

	public void setIssuedQuotaQty(final Integer issuedQuotaQty) {
		this.issuedQuotaQty = issuedQuotaQty;
	}

	public Integer getIssuedQuotaType() {
		return issuedQuotaType;
	}

	public void setIssuedQuotaType(final Integer issuedQuotaType) {
		this.issuedQuotaType = issuedQuotaType;
	}

	public Double getRewardQty() {
		return rewardQty;
	}

	public void setRewardQty(final Double rewardQty) {
		this.rewardQty = rewardQty;
	}

	public Double getMaxRewardQty() {
		return maxRewardQty;
	}

	public void setMaxRewardQty(final Double maxRewardQty) {
		this.maxRewardQty = maxRewardQty;
	}

	public Boolean getIsUseWithSameCoupon() {
		return isUseWithSameCoupon;
	}

	public void setIsUseWithSameCoupon(final Boolean isUseWithSameCoupon) {
		this.isUseWithSameCoupon = isUseWithSameCoupon;
	}

	public Boolean getIsUseWithOtherCoupon() {
		return isUseWithOtherCoupon;
	}

	public void setIsUseWithOtherCoupon(final Boolean isUseWithOtherCoupon) {
		this.isUseWithOtherCoupon = isUseWithOtherCoupon;
	}

	public Boolean getIsTransferrable() {
		return isTransferrable;
	}

	public void setIsTransferrable(final Boolean isTransferrable) {
		this.isTransferrable = isTransferrable;
	}

	public enum CouponStatus {
		// IN_ACTIVE(0),ACTIVE(1),TERMINATED(2),OUT_OF_STOCK(3),EXPIRED(4);
		PENDING(1), ACTIVE(2), EXPIRED(3), IN_ACTIVE(4);

		private final int value;

		CouponStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum CouponIssuedQuotaType {

		NO_LIMIT(0), DAY(1), MONTH(2), YEAR(3), CAMPAIGN(4), COUPON_PERIOD(5);

		private final int value;

		private CouponIssuedQuotaType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum CouponType {

		STAMPS(1, "Stamps(Add)"), POINT(2, "Point(Add)"), CASH(3, "Cash"), DISCOUNT(4, "Discount"), STAMPS_MULTIPLY(5,
				"Stamps(Multiply)"), POINT_MULTIPLY(6, "Point(Multiply)");

		private final int value;
		private final String info;

		private CouponType(final int value, final String info) {
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

}