package com.cherrypicks.tcc.cms.dto;

import java.util.Date;
import java.util.List;

public class GameDetailDTO extends BaseObject {

	private static final long serialVersionUID = 7809045276369127236L;

	private Long id;
	private Long merchantId;
	private String webUrl;
	private Date createdTime;
	private Integer status;
	private Date startTime;
	private Date endTime;
	private Integer inappOpen;
	private List<GameLangMapDTO> gameLangMaps;

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

	public List<GameLangMapDTO> getGameLangMaps() {
		return gameLangMaps;
	}

	public void setGameLangMaps(List<GameLangMapDTO> gameLangMaps) {
		this.gameLangMaps = gameLangMaps;
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

}
