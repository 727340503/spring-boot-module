package com.cherrypicks.tcc.model;

import java.util.Date;

public class PosReservationPickUp extends BaseModel {

    private static final long serialVersionUID = -6803892369966689683L;

    private String merchantCode;

	private String externalStoreId;

	private Date updateDateTime;

	private String reservationCode;

	private String itemCodeQty;

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

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(final String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public String getItemCodeQty() {
        return itemCodeQty;
    }

    public void setItemCodeQty(final String itemCodeQty) {
        this.itemCodeQty = itemCodeQty;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getExternalStoreId() {
        return externalStoreId;
    }

    public void setExternalStoreId(final String externalStoreId) {
        this.externalStoreId = externalStoreId;
    }

}

