package com.cherrypicks.tcc.model;

public class BannerLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4443785919749890043L;

	/**  */
	private Long bannerId;

	private String name;

	/**  */
	private String img;

	/**  */
	private String langCode;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getBannerId() {
		return this.bannerId;
	}

	/**
	 * 设置
	 *
	 * @param bannerId
	 *
	 */
	public void setBannerId(final Long bannerId) {
		this.bannerId = bannerId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getImg() {
		return img;
	}

}