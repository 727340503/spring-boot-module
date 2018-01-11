package com.cherrypicks.tcc.cms.push.service.push.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PushNotificationDTO implements Serializable{

	private static final long serialVersionUID = 5935714236717193701L;

	public PushNotificationDTO() {
		this.pushModel = PushModel.NOW.toValue();
		this.pushFilterType = PushFilterType.USER_ID.toValue();
		this.pushMultipleMessage = PushMultipleMessage.FALSE.toValue();
	}
	
	public PushNotificationDTO(final Integer pushModel) {
		this.pushModel = pushModel;
		this.pushFilterType = PushFilterType.USER_ID.toValue();
		this.pushMultipleMessage = PushMultipleMessage.FALSE.toValue();
	}

	public PushNotificationDTO(final String msgtitle) {
		this.pushModel = PushModel.NOW.toValue();
		this.pushFilterType = PushFilterType.USER_ID.toValue();
		this.pushMultipleMessage = PushMultipleMessage.FALSE.toValue();
		this.pushParameters = new ArrayList<ApiPushParameterDTO>();
		this.pushParameters.add(new ApiPushParameterDTO("msgtitle", msgtitle));
	}

	private Integer pushModel;
	private Integer pushFilterType;
	private final Integer pushMultipleMessage;
	private Date pushTime;

	private List<PushMsgDTO> pushMsgs;

	private List<ApiPushParameterDTO> pushParameters;

	public Integer getPushModel() {
		return pushModel;
	}

	public void setPushModel(final Integer pushModel) {
		this.pushModel = pushModel;
	}

	public Integer getPushFilterType() {
		return pushFilterType;
	}

	public void setPushFilterType(final Integer pushFilterType) {
		this.pushFilterType = pushFilterType;
	}

	public Integer getPushMultipleMessage() {
		return pushMultipleMessage;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(final Date pushTime) {
		this.pushTime = pushTime;
	}

	public List<PushMsgDTO> getPushMsgs() {
		return pushMsgs;
	}

	public void setPushMsgs(List<PushMsgDTO> pushMsgs) {
		this.pushMsgs = pushMsgs;
	}

	public List<ApiPushParameterDTO> getPushParameters() {
		return pushParameters;
	}

	public void setPushParameters(final List<ApiPushParameterDTO> pushParameters) {
		this.pushParameters = pushParameters;
	}

	public enum PushModel {

		NOW(1), SCHEDULE(2);

		private int value;

		PushModel(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum PushFilterType {

		DEVICE_TOKEN(1), USER_ID(2);

		private int value;

		PushFilterType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum PushMultipleMessage {

		FALSE(0), TRUE(1);

		private int value;

		PushMultipleMessage(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum PushType {

		BATCH(1), DEVICES(2);

		private int value;

		PushType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

}