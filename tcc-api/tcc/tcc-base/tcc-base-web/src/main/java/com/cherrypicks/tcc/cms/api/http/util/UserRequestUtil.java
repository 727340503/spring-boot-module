package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.User;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class UserRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(UserRequestUtil.class);
	
	
	private final static String USER_BY_CUSTOMER_ID = CmsConstants.USER_BY_CUSTOMER_ID;
	private final static String CAMPAIGN_TOTAL_USER_COUNT_URL = CmsConstants.CAMPAIGN_TOTAL_USER_COUNT_URL;

	public static User findById(final Long customerId) {
		User result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("customerId", String.valueOf(customerId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(USER_BY_CUSTOMER_ID, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), User.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static Long findCampaignTotalUserCount(final Long campaignId) {
		Long result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(CAMPAIGN_TOTAL_USER_COUNT_URL, params);
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
