package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiPushTaskDTO implements Serializable {

	private static final long serialVersionUID = -4370810659982596635L;

	//if pushType = batch, ignore this field.
	private Integer multipleMessage;

	//if pushType = batch, ignore this field.
	private Integer filterDeviceType;

	private String projectName;

	private Integer pushModel;

	private Long startTime;

	private Long endTime;

	private Integer pushType;

	private String email;

	private Boolean iosSupport = false;

	private Boolean aosSupport = false;

	private Boolean baiduAosSupport = false;

	private List<ApiPushMessageDTO> messageList;

	private List<ApiPushDeviceDTO> deviceList;

	public Integer getMultipleMessage() {
		return multipleMessage;
	}

	public void setMultipleMessage(final Integer multipleMessage) {
		this.multipleMessage = multipleMessage;
	}

	public Integer getFilterDeviceType() {
		return filterDeviceType;
	}

	public void setFilterDeviceType(final Integer filterDeviceType) {
		this.filterDeviceType = filterDeviceType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public Integer getPushModel() {
		return pushModel;
	}

	public void setPushModel(final Integer pushModel) {
		this.pushModel = pushModel;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(final Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(final Long endTime) {
		this.endTime = endTime;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(final Integer pushType) {
		this.pushType = pushType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Boolean getIosSupport() {
		return iosSupport;
	}

	public void setIosSupport(final Boolean iosSupport) {
		this.iosSupport = iosSupport;
	}

	public Boolean getAosSupport() {
		return aosSupport;
	}

	public void setAosSupport(final Boolean aosSupport) {
		this.aosSupport = aosSupport;
	}

	public List<ApiPushMessageDTO> getMessageList() {
		return messageList;
	}

	public void setMessageList(final List<ApiPushMessageDTO> messageList) {
		this.messageList = messageList;
	}

	public void addMessage(final ApiPushMessageDTO message){
		if(messageList == null){
			messageList = new ArrayList<ApiPushMessageDTO>();
		}
		messageList.add(message);
	}

	public List<ApiPushDeviceDTO> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(final List<ApiPushDeviceDTO> deviceList) {
		this.deviceList = deviceList;
	}

	public void addDevice(final ApiPushDeviceDTO device){
		if(deviceList == null){
			deviceList = new ArrayList<ApiPushDeviceDTO>();
		}
		deviceList.add(device);
	}

	public Boolean getBaiduAosSupport() {
		return baiduAosSupport;
	}

	public void setBaiduAosSupport(final Boolean baiduAosSupport) {
		this.baiduAosSupport = baiduAosSupport;
	}


}
