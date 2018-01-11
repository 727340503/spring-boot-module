package com.cherrypicks.tcc.cms.dto;

import java.util.Date;
import java.util.List;

public class PushNotifDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3163310467069866226L;
	private Long id;
	private Long merchantId;
	private Integer status;
	private Integer type;
	private Integer pageRedirectType;
	private Date createdTime;
	private List<PushNotifLangMapDTO> pushNotifLangMaps;

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

	public List<PushNotifLangMapDTO> getPushNotifLangMaps() {
		return pushNotifLangMaps;
	}

	public void setPushNotifLangMaps(List<PushNotifLangMapDTO> pushNotifLangMaps) {
		this.pushNotifLangMaps = pushNotifLangMaps;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPageRedirectType() {
		return pageRedirectType;
	}

	public void setPageRedirectType(Integer pageRedirectType) {
		this.pageRedirectType = pageRedirectType;
	}

}