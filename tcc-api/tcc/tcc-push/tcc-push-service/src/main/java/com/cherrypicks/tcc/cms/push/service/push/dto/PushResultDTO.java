package com.cherrypicks.tcc.cms.push.service.push.dto;


public class PushResultDTO<T> {

	public static final String RESULT_FAILED = "0";
	public static final String RESULT_SUCCESS = "1";
	
	private int errorCode = 0;
	private String errorMessage = "";
	private T result;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
