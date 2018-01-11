package com.cherrypicks.tcc.model;

import java.util.Date;

public class PushNotif extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8512049166754440818L;

	private Long merchantId;
	private Integer type;
	private Integer pageRedirectType;
	private Long pushTaskId;
	private Date startTime;
	private Date endTime;
	private Integer frequency;
	private Integer status;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPageRedirectType() {
		return pageRedirectType;
	}

	public void setPageRedirectType(Integer pageRedirectType) {
		this.pageRedirectType = pageRedirectType;
	}

	public Long getPushTaskId() {
		return pushTaskId;
	}

	public void setPushTaskId(Long pushTaskId) {
		this.pushTaskId = pushTaskId;
	}

	public enum PageRedirectType {
		STAMP_ACTIVE_CAMPAIGN_LIST(1), STAMP_DETAIL(2), ME_FRIEND_LIST(3), ME_RESERVATION_HISTORY(4), PROMOTION(5);

		private int code;

		private PageRedirectType(int code) {
			this.code = code;
		}

		public int getCode() {
			return this.code;
		}
	}

	public enum Type {
		RESERVATION(1), RECEIVE_STAMPS(2), RECEIVE_FRIEND(3), CMS(4);

		private int code;

		private Type(int code) {
			this.code = code;
		}

		public int getCode() {
			return this.code;
		}
	}

	public enum Status {
		DRAFT(1), SENT(2);

		int value;

		private Status(int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum Frequency {
		DAILY("daily", 1), WEEKLY("weekly", 2), MONTHLY("monthly", 3);

		String value;
		int code;

		private Frequency(String value, int code) {
			this.value = value;
			this.code = code;
		}

		public String getValue() {
			return this.value;
		}

		public int getCode() {
			return this.code;
		}
	}

}