package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class AuthorizeRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(AuthorizeRequestUtil.class);
	
	
	private final static String CHECK_USER_MERCHANT_PERMISSION = CmsConstants.CHECK_USER_MERCHANT_PERMISSION;


	public static void checkUserMerchantPermission(Long userId, Long merchantId, String lang) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		params.put("merchantId", String.valueOf(merchantId));
		params.put("lang", lang);
		
		String json = HttpClientUtil.getInstance().sendHttpPost(CHECK_USER_MERCHANT_PERMISSION, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				
				boolean flag = (boolean) resultMap.get("result");
				if(!flag) {
					throw new ForbiddenException();
				}
				
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
	}
}
