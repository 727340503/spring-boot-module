package com.cherrypicks.tcc.model;

import java.util.Date;

public class Banner extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 8985890635625552991L;

	public enum BannerStatus {
		PENDING(1), ACTIVE(2), EXPIRED(3), IN_ACTIVE(4);

		private final int value;

		BannerStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}
	
	public enum BannerInappOpenStatus {
		IN_APP(0),NATIVE_BROWSER(1);
		
		private final int value;
		
		BannerInappOpenStatus(final int value) {
			this.value = value;
		}
		
		public int toValue() {
			return this.value;
		}
	}
	
	

	/**  */
	private Long merchantId;

	/**  */
	private Integer sortOrder;

	/**  */
	private String webUrl;

	private Date startTime;

	private Date endTime;

	/**  */
	private Integer status;

	private Integer inappOpen;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置
	 *
	 * @param status
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

	/**
	 * 获取
	 *
	 * @return
	 */
	public Integer getSortOrder() {
		return this.sortOrder;
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

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getWebUrl() {
		return this.webUrl;
	}

	/**
	 * 设置
	 *
	 * @param webUrl
	 *
	 */
	public void setWebUrl(final String webUrl) {
		this.webUrl = webUrl;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getInappOpen() {
		return inappOpen;
	}

	public void setInappOpen(Integer inappOpen) {
		this.inappOpen = inappOpen;
	}

}