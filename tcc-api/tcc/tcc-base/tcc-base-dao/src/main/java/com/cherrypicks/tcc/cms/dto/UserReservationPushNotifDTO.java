package com.cherrypicks.tcc.cms.dto;

import java.util.Date;

public class UserReservationPushNotifDTO extends BaseObject {

	private static final long serialVersionUID = 5751360222274780892L;

	private Long userId;
	private String userName;
	private String langCode;
	private String timeZone;
	private String giftName;
	private String dateFormat;
	private Date reservationExpiredTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Date getReservationExpiredTime() {
		return reservationExpiredTime;
	}

	public void setReservationExpiredTime(Date reservationExpiredTime) {
		this.reservationExpiredTime = reservationExpiredTime;
	}

}
