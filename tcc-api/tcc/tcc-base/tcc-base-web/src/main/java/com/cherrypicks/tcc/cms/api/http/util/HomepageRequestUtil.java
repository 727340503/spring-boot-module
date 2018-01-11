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

public class HomepageRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(HomepageRequestUtil.class);
	
	private final static String UPDATE_HOME_PAGE_CACHE_BY_REF = CmsConstants.UPDATE_HOME_PAGE_CACHE_BY_REF;

	public static boolean updateMerchantHomePageCacheByRefId(final Long userId, final Long merchantId, final Long id, final int type,final String lang) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		params.put("merchantId", String.valueOf(merchantId));
		params.put("id", String.valueOf(id));
		params.put("type", String.valueOf(type));
		params.put("lang", lang);
		
		String json = HttpClientUtil.getInstance().sendHttpPost(UPDATE_HOME_PAGE_CACHE_BY_REF, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				boolean flag = Json.convert(resultMap.get("result"),Boolean.class);
				if(flag) {
					return flag;
				}
			}
		}
		
		throw new CallOtherModuleApiErrorException();
	}

	public static long findCountByReftId(final Long campaignId, final int type) {
		long result = 0L;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		params.put("type", String.valueOf(type));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(UPDATE_HOME_PAGE_CACHE_BY_REF, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"),Long.class);
			}else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
}
