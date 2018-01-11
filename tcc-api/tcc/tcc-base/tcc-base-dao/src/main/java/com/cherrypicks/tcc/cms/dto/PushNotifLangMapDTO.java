package com.cherrypicks.tcc.cms.dto;

public class PushNotifLangMapDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6808956165758639949L;
	private Long id;
	private String msg;
	private String landingImg;
	private String landingUrl;
	private String merchantName;
	private String langCode;
	private String fullLandingImg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLandingImg() {
		return landingImg;
	}

	public void setLandingImg(String landingImg) {
		this.landingImg = landingImg;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getFullLandingImg() {
		return fullLandingImg;
	}

	public void setFullLandingImg(String fullLandingImg) {
		this.fullLandingImg = fullLandingImg;
	}

	public String getLandingUrl() {
		return landingUrl;
	}

	public void setLandingUrl(String landingUrl) {
		this.landingUrl = landingUrl;
	}

}
