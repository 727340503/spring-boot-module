package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserStampHistoryDetailDTO extends BaseObject {

	private static final long serialVersionUID = -6733666889175097328L;

	private Long id;
	private String merchantName;
	private String campaignName;
	private Date createdTime;
	private Integer type;
	private Integer stampQty;
	private Date transDateTime;
	private String transNo;
	private Integer exchangeType;
	private String giftName;
	private String merchantTimeZone;
	private String remarks;
	private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStampQty() {
		return stampQty;
	}

	public void setStampQty(Integer stampQty) {
		this.stampQty = stampQty;
	}

	public Date getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(Date transDateTime) {
		this.transDateTime = transDateTime;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Integer getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(Integer exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getMerchantTimeZone() {
		return merchantTimeZone;
	}

	public void setMerchantTimeZone(String merchantTimeZone) {
		this.merchantTimeZone = merchantTimeZone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
