package com.cherrypicks.tcc.model;

public class SmsLog extends BaseModel {

    private static final long serialVersionUID = -1994257540710172035L;

    private Long merchantId;
    private String phone;
    private String messageId;
    private String text;
    private String result;
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(final String phone) {
        this.phone = phone;
    }
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }
    public String getText() {
        return text;
    }
    public void setText(final String text) {
        this.text = text;
    }
    public String getResult() {
        return result;
    }
    public void setResult(final String result) {
        this.result = result;
    }

}
