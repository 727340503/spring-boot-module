package com.cherrypicks.tcc.model;

public class Stamp extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4167662183305331221L;

	/**  */
	private Long stampCardId;

	public Long getStampCardId() {
		return stampCardId;
	}

	public void setStampCardId(final Long stampCardId) {
		this.stampCardId = stampCardId;
	}

}