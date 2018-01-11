package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;

public class PushMsgDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8500394246365772736L;

	private String messge;
	private String langCode;

	public String getMessge() {
		return messge;
	}

	public void setMessge(String messge) {
		this.messge = messge;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}