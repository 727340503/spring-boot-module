package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.dto.CampaignDetailDTO;
import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class CampaignRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(CampaignRequestUtil.class);
	
	
	private final static String CAMPAIGN_BY_ID = CmsConstants.CAMPAIGN_BY_ID;
	private final static String CAMPAIGN_DETAIL = CmsConstants.CAMPAIGN_DETAIL;
	private final static String CAMPAIGN_DEFAULT_NAME = CmsConstants.CAMPAIGN_DEFAULT_NAME;

	public static Campaign findById(final Long campaignId) {
		Campaign result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(campaignId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(CAMPAIGN_BY_ID, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), Campaign.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static CampaignDetailDTO findDetailById(final Long userId, final Long campaignId, final String lang) {
		CampaignDetailDTO result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		params.put("userId", String.valueOf(userId));
		params.put("lang", lang);
		
		String json = HttpClientUtil.getInstance().sendHttpPost(CAMPAIGN_DETAIL, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), CampaignDetailDTO.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static String findCampaignDefaultName(final Long campaignId) {
		String  result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(CAMPAIGN_DEFAULT_NAME, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = (String) resultMap.get("result");
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
}
