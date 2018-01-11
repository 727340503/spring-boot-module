package com.cherrypicks.tcc.model;

public class Share extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 8985890635625552991L;

	private Long merchantId;

	private String fbAppId;

	private String type;

	private String title;

	private String image;

	private String descr;

    public String getFbAppId() {
        return fbAppId;
    }

    public void setFbAppId(final String fbAppId) {
        this.fbAppId = fbAppId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(final String descr) {
        this.descr = descr;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

}