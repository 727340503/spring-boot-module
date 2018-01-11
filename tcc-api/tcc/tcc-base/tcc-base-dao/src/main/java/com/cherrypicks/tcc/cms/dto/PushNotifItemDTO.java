package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class PushNotifItemDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3163310467069866226L;
	private Long id;
	private String merchantName;

	private Integer status;
	// private Date createdTime;
	private Date updatedTime;

	private String msg;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}