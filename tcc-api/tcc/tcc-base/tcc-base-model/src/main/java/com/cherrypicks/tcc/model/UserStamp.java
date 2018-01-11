package com.cherrypicks.tcc.model;

import java.util.Date;
public class UserStamp extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = -4070924742078995137L;

    public enum UserStampStatus {
        ACTIVE(1), REDEEMED(2);

        private final int value;

        UserStampStatus(final int value) {
            this.value = value;
        }

        public int toValue() {
            return this.value;
        }
    }

    /**  */
    private Long userId;

    /**  */
    private Long merchantId;

    /**  */
    private Long stampId;

    /**  */
    private Long userStampCardId;

    /** 1- Active 2- Redeemed */
    private Integer status;

    /**  */

    private Date redeemedDate;

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
    public Long getStampId() {
        return this.stampId;
    }

    /**
     * 设置
     *
     * @param stampId
     *
     */
    public void setStampId(final Long stampId) {
        this.stampId = stampId;
    }

    /**
     * 获取1- Active 2- Redeemed
     *
     * @return 1- Active 2- Redeemed
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置1- Active 2- Redeemed
     *
     * @param status
     *          1- Active 2- Redeemed
     */
    public void setStatus(final Integer status) {
        this.status = status;
    }

    /**
     * 获取
     *
     * @return
     */
    public Date getRedeemedDate() {
        return this.redeemedDate;
    }

    /**
     * 设置
     *
     * @param redeemedDate
     *
     */
    public void setRedeemedDate(final Date redeemedDate) {
        this.redeemedDate = redeemedDate;
    }

    /**
     * 获取
     *
     * @return
     */
    public Long getUserStampCardId() {
        return userStampCardId;
    }

    /**
     * 设置
     *
     * @param userStampCardId
     *
     */
    public void setUserStampCardId(final Long userStampCardId) {
        this.userStampCardId = userStampCardId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }


}