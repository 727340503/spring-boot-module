package com.cherrypicks.tcc.cms.api.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.UserStampHistory;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class UserStampHistoryRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(UserStampHistoryRequestUtil.class);
	
	
	private final static String STAMP_HISTORY_COUNT_BY_TYPE = CmsConstants.STAMP_HISTORY_COUNT_BY_TYPE;

	public static Long findCampaignTotalIssuanceStamps(final Long campaignId) {
		Long result = null;
		
		final List<Integer> userStampHistoryTypes = new ArrayList<Integer>();
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.COLLECT_STAMPS.toValue());
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.SYSTEM_IN_STAMPS.toValue());
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.GRANT.toValue());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		params.put("types", Json.toJson(userStampHistoryTypes));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(STAMP_HISTORY_COUNT_BY_TYPE, params);
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

	public static Long findCampaignTotalUsedStamps(final Long campaignId) {
		Long result = null;
		
		final List<Integer> userStampHistoryTypes = new ArrayList<Integer>();
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.REDEEM.toValue());
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.RESERVATION.toValue());
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		params.put("types", Json.toJson(userStampHistoryTypes));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(STAMP_HISTORY_COUNT_BY_TYPE, params);
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

	public static Long findCampaignTotalRedemptionCount(final Long campaignId) {
		Long result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(STAMP_HISTORY_COUNT_BY_TYPE, params);
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

	public static Long findCampaignTotalRevokeStamps(Long campaignId) {
		Long result = null;
		
		final List<Integer> userStampHistoryTypes = new ArrayList<Integer>();
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.SYSTEM_OUT_STAMPS.toValue());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		params.put("types", Json.toJson(userStampHistoryTypes));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(STAMP_HISTORY_COUNT_BY_TYPE, params);
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
