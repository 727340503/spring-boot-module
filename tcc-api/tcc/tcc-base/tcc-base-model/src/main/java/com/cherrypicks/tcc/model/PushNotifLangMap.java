package com.cherrypicks.tcc.model;

public class PushNotifLangMap extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4899307056838846889L;

	private Long pushNotifId;
	private String msg;
	private String landingImg;
	private String landingUrl;
	private String langCode;

	public Long getPushNotifId() {
		return pushNotifId;
	}

	public void setPushNotifId(Long pushNotifId) {
		this.pushNotifId = pushNotifId;
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

	public String getLandingUrl() {
		return landingUrl;
	}

	public void setLandingUrl(String landingUrl) {
		this.landingUrl = landingUrl;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}