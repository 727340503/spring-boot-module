package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;

public class ApiPushParameterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8240440295156524083L;

	private String key;
	private String value;
	
	public ApiPushParameterDTO() {
	}
	
	public ApiPushParameterDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
