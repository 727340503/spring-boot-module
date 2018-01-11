package com.cherrypicks.tcc.model;

public class UserStampCard extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = -4070924742078995137L;

    /**  */
    private Long userId;

    /**  */
    private Long merchantId;

    private Long campaignId;

    /**  */
    private Long stampCardId;

    /**  */
    private Long collectStampQty;

    /**  */
    private String collectCode;

    /**  */
    private Integer isScan ;

    /**  */
    private Integer scanStatus;

    /**  */
    private String msg;

    /**  */
    private Integer grantStampQty;

    /**  */
    private Boolean isPopGrant;

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

    /**
     * 获取
     *
     * @return
     */
    public Long getCollectStampQty() {
        return this.collectStampQty;
    }

    /**
     * 设置
     *
     * @param collectStampQty
     *
     */
    public void setCollectStampQty(final Long collectStampQty) {
        this.collectStampQty = collectStampQty;
    }

    /**
     * 获取
     *
     * @return
     */
    public Long getStampCardId() {
        return stampCardId;
    }

    /**
     * 设置
     *
     * @param stampCardId
     *
     */
    public void setStampCardId(final Long stampCardId) {
        this.stampCardId = stampCardId;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getCollectCode() {
        return collectCode;
    }

    /**
     * 设置
     *
     * @param collectCode
     *
     */
    public void setCollectCode(final String collectCode) {
        this.collectCode = collectCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public Integer getIsScan() {
        return isScan;
    }

    public void setIsScan(final Integer isScan) {
        this.isScan = isScan;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(final Long campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(final Integer scanStatus) {
        this.scanStatus = scanStatus;
    }

    public Integer getGrantStampQty() {
        return grantStampQty;
    }

    public void setGrantStampQty(final Integer grantStampQty) {
        this.grantStampQty = grantStampQty;
    }

    public Boolean getIsPopGrant() {
        return isPopGrant;
    }

    public void setIsPopGrant(final Boolean isPopGrant) {
        this.isPopGrant = isPopGrant;
    }

}