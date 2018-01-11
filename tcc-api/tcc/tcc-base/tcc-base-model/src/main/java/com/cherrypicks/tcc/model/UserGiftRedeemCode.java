package com.cherrypicks.tcc.model;

import java.util.Date;

public class UserGiftRedeemCode extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = -4070924742078995137L;

    /**  */
    private Long merchantId;

    /**  */
    private Long userId;

    /**  */
    private Long userStampCardId;

    /**  */
    private Long campaignGiftMapId;

    /**  */
    private Long campaignGiftExchangeTypeId;

    /**  */
    private Date expiryTime;

    /**  */
    private Integer isRedeem;

    /**  */
    private Integer isScan ;

    /**  */
    private Integer scanStatus;


    /**  */
    private String msg;

    /**  */

    private String redeemCode;


    /**
     * 获取
     *
     * @return
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * 设置
     *
     * @param userId
     *
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(final Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getRedeemCode() {
        return redeemCode;
    }

    /**
     * 设置
     *
     * @param redeemCode
     *
     */
    public void setRedeemCode(final String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public Long getUserStampCardId() {
        return userStampCardId;
    }

    public void setUserStampCardId(final Long userStampCardId) {
        this.userStampCardId = userStampCardId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCampaignGiftExchangeTypeId() {
        return campaignGiftExchangeTypeId;
    }

    public void setCampaignGiftExchangeTypeId(final Long campaignGiftExchangeTypeId) {
        this.campaignGiftExchangeTypeId = campaignGiftExchangeTypeId;
    }

    public Long getCampaignGiftMapId() {
        return campaignGiftMapId;
    }

    public void setCampaignGiftMapId(final Long campaignGiftMapId) {
        this.campaignGiftMapId = campaignGiftMapId;
    }

    public Integer getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(final Integer scanStatus) {
        this.scanStatus = scanStatus;
    }

    public Integer getIsScan() {
        return isScan;
    }

    public void setIsScan(final Integer isScan) {
        this.isScan = isScan;
    }

    public Integer getIsRedeem() {
        return isRedeem;
    }

    public void setIsRedeem(final Integer isRedeem) {
        this.isRedeem = isRedeem;
    }

}