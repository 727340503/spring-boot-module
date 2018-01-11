package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.Store;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class StoreRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(StoreRequestUtil.class);
	
	
	private final static String STORE_BY_ID_URL = CmsConstants.STORE_BY_ID_URL;

	public static Store findById(final Long storeId) {
		Store result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeId", String.valueOf(storeId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(STORE_BY_ID_URL, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), Store.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}


	
}
