package com.cherrypicks.tcc.model;

import java.util.Date;

/**
 * @version 1.0.0 2017-03-07
 */
public class ScheduleJob  extends BaseModel {
	
	/** 版本号 */
	private static final long serialVersionUID = -1416521068008743320L;

	
	/**  */
	private String uuid;

	/**  */
	private Date scheduleExecuteDate;
	
	/**  */
	private Date newDate;
	
	/**
	 * 获取
	 * 
	 * @return
	 */
	public Date getNewDate() {
		return newDate;
	}

	/**
	 * 设置
	 * 
	 * @param newDate
	 * 
	 */
	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * 设置
	 * 
	 * @param uuid
	 * 
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Date getScheduleExecuteDate() {
		return scheduleExecuteDate;
	}

	/**
	 * 设置
	 * 
	 * @param uuid
	 * 
	 */
	public void setScheduleExecuteDate(Date scheduleExecuteDate) {
		this.scheduleExecuteDate = scheduleExecuteDate;
	}


}