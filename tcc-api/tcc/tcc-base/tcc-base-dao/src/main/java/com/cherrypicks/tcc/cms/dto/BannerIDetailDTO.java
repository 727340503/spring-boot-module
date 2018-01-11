package com.cherrypicks.tcc.cms.dto;

import java.util.Date;
import java.util.List;

public class BannerIDetailDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4228209221403757041L;

	private Long id;
	private Long merchantId;
	private Integer status;
	private Integer sortOrder;
	private String webUrl;
	private Integer inappOpen;
	private Date createdTime;
	private Date startTime;
	private Date endTime;
	private List<BannerLangMapDTO> bannerLangMaps;

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

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public List<BannerLangMapDTO> getBannerLangMaps() {
		return bannerLangMaps;
	}

	public void setBannerLangMaps(List<BannerLangMapDTO> bannerLangMaps) {
		this.bannerLangMaps = bannerLangMaps;
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
