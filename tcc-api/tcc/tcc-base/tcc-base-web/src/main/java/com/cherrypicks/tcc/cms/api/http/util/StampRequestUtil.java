package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.Stamp;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class StampRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(StampRequestUtil.class);
	
	
	private final static String STAMP_LIST_BY_STAMP_CARD_ID = CmsConstants.STAMP_LIST_BY_STAMP_CARD_ID;

	public static List<Stamp> findByStampCardId(final Long stampCardId) {
		List<Stamp> result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("stampCardId", String.valueOf(stampCardId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(STAMP_LIST_BY_STAMP_CARD_ID, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.toListObject(resultMap.get("result").toString(), Stamp.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
}
