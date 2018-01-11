package com.cherrypicks.tcc.cms.push.service.push;

import java.util.List;

import com.cherrypicks.tcc.cms.push.service.push.dto.PushNotificationDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushResultDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushTaskDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushTaskDetailDTO;


public interface PushService {

	void registerDeviceToken(Object userSessionId, String deviceToken, Integer deviceType, String lang, final String pushNotificationProjectId);

	void unbindByUserSessionIds(Object userId,String lang, final String pushNotificationProjectId);

	List<PushResultDTO<Object>> pushMsgByUserIds(PushNotificationDTO pushNotification, List<Object> userIds, final String pushNotificationProjectId) throws Exception;

    List<PushResultDTO<Object>> pushMsgByUserId(PushNotificationDTO pushNotification, Object userId, final String pushNotificationProjectId) throws Exception;

    List<PushResultDTO<Object>> pushMsgAllUser(PushNotificationDTO pushNotification, final String pushNotificationProjectId) throws Exception;
    
    List<PushTaskDTO> getPushTask(final String fromDate, final String toDate,final String lang, final String pushNotificationProjectId) throws Exception;
    
    List<PushTaskDetailDTO> getPushTaskDetail(final String pushTaskId,final String lang, final String pushNotificationProjectId) throws Exception;

	void cancelPushTask(final String pushTaskId,final String lang, final String pushNotificationProjectId);

}
