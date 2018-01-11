package com.cherrypicks.tcc.cms.push.service.push.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cherrypicks.tcc.cms.exception.PushCancelTaskException;
import com.cherrypicks.tcc.cms.exception.PushRegisterDeviceTokenException;
import com.cherrypicks.tcc.cms.exception.PushSearchTaskDetailException;
import com.cherrypicks.tcc.cms.exception.PushSearchTaskException;
import com.cherrypicks.tcc.cms.exception.PushUnbindUserException;
import com.cherrypicks.tcc.cms.push.service.push.PushService;
import com.cherrypicks.tcc.cms.push.service.push.dto.ApiMessageBodyDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.ApiPushDeviceDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.ApiPushMessageDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.ApiPushParameterDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.ApiPushTaskDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushMsgDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushNotificationDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushResultDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushTaskDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushTaskDetailDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushNotificationDTO.PushModel;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushNotificationDTO.PushType;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.JsonUtil;

@Service
public class PushServiceImpl implements PushService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${push.register.user.url:''}")
    private String pushRegisterUerUrl;

    @Value("${push.create.push.task.url:''}")
    private String pushCreatePushTaskUrl;

    @Value("${push.unbind.user.url:''}")
    private String pushUnbindUserUrl;

//    @Value("${push.notification.projectId:''}")
//    private String pushNotificationProjectId;
    
    //set send active,add by swing 2016/11/07
    @Value("${send.push.active:true}")
    private Boolean sendPushActive;
    
    @Value("${push.search.task.url:''}")
    private String searchPushTaskUrl;
    
    @Value("${push.search.task.detail.url:''}")
    private String searchPushTaskDetailUrl;
    
    @Value("${push.cancel.task.url:''}")
    private String cancelPushTaskUrl;
    
	@Override
	@SuppressWarnings("unchecked")
    public void registerDeviceToken(final Object userSessionId, final String deviceToken, final Integer deviceType, final String lang, final String pushNotificationProjectId) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("deviceToken", deviceToken);
        params.put("deviceType", deviceType.toString());
        params.put("status", "1"); // 1 ACTIVE
        params.put("langId", lang);
        params.put("projectName", pushNotificationProjectId);
        params.put("userId", userSessionId.toString());
        final String jsonResult = HttpClientUtil.getInstance().sendHttpPost(pushRegisterUerUrl, params);

        final PushResultDTO<Object> resultVo = JsonUtil.toObject(jsonResult, PushResultDTO.class);
        if (resultVo == null || resultVo.getErrorCode() != 0) {
            throw new PushRegisterDeviceTokenException(I18nUtil.getMessage(CmsCodeStatus.REGISTER_DEVICE_TOKEN_ERROR, null, lang) + (resultVo != null ? resultVo.getErrorMessage() : ""));
        }
    }

    @Override
    public List<PushResultDTO<Object>> pushMsgAllUser(final PushNotificationDTO pushNotification, final String pushNotificationProjectId) throws Exception {
        return pushMsgByDevices(pushNotification, true, null,pushNotificationProjectId);
    }

    @Override
    public List<PushResultDTO<Object>> pushMsgByUserId(final PushNotificationDTO pushNotification, final Object userId, final String pushNotificationProjectId) throws Exception {
        final List<Object> userIds = new ArrayList<Object>(1);
        userIds.add(userId);
        return pushMsgByUserIds(pushNotification, userIds, pushNotificationProjectId);
    }

    @Override
    public List<PushResultDTO<Object>> pushMsgByUserIds(final PushNotificationDTO pushNotification, final List<Object> userSessionIds, final String pushNotificationProjectId) throws Exception {
    	//set send active,add by swing 2016/11/07
		if (!sendPushActive) {
			logger.error("push unsucess:Send push active is false.");
			return null;
		}
    	
        if (userSessionIds == null || userSessionIds.isEmpty()) {
            logger.error("push unsucess: userSessionIds is null.");
            return null;
        }

        final List<ApiPushDeviceDTO> apiPushDevices = new ArrayList<ApiPushDeviceDTO>();
        for (final Object userSessionId : userSessionIds) {
            final ApiPushDeviceDTO dto = new ApiPushDeviceDTO();
            dto.setUserId(userSessionId);

            apiPushDevices.add(dto);
        }
        return pushMsgByDevices(pushNotification, apiPushDevices,pushNotificationProjectId);
    }

    private List<PushResultDTO<Object>> pushMsgByDevices(final PushNotificationDTO pushNotification, final List<ApiPushDeviceDTO> apiPushDevices, final String pushNotificationProjectId) throws Exception {
        if (apiPushDevices == null || apiPushDevices.isEmpty()) {
            return null;
        }
        return pushMsgByDevices(pushNotification, false, apiPushDevices,pushNotificationProjectId);
    }

    @SuppressWarnings("unchecked")
	private List<PushResultDTO<Object>> pushMsgByDevices(final PushNotificationDTO pushNotification, final boolean isAllAppUser, final List<ApiPushDeviceDTO> apiPushDevices, final String pushNotificationProjectId) throws Exception {
        final List<PushResultDTO<Object>> result = new ArrayList<PushResultDTO<Object>>();
        // 1. get parameter for push
        final List<ApiPushParameterDTO> parameterList = pushNotification.getPushParameters();

        final Integer pushModel = pushNotification.getPushModel();
        final List<PushMsgDTO> pushMsgs = pushNotification.getPushMsgs();

        // create pushMsg
        final List<ApiPushMessageDTO> messageList = new ArrayList<ApiPushMessageDTO>();
        
        for(PushMsgDTO pushMsg : pushMsgs){
        	final ApiPushMessageDTO apiPushMessage = new ApiPushMessageDTO();
        	final ApiMessageBodyDTO message = new ApiMessageBodyDTO();
        	message.setLang(pushMsg.getLangCode());
        	message.setMessage(pushMsg.getMessge());
        	message.setParameterList(parameterList);
        	apiPushMessage.setMessageBody(message);
        	messageList.add(apiPushMessage);
        }

        // create ApiPushTask
        final ApiPushTaskDTO apiPushTask = new ApiPushTaskDTO();
        apiPushTask.setMessageList(messageList);
        apiPushTask.setDeviceList(apiPushDevices);
        apiPushTask.setMultipleMessage(pushNotification.getPushMultipleMessage());
        if (!isAllAppUser) {
            apiPushTask.setFilterDeviceType(pushNotification.getPushFilterType());
        }
        apiPushTask.setProjectName(pushNotificationProjectId);
        apiPushTask.setPushModel(pushModel);
        if (PushModel.SCHEDULE.toValue() == pushModel.intValue()) {
            final Date startTime = pushNotification.getPushTime();
            // add 1 hours
            final Date endTime = DateUtil.addHours(startTime, 1);
            apiPushTask.setStartTime(startTime.getTime());
            apiPushTask.setEndTime(endTime.getTime());
        }
        apiPushTask.setPushType(isAllAppUser ? PushType.BATCH.toValue() : PushType.DEVICES.toValue());
        apiPushTask.setIosSupport(true);
        apiPushTask.setAosSupport(true);
        apiPushTask.setBaiduAosSupport(false);
        final String jsonString = JsonUtil.toJson(apiPushTask);
        logger.info("push message request: " + jsonString);

        final String jsonResult = HttpClientUtil.getInstance().sendJsonPost(pushCreatePushTaskUrl, jsonString);
        logger.info("push message result : " + jsonResult);

        if (StringUtils.isNotBlank(jsonResult)) {
            final PushResultDTO<Object> resultVo = JsonUtil.toObject(jsonResult, PushResultDTO.class);
            result.add(resultVo);
        }
        
        return result;
    }

	@Override
	@SuppressWarnings("unchecked")
    public void unbindByUserSessionIds(final Object userSessionId,String lang, final String pushNotificationProjectId) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("projectName", pushNotificationProjectId);
        params.put("userIds", userSessionId.toString());

        final String jsonResult = HttpClientUtil.getInstance().sendHttpPost(pushUnbindUserUrl, params);

        final PushResultDTO<Object> resultVo = JsonUtil.toObject(jsonResult, PushResultDTO.class);
        if (resultVo == null || resultVo.getErrorCode() != 0) {
            throw new PushUnbindUserException(I18nUtil.getMessage(CmsCodeStatus.PUSH_UNBIND_USER_ERROR, null, lang));
        }
    }
    
	@Override
	@SuppressWarnings("unchecked")
	public List<PushTaskDTO> getPushTask(final String fromDate, final String toDate,String lang, final String pushNotificationProjectId) throws Exception {
		Assert.hasText(fromDate, "From date "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		Assert.hasText(toDate, "To date "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		final Map<String, String> params = new HashMap<String, String>();
        params.put("projectName", pushNotificationProjectId);
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
		
		final String jsonResult = HttpClientUtil.getInstance().sendHttpPost(searchPushTaskUrl, params);
		if (StringUtils.isBlank(jsonResult)) {
			throw new PushSearchTaskException(I18nUtil.getMessage(CmsCodeStatus.PUSH_SEARCH_TASK_ERROR, null, lang));
		}
		
		PushResultDTO<Object> resultVo = JsonUtil.toObject(jsonResult, PushResultDTO.class);
		if (resultVo == null || resultVo.getErrorCode() != 0) {
            throw new PushSearchTaskException(I18nUtil.getMessage(CmsCodeStatus.PUSH_SEARCH_TASK_ERROR, null, lang) + (resultVo != null ? resultVo.getErrorMessage() : ""));
        }
		
		if (null != resultVo.getResult()) {
			final List<PushTaskDTO> pushTaskList = JsonUtil.toListObject(JsonUtil.toJson(resultVo.getResult()), PushTaskDTO.class);
			if (null != pushTaskList) {
				return pushTaskList;
			}
		}
		return null;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<PushTaskDetailDTO> getPushTaskDetail(final String pushTaskId,String lang, final String pushNotificationProjectId) throws Exception {
		Assert.hasText(pushTaskId, "Push task id"+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		List<PushTaskDetailDTO> pushTaskDetailList = new ArrayList<PushTaskDetailDTO>();
		
		final Map<String, String> params = new HashMap<String, String>();
        params.put("pushTaskId", pushTaskId);
		
		final String jsonResult = HttpClientUtil.getInstance().sendHttpPost(searchPushTaskDetailUrl, params);
		if (StringUtils.isBlank(jsonResult)) {
			throw new PushSearchTaskDetailException(I18nUtil.getMessage(CmsCodeStatus.PUSH_SEARCH_TASK_DETAIL_ERROR, null, lang));
		}
		
		PushResultDTO<Object> resultVo = JsonUtil.toObject(jsonResult, PushResultDTO.class);
		if (resultVo == null || resultVo.getErrorCode() != 0) {
            throw new PushSearchTaskDetailException(I18nUtil.getMessage(CmsCodeStatus.PUSH_SEARCH_TASK_DETAIL_ERROR, null, lang) + (resultVo != null ? resultVo.getErrorMessage() : ""));
        }
		
		if (null != resultVo.getResult()) {
			pushTaskDetailList = JsonUtil.toListObject(JsonUtil.toJson(resultVo.getResult()), PushTaskDetailDTO.class);
		}
		
		return pushTaskDetailList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void cancelPushTask(final String pushTaskId,final String lang, final String pushNotificationProjectId) {
		Assert.hasText(pushTaskId, "Push task id"+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		final Map<String, String> params = new HashMap<String, String>();
        params.put("pushTaskId", pushTaskId);
		
		final String jsonResult = HttpClientUtil.getInstance().sendHttpPost(cancelPushTaskUrl, params);
		if (StringUtils.isBlank(jsonResult)) {
			throw new PushCancelTaskException(I18nUtil.getMessage(CmsCodeStatus.PUSH_CANCEL_TASK_ERROR, null, lang));
		}
		
		PushResultDTO<Object> resultVo = JsonUtil.toObject(jsonResult, PushResultDTO.class);
		if (resultVo == null || resultVo.getErrorCode() != 0) {
            throw new PushCancelTaskException(I18nUtil.getMessage(CmsCodeStatus.PUSH_CANCEL_TASK_ERROR, null, lang) + (resultVo != null ? resultVo.getErrorMessage() : ""));
        }
		
		Integer result = (Integer) resultVo.getResult();
		if (null != result) {
			if (result.intValue() <= 0) {
//				logger.error("Cancel push task detail failed:" + resultVo.getResult() + " errorCode:" + resultVo.getErrorCode() + " errorMsg:" + resultVo.getErrorMessage());
				
				throw new PushCancelTaskException(I18nUtil.getMessage(CmsCodeStatus.PUSH_CANCEL_TASK_ERROR, null, lang) + (resultVo != null ? resultVo.getErrorMessage() : ""));
			} 
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String str = "{\"errorCode\":0,\"errorMessage\":\"\",\"result\":0}";
		
		 final PushResultDTO<Object> resultVo = JsonUtil.toObject(str, PushResultDTO.class);
		 System.out.println(resultVo.getErrorCode());
		 System.out.println(resultVo.getResult());
		
	}
}
