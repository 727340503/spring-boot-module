package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.SystemUser;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class SystemUserRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(SystemUserRequestUtil.class);
	
	
	private final static String SYSTEM_USER_BY_ID_URL = CmsConstants.SYSTEM_USER_BY_ID_URL;


	public static SystemUser findById(final Long userId) {
		SystemUser result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(userId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(SYSTEM_USER_BY_ID_URL, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result =  Json.convert(resultMap.get("result"),SystemUser.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}


	
	
}
