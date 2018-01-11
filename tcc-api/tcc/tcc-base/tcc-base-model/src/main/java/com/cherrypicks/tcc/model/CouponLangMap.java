package com.cherrypicks.tcc.model;

public class CouponLangMap extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = 5932348515951898938L;

    /**  */
    private Long couponId;

    /**  */
    private String name;

    /**  */
    private String terms;

    /**  */
    private String img;

    /**  */
    private String descr;

    /**  */
    private String langCode;

    /**
     * 获取
     *
     * @return
     */
    public Long getCouponId() {
        return this.couponId;
    }

    /**
     * 设置
     *
     * @param couponId
     *
     */
    public void setCouponId(final Long couponId) {
        this.couponId = couponId;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置
     *
     * @param name
     *
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getDescr() {
        return this.descr;
    }

    /**
     * 设置
     *
     * @param descr
     *
     */
    public void setDescr(final String descr) {
        this.descr = descr;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getLangCode() {
        return this.langCode;
    }

    /**
     * 设置
     *
     * @param langCode
     *
     */
    public void setLangCode(final String langCode) {
        this.langCode = langCode;
    }

    /**
     * 获取
     *
     * @return
     */

    public String getTerms() {
        return terms;
    }

    /**
     * 设置
     *
     * @param terms
     *
     */
    public void setTerms(final String terms) {
        this.terms = terms;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置
     *
     * @param img
     *
     */
    public void setImg(final String img) {
        this.img = img;
    }

}