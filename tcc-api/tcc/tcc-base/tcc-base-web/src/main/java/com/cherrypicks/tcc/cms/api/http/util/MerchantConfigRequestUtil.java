package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class MerchantConfigRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(MerchantConfigRequestUtil.class);
	
	private final static String FIND_BY_MERCHANT_ID = CmsConstants.FIND_MERCHANT_CONFIG_BY_MERCHANT_ID;

	public static MerchantConfig findByMerchantId(final Long merchantId) {
		MerchantConfig  result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(FIND_BY_MERCHANT_ID, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result =  Json.convert(resultMap.get("result"),MerchantConfig.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
	
	
	
	
	
}
