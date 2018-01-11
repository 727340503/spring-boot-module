package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class UserCouponRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(UserCouponRequestUtil.class);
	
	
	private final static String TOTAL_USER_COUPON_COUNT_BY_CAMPAIGN_ID = CmsConstants.TOTAL_USER_COUPON_COUNT_BY_CAMPAIGN_ID;

	public static Long findTotalCountByCampaignId(Long campaignId, Integer status) {
		Long result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		if(null != status) {
			params.put("status", String.valueOf(status));
		}
		
		String json = HttpClientUtil.getInstance().sendHttpPost(TOTAL_USER_COUPON_COUNT_BY_CAMPAIGN_ID, params);
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
