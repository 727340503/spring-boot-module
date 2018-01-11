package com.cherrypicks.tcc.model;

public class UserFriend extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = -5611796686008635604L;

    public enum UserFriendStatus {
        INVITED(0),ACCEPT(1);

        private final int value;

        UserFriendStatus(final int value) {
            this.value = value;
        }

        public int toValue() {
            return this.value;
        }
    }

    public enum UserFriendReadStatus {
        UN_READ(0),READ(1);

        private final int value;

        UserFriendReadStatus(final int value) {
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
    private Long friendUserId;

    /** */
    private Integer status;

    /** */
    private Integer readStatus;

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
    public Long getFriendUserId() {
        return this.friendUserId;
    }

    /**
     * 设置
     *
     * @param friendUserId
     *
     */
    public void setFriendUserId(final Long friendUserId) {
        this.friendUserId = friendUserId;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(final Integer status) {
        this.status = status;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(final Integer readStatus) {
        this.readStatus = readStatus;
    }

}