package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserCouponItemDTO extends BaseObject {

	private static final long serialVersionUID = 157361464758227222L;

	private Long id;
	private Integer type;
	private String couponName;
	private String campaignName;
	private Date createdTime;
	private Date redeemedDate;
	private Date expiredTime;
	private Integer status;
	private String remark;
	private Boolean isAfterCollect;
	private Integer endDaysAfterCollect;
	private Date redeemEndTime;
	private String merchantTimeZone;
	private String handledBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getRedeemedDate() {
		return redeemedDate;
	}

	public void setRedeemedDate(Date redeemedDate) {
		this.redeemedDate = redeemedDate;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getMerchantTimeZone() {
		return merchantTimeZone;
	}

	public void setMerchantTimeZone(String merchantTimeZone) {
		this.merchantTimeZone = merchantTimeZone;
	}

	public String getHandledBy() {
		return handledBy;
	}

	public void setHandledBy(String handledBy) {
		this.handledBy = handledBy;
	}

}
