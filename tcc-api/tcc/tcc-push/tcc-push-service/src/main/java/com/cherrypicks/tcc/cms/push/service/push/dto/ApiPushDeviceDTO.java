package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;

public class ApiPushDeviceDTO implements Serializable {

	private static final long serialVersionUID = 1061772595654975571L;

	private Integer deviceType;

	private String lang;

	private String deviceToken;

	private Object userId;

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(final Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(final String lang) {
		this.lang = lang;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(final String deviceToken) {
		this.deviceToken = deviceToken;
	}

    public Object getUserId() {
        return userId;
    }

    public void setUserId(final Object userId) {
        this.userId = userId;
    }
}
