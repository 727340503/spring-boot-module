package com.cherrypicks.tcc.cms.dto;

import java.util.Date;
import java.util.List;

public class CouponDetailDTO extends BaseObject {

	private static final long serialVersionUID = 157361464758227222L;

	private Long id;
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
	private Boolean isUserTier = false;
	private Long userMemberTierId;
	private Integer type;
	private Integer issuedQuotaType;
	private Integer issuedQuotaQty;
	private Double rewardQty;
	private Double maxRewardQty;
	private Boolean isUseWithSameCoupon = false;
	private Boolean isUseWithOtherCoupon = false;
	private Boolean isTransferrable = false;
	private Integer sortOrder;
	private Integer status;
	private Date createdDate;
	private List<CouponLangMapDTO> couponLangMaps;
	private List<CouponCollectMethodDTO> collectMethodData;
	private List<CouponCollectQuotaDTO> collectQuotaData;
	private List<CouponRedeemMethodDTO> redeemMethodData;
	private List<CouponRedeemQuotaDTO> redeemQuotaData;

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

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public Date getCollectStartTime() {
		return collectStartTime;
	}

	public void setCollectStartTime(Date collectStartTime) {
		this.collectStartTime = collectStartTime;
	}

	public Date getCollectEndTime() {
		return collectEndTime;
	}

	public void setCollectEndTime(Date collectEndTime) {
		this.collectEndTime = collectEndTime;
	}

	public Date getRedeemStartTime() {
		return redeemStartTime;
	}

	public void setRedeemStartTime(Date redeemStartTime) {
		this.redeemStartTime = redeemStartTime;
	}

	public Date getRedeemEndTime() {
		return redeemEndTime;
	}

	public void setRedeemEndTime(Date redeemEndTime) {
		this.redeemEndTime = redeemEndTime;
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

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
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

	public void setIsUserTier(Boolean isUserTier) {
		this.isUserTier = isUserTier;
	}

	public Long getUserMemberTierId() {
		return userMemberTierId;
	}

	public void setUserMemberTierId(Long userMemberTierId) {
		this.userMemberTierId = userMemberTierId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIssuedQuotaType() {
		return issuedQuotaType;
	}

	public void setIssuedQuotaType(Integer issuedQuotaType) {
		this.issuedQuotaType = issuedQuotaType;
	}

	public Integer getIssuedQuotaQty() {
		return issuedQuotaQty;
	}

	public void setIssuedQuotaQty(Integer issuedQuotaQty) {
		this.issuedQuotaQty = issuedQuotaQty;
	}

	public Double getRewardQty() {
		return rewardQty;
	}

	public void setRewardQty(Double rewardQty) {
		this.rewardQty = rewardQty;
	}

	public Double getMaxRewardQty() {
		return maxRewardQty;
	}

	public void setMaxRewardQty(Double maxRewardQty) {
		this.maxRewardQty = maxRewardQty;
	}

	public Boolean getIsUseWithSameCoupon() {
		return isUseWithSameCoupon;
	}

	public void setIsUseWithSameCoupon(Boolean isUseWithSameCoupon) {
		this.isUseWithSameCoupon = isUseWithSameCoupon;
	}

	public Boolean getIsUseWithOtherCoupon() {
		return isUseWithOtherCoupon;
	}

	public void setIsUseWithOtherCoupon(Boolean isUseWithOtherCoupon) {
		this.isUseWithOtherCoupon = isUseWithOtherCoupon;
	}

	public Boolean getIsTransferrable() {
		return isTransferrable;
	}

	public void setIsTransferrable(Boolean isTransferrable) {
		this.isTransferrable = isTransferrable;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<CouponLangMapDTO> getCouponLangMaps() {
		return couponLangMaps;
	}

	public void setCouponLangMaps(List<CouponLangMapDTO> couponLangMaps) {
		this.couponLangMaps = couponLangMaps;
	}

	public List<CouponCollectMethodDTO> getCollectMethodData() {
		return collectMethodData;
	}

	public void setCollectMethodData(List<CouponCollectMethodDTO> collectMethodData) {
		this.collectMethodData = collectMethodData;
	}

	public List<CouponCollectQuotaDTO> getCollectQuotaData() {
		return collectQuotaData;
	}

	public void setCollectQuotaData(List<CouponCollectQuotaDTO> collectQuotaData) {
		this.collectQuotaData = collectQuotaData;
	}

	public List<CouponRedeemMethodDTO> getRedeemMethodData() {
		return redeemMethodData;
	}

	public void setRedeemMethodData(List<CouponRedeemMethodDTO> redeemMethodData) {
		this.redeemMethodData = redeemMethodData;
	}

	public List<CouponRedeemQuotaDTO> getRedeemQuotaData() {
		return redeemQuotaData;
	}

	public void setRedeemQuotaData(List<CouponRedeemQuotaDTO> redeemQuotaData) {
		this.redeemQuotaData = redeemQuotaData;
	}

}
