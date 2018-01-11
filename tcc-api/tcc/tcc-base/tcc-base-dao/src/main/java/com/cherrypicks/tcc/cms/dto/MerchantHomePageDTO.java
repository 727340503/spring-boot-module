package com.cherrypicks.tcc.cms.dto;

import java.util.List;



public class MerchantHomePageDTO extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 2433629486187134245L;

	private String homePage;
	private List<HomePageDetailDTO> records;

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(final String homePage) {
		this.homePage = homePage;
	}

	public List<HomePageDetailDTO> getRecords() {
		return records;
	}

	public void setRecords(final List<HomePageDetailDTO> records) {
		this.records = records;
	}

}
