package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.UserReservation;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class PushRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(PushRequestUtil.class);
	
	private final static String SEND_USER_RESERVATION_PUSH = CmsConstants.SEND_USER_RESERVATION_PUSH;

	public static void sendUserReservationPushMessage(List<UserReservation> needSendEmailUserReservation) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userReservations", Json.toJson(needSendEmailUserReservation));

		String json = HttpClientUtil.getInstance().sendHttpPost(SEND_USER_RESERVATION_PUSH, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				return;
			}
		}
		
		throw new CallOtherModuleApiErrorException();
	}
}
