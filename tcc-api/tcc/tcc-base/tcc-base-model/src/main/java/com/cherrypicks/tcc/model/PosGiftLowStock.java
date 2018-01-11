package com.cherrypicks.tcc.model;

import java.util.Date;

public class PosGiftLowStock extends BaseModel {

    private static final long serialVersionUID = -5887063519329150467L;

    private String merchantCode;

	private String externalGiftId;

	private Date statusDateTime;

	private String sign;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(final String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(final String sign) {
        this.sign = sign;
    }

    public String getExternalGiftId() {
        return externalGiftId;
    }

    public void setExternalGiftId(final String externalGiftId) {
        this.externalGiftId = externalGiftId;
    }

    public Date getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(final Date statusDateTime) {
        this.statusDateTime = statusDateTime;
    }

}

