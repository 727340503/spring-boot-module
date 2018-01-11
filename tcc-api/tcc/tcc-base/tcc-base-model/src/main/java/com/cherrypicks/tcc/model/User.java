package com.cherrypicks.tcc.model;

import java.util.Date;

public class User extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = 5280027843179968727L;

    public enum UserType{
        TEMP_USER(0,"Temp user"),EMAIL(1,"Email"),PHONE(2,"Phone"),SNS(3,"SNS");

        private final int value;
        private final String info;

        UserType(final int value,final String info) {
            this.value = value;
            this.info = info;
        }

        public int toValue() {
            return this.value;
        }

        public String toInfo(){
        	return this.info;
        }
    }

    public enum UserStatus{
        IN_ACTIVE(0,"Inactive"),ACTIVE(1,"Active");

        private final int value;
        private final String info;

        UserStatus(final int value,final String info) {
            this.value = value;
            this.info = info;
        }

        public int toValue() {
            return this.value;
        }

        public String toInfo(){
        	return this.info;
        }
    }

    /**  */
    private Long merchantId;

    /**  */
    private String userName;

    /**  */
    private String firstName;

    /**  */
    private String lastName;

    /**  */
    private String password;

    /**  */
    private String icon;

    /**  */
    private String session;

    /** 0-Temporary user 1-Email user 2-Phone user 3-SNS user */
    private Integer userType;

    /**  */
    private String email;

    /**  */
    private String contactEmail;

    /** 0-false 1-true */
    private Boolean isEmailValidation;

   /* *//**  *//*
    private String emailValidationCode;

    *//**  *//*
    private Date validationCodeExpriedTime;

    *//**  *//*
    private Date resetPwdExpriedTime;*/

    /**  */
    private String phone;

    /**  */
    private String phoneAreaCode;

    /** 0-false 1-true */
    private Boolean isPhoneValidation;

    private String gender;

    private Date birthday;

    private String langCode;

    private Boolean isGrant;

    private Boolean isMarketingInfo;

    private Date lastMobileVerifyTime;

    /**
     * 获取
     *
     * @return
     */
    public Long getMerchantId() {
        return this.merchantId;
    }

    /**
     * 设置
     *
     * @param merchantId
     *
     */
    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置
     *
     * @param password
     *
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getIcon() {
        return this.icon;
    }

    /**
     * 设置
     *
     * @param icon
     *
     */
    public void setIcon(final String icon) {
        this.icon = icon;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getSession() {
        return this.session;
    }

    /**
     * 设置
     *
     * @param session
     *
     */
    public void setSession(final String session) {
        this.session = session;
    }

    /**
     * 获取0-Temporary user 1-Email user 2-Phone user 3-SNS user
     *
     * @return 0-Temporary user 1-Email user 2-Phone user 3-SNS user
     */
    public Integer getUserType() {
        return this.userType;
    }

    /**
     * 设置0-Temporary user 1-Email user 2-Phone user 3-SNS user
     *
     * @param userType
     *          0-Temporary user 1-Email user 2-Phone user 3-SNS user
     */
    public void setUserType(final Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 设置
     *
     * @param email
     *
     */
    public void setEmail(final String email) {
        this.email = email;
    }


    public Boolean getIsPhoneValidation() {
        return isPhoneValidation;
    }

    public void setIsPhoneValidation(final Boolean isPhoneValidation) {
        this.isPhoneValidation = isPhoneValidation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    public void setPhoneAreaCode(final String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(final String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(final String langCode) {
        this.langCode = langCode;
    }

 /*   public String getEmailValidationCode() {
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

    public Date getResetPwdExpriedTime() {
        return resetPwdExpriedTime;
    }

    public void setResetPwdExpriedTime(final Date resetPwdExpriedTime) {
        this.resetPwdExpriedTime = resetPwdExpriedTime;
    }*/

    public Boolean getIsEmailValidation() {
        return isEmailValidation;
    }

    public void setIsEmailValidation(final Boolean isEmailValidation) {
        this.isEmailValidation = isEmailValidation;
    }

    public Boolean getIsGrant() {
        return isGrant;
    }

    public void setIsGrant(final Boolean isGrant) {
        this.isGrant = isGrant;
    }

    public Boolean getIsMarketingInfo() {
        return isMarketingInfo;
    }

    public void setIsMarketingInfo(final Boolean isMarketingInfo) {
        this.isMarketingInfo = isMarketingInfo;
    }

    public Date getLastMobileVerifyTime() {
        return lastMobileVerifyTime;
    }

    public void setLastMobileVerifyTime(final Date lastMobileVerifyTime) {
        this.lastMobileVerifyTime = lastMobileVerifyTime;
    }
}