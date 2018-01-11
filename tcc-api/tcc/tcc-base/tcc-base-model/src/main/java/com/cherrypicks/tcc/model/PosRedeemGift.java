package com.cherrypicks.tcc.model;

import java.util.Date;

public class PosRedeemGift  extends BaseModel {

    private static final long serialVersionUID = 7969749174199253576L;

    private String merchantCode;

	private String externalStoreId;

	private Date transDateTime;

	private String transNo;

	private Double transAmt;

	private String currency;

	private String redeemCode;

	private String itemCodeQty;

	private String sign;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(final String merchantCode) {
        this.merchantCode = merchantCode;
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

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(final String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public String getItemCodeQty() {
        return itemCodeQty;
    }

    public void setItemCodeQty(final String itemCodeQty) {
        this.itemCodeQty = itemCodeQty;
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

    public String getExternalStoreId() {
        return externalStoreId;
    }

    public void setExternalStoreId(final String externalStoreId) {
        this.externalStoreId = externalStoreId;
    }

}

