package com.cherrypicks.tcc.model;

import java.util.Date;

public class Game extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 8985890635625552991L;

	/**  */
	private Long merchantId;

	/**  */
	private String webUrl;

	private Date startTime;

	private Date endTime;

	private Integer status;

	private Integer inappOpen;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public enum Status {
		PENDING(1), ACTIVE(2), EXPIRED(3), IN_ACTIVE(4);

		int code;

		private Status(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}
	}
}