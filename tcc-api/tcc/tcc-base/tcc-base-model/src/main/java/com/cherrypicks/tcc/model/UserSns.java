package com.cherrypicks.tcc.model;

public class UserSns extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = -3827788483518747141L;

    /**  */
    private Long userId;

    /**  */
    private String snsId;

    /** 1-Facebook */
    private Integer snsType;

    public enum UserSnsType{
        FACE_BOOK(1,"Facebook");

        private final int value;
        private final String info;

        UserSnsType(final int value,final String info) {
            this.value = value;
            this.info = info;
        }

        public int toValue() {
            return this.value;
        }
        
        public String toInfo(){
        	return this.info;
        }
    }

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
    public String getSnsId() {
        return this.snsId;
    }

    /**
     * 设置
     *
     * @param snsId
     *
     */
    public void setSnsId(final String snsId) {
        this.snsId = snsId;
    }

    /**
     * 获取1-Facebook
     *
     * @return 1-Facebook
     */
    public Integer getSnsType() {
        return this.snsType;
    }

    /**
     * 设置1-Facebook
     *
     * @param snsType
     *          1-Facebook
     */
    public void setSnsType(final Integer snsType) {
        this.snsType = snsType;
    }
}