package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;

public class PushTaskDTO implements Serializable {
	
	private static final long serialVersionUID = 4970033001243585976L;

	private Long pushTaskId;
	
	private Integer pushModel;

	private String startTime;
	
	private String endTime;
	
	private Integer pushType;
	
	private String finishedTime;
	
	private Boolean iosSupport = false;
	
	private Boolean aosSupport = false;
	
	private Boolean baiduAosSupport = false;
	
	private String email;
	
	private Boolean emailSend = false; 
	
	private Integer status;

	private String createFrom;
	
	private Long pushProjectId;
	
	private String iosDeviceFile;
	
	private String aosDeviceFile;
	
	private String baiduAosDeviceFile;
	
	private Integer taskType;
	
	private String createdTime;
	
	private String projectName;
	
	private String msgEn;
	
	private String msgTc;
	
	private String msgCn;
	
	public String getCreateFrom() {
		return createFrom;
	}

	public void setCreateFrom(String createFrom) {
		this.createFrom = createFrom;
	}

	public Long getPushProjectId() {
		return pushProjectId;
	}

	public void setPushProjectId(Long pushProjectId) {
		this.pushProjectId = pushProjectId;
	}

	public Integer getPushModel() {
		return pushModel;
	}

	public void setPushModel(Integer pushModel) {
		this.pushModel = pushModel;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public String getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Boolean getIosSupport() {
		return iosSupport;
	}

	public void setIosSupport(Boolean iosSupport) {
		this.iosSupport = iosSupport;
	}

	public Boolean getAosSupport() {
		return aosSupport;
	}

	public void setAosSupport(Boolean aosSupport) {
		this.aosSupport = aosSupport;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailSend() {
		return emailSend;
	}

	public void setEmailSend(Boolean emailSend) {
		this.emailSend = emailSend;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIosDeviceFile() {
		return iosDeviceFile;
	}

	public void setIosDeviceFile(String iosDeviceFile) {
		this.iosDeviceFile = iosDeviceFile;
	}

	public String getAosDeviceFile() {
		return aosDeviceFile;
	}

	public void setAosDeviceFile(String aosDeviceFile) {
		this.aosDeviceFile = aosDeviceFile;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Boolean getBaiduAosSupport() {
		return baiduAosSupport;
	}

	public void setBaiduAosSupport(Boolean baiduAosSupport) {
		this.baiduAosSupport = baiduAosSupport;
	}

	public String getBaiduAosDeviceFile() {
		return baiduAosDeviceFile;
	}

	public void setBaiduAosDeviceFile(String baiduAosDeviceFile) {
		this.baiduAosDeviceFile = baiduAosDeviceFile;
	}

	public Long getPushTaskId() {
		return pushTaskId;
	}

	public void setPushTaskId(Long pushTaskId) {
		this.pushTaskId = pushTaskId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMsgEn() {
		return msgEn;
	}

	public void setMsgEn(String msgEn) {
		this.msgEn = msgEn;
	}

	public String getMsgTc() {
		return msgTc;
	}

	public void setMsgTc(String msgTc) {
		this.msgTc = msgTc;
	}

	public String getMsgCn() {
		return msgCn;
	}

	public void setMsgCn(String msgCn) {
		this.msgCn = msgCn;
	}
}
