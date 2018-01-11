package com.cherrypicks.tcc.model;

import java.util.Date;

public class TempEmailUser extends BaseModel {

    private static final long serialVersionUID = -3880873366681439537L;


    private Long merchantId;
    private String email;
    private String password;
    private Boolean isEmailValidation;
    private String emailValidationCode;
    private Date validationCodeExpriedTime;
    private Date resetPwdExpriedTime;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean getIsEmailValidation() {
        return isEmailValidation;
    }

    public void setIsEmailValidation(final Boolean isEmailValidation) {
        this.isEmailValidation = isEmailValidation;
    }

    public String getEmailValidationCode() {
        return emailValidationCode;
    }

    public void setEmailValidationCode(final String emailValidationCode) {
        this.emailValidationCode = emailValidationCode;
    }

    public Date getValidationCodeExpriedTime() {
        return validationCodeExpriedTime;
    }

    public void setValidationCodeExpriedTime(final Date validationCodeExpriedTime) {
        this.validationCodeExpriedTime = validationCodeExpriedTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

	public Date getResetPwdExpriedTime() {
		return resetPwdExpriedTime;
	}

	public void setResetPwdExpriedTime(Date resetPwdExpriedTime) {
		this.resetPwdExpriedTime = resetPwdExpriedTime;
	}

}
