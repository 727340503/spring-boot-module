package com.cherrypicks.tcc.cms.api.vo;

import java.io.Serializable;

import com.cherrypicks.tcc.util.CodeStatus;

public class UserStampHistoryVO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3061703093783719824L;
	private Object result;
	private Long totalStampNo;
    private  int errorCode = CodeStatus.SUCCUSS;
    private  String errorMessage = "";

    public Object getResult() {
        return result;
    }

    public void setResult(final Object result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

	public Long getTotalStampNo() {
		return totalStampNo;
	}

	public void setTotalStampNo(Long totalStampNo) {
		this.totalStampNo = totalStampNo;
	}

}
