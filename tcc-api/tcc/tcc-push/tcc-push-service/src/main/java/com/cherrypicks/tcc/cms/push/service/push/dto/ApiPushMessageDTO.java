package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiPushMessageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2550163755065755757L;

	private ApiMessageBodyDTO messageBody;
	
	private List<ApiPushDeviceDTO> deviceList;

	public ApiMessageBodyDTO getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(ApiMessageBodyDTO messageBody) {
		this.messageBody = messageBody;
	}

	public List<ApiPushDeviceDTO> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<ApiPushDeviceDTO> deviceList) {
		this.deviceList = deviceList;
	}

	public void addDevice(ApiPushDeviceDTO device){
		if(deviceList == null){
			deviceList = new ArrayList<ApiPushDeviceDTO>();
		}
		deviceList.add(device);
	}

}
