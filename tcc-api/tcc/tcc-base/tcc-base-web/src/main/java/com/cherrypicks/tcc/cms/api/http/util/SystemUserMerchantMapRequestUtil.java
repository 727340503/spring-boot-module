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

public class SystemUserMerchantMapRequestUtil{

	private static final Logger log = LoggerFactory.getLogger(SystemUserMerchantMapRequestUtil.class);
	
	
	private final static String MERCHANT_ID_BY_USER_ID_URL = CmsConstants.MERCHANT_ID_BY_USER_ID_URL;


	public static Long findMerchantIdBySystemUserId(Long userId) {
		
		Long  result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(MERCHANT_ID_BY_USER_ID_URL, params);
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
