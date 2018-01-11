package com.cherrypicks.tcc.cms.push.service.push.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.api.http.util.MerchantConfigRequestUtil;
import com.cherrypicks.tcc.cms.dto.PushNotifDTO;
import com.cherrypicks.tcc.cms.dto.PushNotifLangMapDTO;
import com.cherrypicks.tcc.cms.exception.PushMsgException;
import com.cherrypicks.tcc.cms.push.service.PushNotifService;
import com.cherrypicks.tcc.cms.push.service.push.PushExecutorService;
import com.cherrypicks.tcc.cms.push.service.push.PushService;
import com.cherrypicks.tcc.cms.push.service.push.dto.ApiPushParameterDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushMsgDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushNotificationDTO;
import com.cherrypicks.tcc.cms.push.service.push.dto.PushResultDTO;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.model.UserReservation;
import com.cherrypicks.tcc.util.Constants;
import com.cherrypicks.tcc.util.JsonUtil;

@Service
public class PushExecutorServiceImpl implements PushExecutorService {

	@Autowired
	private PushNotifService pushNotifService;

	@Autowired
	private PushService pushService;
	
	@Override
	public void sendUserReservationPushMessage(final List<UserReservation> userReservations) throws Exception {
		for (UserReservation userReservation : userReservations) {
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(userReservation.getMerchantId());

			PushNotifDTO pushNotifTempleate = pushNotifService.findMerchantReservationPushNotif(userReservation.getMerchantId(),userReservation,merchantConfig);
			PushNotificationDTO pushNotification = new PushNotificationDTO();

			List<PushMsgDTO> pushMsgs = new ArrayList<PushMsgDTO>();
			for (PushNotifLangMapDTO pushLangMap : pushNotifTempleate.getPushNotifLangMaps()) {

				PushMsgDTO pushMsg = new PushMsgDTO();
				pushMsg.setLangCode(pushLangMap.getLangCode());
				pushMsg.setMessge(pushLangMap.getMsg());

				pushMsgs.add(pushMsg);
			}
			pushNotification.setPushMsgs(pushMsgs);
			
			List<ApiPushParameterDTO> pushParams = getPushParams(pushNotifTempleate);
			pushNotification.setPushParameters(pushParams);

			
			pushService.pushMsgByUserId(pushNotification, userReservation.getUserId(),merchantConfig.getPushProjectId());
		}

	}

	@Override
	public Long sendPushNotif(final PushNotifDTO pushNotif,final  List<Long> userIds) throws Exception {
		PushNotificationDTO pushNotification = new PushNotificationDTO();
		
		final MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(pushNotif.getMerchantId());

		List<PushMsgDTO> pushMsgs = new ArrayList<PushMsgDTO>();
		for (PushNotifLangMapDTO pushLangMap : pushNotif.getPushNotifLangMaps()) {

			PushMsgDTO pushMsg = new PushMsgDTO();
			pushMsg.setLangCode(pushLangMap.getLangCode());
			pushMsg.setMessge(pushLangMap.getMsg());

			pushMsgs.add(pushMsg);
		}
		pushNotification.setPushMsgs(pushMsgs);
		
		List<Object> userSessionIds = new ArrayList<Object>();
		for(Long userId : userIds){
			userSessionIds.add(userId);
		}
		
		List<ApiPushParameterDTO> pushParams = getPushParams(pushNotif);
		pushNotification.setPushParameters(pushParams);
		
		List<PushResultDTO<Object>> results = pushService.pushMsgByUserIds(pushNotification, userSessionIds,merchantConfig.getPushProjectId());
		if(null != results && results.size() > 0 ){
			PushResultDTO<Object> result = results.get(0);
			if (null != result.getResult() && StringUtils.isNotBlank(result.getResult().toString())) {
				final Long pushTaskId = Long.parseLong(result.getResult().toString());
				if(pushTaskId > 0){
					return pushTaskId;
				}
			}
		}
		throw new PushMsgException();
	}
	
	@Override
	public Long sendPushNotifToAllUser(final PushNotifDTO pushNotif) throws Exception {
		PushNotificationDTO pushNotification = new PushNotificationDTO();
		
		final MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(pushNotif.getMerchantId());

		List<PushMsgDTO> pushMsgs = new ArrayList<PushMsgDTO>();
		for (PushNotifLangMapDTO pushLangMap : pushNotif.getPushNotifLangMaps()) {

			PushMsgDTO pushMsg = new PushMsgDTO();
			pushMsg.setLangCode(pushLangMap.getLangCode());
			pushMsg.setMessge(pushLangMap.getMsg());

			pushMsgs.add(pushMsg);
		}
		pushNotification.setPushMsgs(pushMsgs);
		
		
		List<ApiPushParameterDTO> pushParams = getPushParams(pushNotif);
		pushNotification.setPushParameters(pushParams);
		
		List<PushResultDTO<Object>> results = pushService.pushMsgAllUser(pushNotification, merchantConfig.getPushProjectId());
		if(null != results && results.size() > 0 ){
			PushResultDTO<Object> result = results.get(0);
			if (null != result.getResult() && StringUtils.isNotBlank(result.getResult().toString())) {
				final Long pushTaskId = Long.parseLong(result.getResult().toString());
				if(pushTaskId > 0){
					return pushTaskId;
				}
			}
		}
		throw new PushMsgException();
	}
	
	private List<ApiPushParameterDTO> getPushParams(PushNotifDTO pushNotif) {
		ApiPushParameterDTO pushParam = new ApiPushParameterDTO();

		Map<String, Integer> paramValueMap = new HashMap<String,Integer>();
		paramValueMap.put(Constants.PUSH_TYPE, pushNotif.getType());
		
		if(null != pushNotif.getPageRedirectType()){
			paramValueMap.put(Constants.PUSH_PAGE_REDIRECT_TYPE, pushNotif.getPageRedirectType());
		}
		

		pushParam.setKey(Constants.PUSH_DATA);
		pushParam.setValue(JsonUtil.toJson(paramValueMap));
		
		List<ApiPushParameterDTO> pushParmas = new ArrayList<ApiPushParameterDTO>();
		pushParmas.add(pushParam);
		
		return pushParmas;
	}


}
