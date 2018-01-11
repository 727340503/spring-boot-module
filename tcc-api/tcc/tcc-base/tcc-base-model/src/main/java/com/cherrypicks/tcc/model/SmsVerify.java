package com.cherrypicks.tcc.model;

import java.util.Date;

public class SmsVerify extends BaseModel {

    private static final long serialVersionUID = -1994257540710172035L;

    public enum SmsVerifyStatus {

        ACCEPTED(0), PENDING(1), UNDELIVERABLE(2), DELIVERED(3), EXPIRED(4), REJECTED(5), VERYFIED(11);

        private final int value;

        SmsVerifyStatus(final int value) {
            this.value = value;
        }

        public int toValue() {
            return this.value;
        }
    }

    public enum SmsVerifyType {

        REGISTER(1), LOGIN(2),  CHANGE_PHONE(3);

        private final int value;

        SmsVerifyType(final int value) {
            this.value = value;
        }

        public int toValue() {
            return this.value;
        }
    }
    private Long merchantId;
    private String phone;
    private String phoneAreaCode;
    private String verifyCode;
    private Date expiryTime;
    private Integer status;
    private String messageId;
    private Integer verifyType;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(final String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(final Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    public Integer getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(final Integer verifyType) {
        this.verifyType = verifyType;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

}
