package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class KeeperUserItemDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3723913173369649945L;

	private Long id;
	private String userName;
	// private String externalStoreId;
	private String storeName;
	private Integer status;
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
