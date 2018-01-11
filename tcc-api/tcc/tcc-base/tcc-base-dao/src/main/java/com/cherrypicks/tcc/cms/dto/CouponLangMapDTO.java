package com.cherrypicks.tcc.cms.dto;

public class CouponLangMapDTO extends BaseObject {

	private static final long serialVersionUID = 157361464758227222L;

	private Long id;
	private String name;
	private String campaignName;
	private String merchantName;
	private String terms;
	private String img;
	private String fullImg;
	private String descr;
	private String langCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}
