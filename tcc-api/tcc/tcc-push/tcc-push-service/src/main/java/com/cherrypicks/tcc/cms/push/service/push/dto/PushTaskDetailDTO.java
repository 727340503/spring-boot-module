package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;
import java.util.Date;


public class PushTaskDetailDTO implements Serializable {

	private static final long serialVersionUID = -6645143199069053730L;
	
	private Long pushTaskId;
	
	private String pushEngine;
	
	private Integer engineId;
	
	private Integer requestDeviceCount;
	
	private Integer sendDeviceCount;
	
	private Integer status;
	
	private Date finishedTime;

	private Integer reportValid;
	
	private String reportFile;
	
	private String inputFile;
	
	public Long getPushTaskId() {
		return pushTaskId;
	}

	public void setPushTaskId(Long pushTaskId) {
		this.pushTaskId = pushTaskId;
	}

	public String getPushEngine() {
		return pushEngine;
	}

	public void setPushEngine(String pushEngine) {
		this.pushEngine = pushEngine;
	}

	public Integer getEngineId() {
		return engineId;
	}

	public void setEngineId(Integer engineId) {
		this.engineId = engineId;
	}

	public Integer getSendDeviceCount() {
		return sendDeviceCount;
	}

	public void setSendDeviceCount(Integer sendDeviceCount) {
		this.sendDeviceCount = sendDeviceCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRequestDeviceCount() {
		return requestDeviceCount;
	}

	public void setRequestDeviceCount(Integer requestDeviceCount) {
		this.requestDeviceCount = requestDeviceCount;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Integer getReportValid() {
		return reportValid;
	}

	public void setReportValid(Integer reportValid) {
		this.reportValid = reportValid;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
}
