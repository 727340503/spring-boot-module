package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiMessageBodyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1759465455003702092L;

	private String lang;
	
	private String message;
	
	private String sound;
	
	private Integer badge;

	private List<ApiPushParameterDTO> parameterList;

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public Integer getBadge() {
		return badge;
	}

	public void setBadge(Integer badge) {
		this.badge = badge;
	}

	public List<ApiPushParameterDTO> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ApiPushParameterDTO> parameterList) {
		this.parameterList = parameterList;
	}
	
	public void addParameter(ApiPushParameterDTO parameter){
		if(parameterList == null){
			parameterList = new ArrayList<ApiPushParameterDTO>();
		}
		parameterList.add(parameter);
	}
}
