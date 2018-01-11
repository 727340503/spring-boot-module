package com.cherrypicks.tcc.model;

public class GameLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4443785919749890043L;

	/**  */
	private Long gameId;

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
	public Long getGameId() {
		return this.gameId;
	}

	/**
	 * 设置
	 *
	 * @param gameId
	 *
	 */
	public void setGameId(final Long gameId) {
		this.gameId = gameId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getImg() {
		return this.img;
	}

	/**
	 * 设置
	 *
	 * @param image
	 *
	 */
	public void setImg(final String img) {
		this.img = img;
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

}