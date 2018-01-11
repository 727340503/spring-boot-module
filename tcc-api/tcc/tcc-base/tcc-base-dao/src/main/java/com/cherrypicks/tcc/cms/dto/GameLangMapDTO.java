package com.cherrypicks.tcc.cms.dto;

public class GameLangMapDTO extends BaseObject {

	private static final long serialVersionUID = 3202269414740081070L;

	private Long id;
	private String merchantName;
	private String img;
	private String fullImg;
	private String name;
	private String langCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getFullImg() {
		return fullImg;
	}

	public void setFullImg(String fullImg) {
		this.fullImg = fullImg;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
