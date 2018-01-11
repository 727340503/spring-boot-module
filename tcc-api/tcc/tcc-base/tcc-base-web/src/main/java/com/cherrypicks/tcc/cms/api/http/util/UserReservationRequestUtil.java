package com.cherrypicks.tcc.cms.api.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.dto.UserReservationPushNotifDTO;
import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.UserStampHistory;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class UserReservationRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(UserReservationRequestUtil.class);
	
	
	private final static String USER_RESERVATION_PUSH_NOTIF = CmsConstants.USER_RESERVATION_PUSH_NOTIF;
	private final static String CAMPAIGN_RESERVATION_COUNT = CmsConstants.CAMPAIGN_RESERVATION_COUNT;

	public static UserReservationPushNotifDTO finduserReservationPushNotifInfo(final Long userReservationId) {
		UserReservationPushNotifDTO result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userReservationId", String.valueOf(userReservationId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(USER_RESERVATION_PUSH_NOTIF, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), UserReservationPushNotifDTO.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static Long findCampaignReservationCount(final Long campaignId) {
		Long result = null;
		
		final List<Integer> userStampHistoryTypes = new ArrayList<Integer>();
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.SYSTEM_OUT_STAMPS.toValue());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(CAMPAIGN_RESERVATION_COUNT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"),Long.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
}
