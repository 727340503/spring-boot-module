package com.cherrypicks.tcc.model;

import java.util.Date;

public class PosTransStamp extends BaseModel {

    private static final long serialVersionUID = 1834845671294249682L;

    private String merchantCode;

	private String externalStoreId;

	private Date transDateTime;

	private String transNo;

	private Double transAmt;

	private String currency;

	private String collectCode;

	private Long stamps;

	private String issueType;

	private String itemCodeQty;

	private String coupons;

	private String sign;

	public enum IssueTypeType {

        MONEY_TO_STAMPS("0"), PAPER_TO_STAMPS("1"), STAMPS_DIRECTLY("2");

        private final String value;

        IssueTypeType(final String value) {
            this.value = value;
        }

        public String toValue() {
            return this.value;
        }
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(final String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getExternalStoreId() {
        return externalStoreId;
    }

    public void setExternalStoreId(final String externalStoreId) {
        this.externalStoreId = externalStoreId;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(final String transNo) {
        this.transNo = transNo;
    }

    public Double getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(final Double transAmt) {
        this.transAmt = transAmt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getCollectCode() {
        return collectCode;
    }

    public void setCollectCode(final String collectCode) {
        this.collectCode = collectCode;
    }

    public Long getStamps() {
        return stamps;
    }

    public void setStamps(final Long stamps) {
        this.stamps = stamps;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(final String issueType) {
        this.issueType = issueType;
    }

    public String getItemCodeQty() {
        return itemCodeQty;
    }

    public void setItemCodeQty(final String itemCodeQty) {
        this.itemCodeQty = itemCodeQty;
    }

    public String getCoupons() {
        return coupons;
    }

    public void setCoupons(final String coupons) {
        this.coupons = coupons;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(final String sign) {
        this.sign = sign;
    }

    public Date getTransDateTime() {
        return transDateTime;
    }

    public void setTransDateTime(final Date transDateTime) {
        this.transDateTime = transDateTime;
    }

}

