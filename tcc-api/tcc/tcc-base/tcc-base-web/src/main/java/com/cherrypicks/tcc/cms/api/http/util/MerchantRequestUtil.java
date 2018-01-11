package com.cherrypicks.tcc.cms.api.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class MerchantRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(MerchantRequestUtil.class);
	
	
	private final static String MERCHANT_BY_ID_URL = CmsConstants.MERCHANT_BY_ID_URL;
	private final static String MERCHANT_LANG_CODES_URL = CmsConstants.MERCHANT_LANG_CODES_URL;
	private final static String MERCHANT_DEFAULT_LANG_CODE_URL = CmsConstants.MERCHANT_DEFAULT_LANG_CODE_URL;
	private final static String MERCHANT_UPDATE_FOR_VERSION = CmsConstants.MERCHANT_UPDATE_FOR_VERSION;
	private final static String MERCHANT_DEFAULT_NAME_URL = CmsConstants.MERCHANT_DEFAULT_NAME_URL;

	public static Merchant findById(final Long merchantId) {
		Merchant result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(MERCHANT_BY_ID_URL, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"),Merchant.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> findMerchantLangCodes(final Long merchantId) {
		List<String>  result = new ArrayList<String>();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(MERCHANT_LANG_CODES_URL, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = (List<String>) resultMap.get("result");
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static String findMerchantDefaultLang(Long merchantId) {
		String  result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(MERCHANT_DEFAULT_LANG_CODE_URL, params);
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

	public static void updateMerchantForVersion(final Merchant merchant) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchant", String.valueOf(merchant));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(MERCHANT_UPDATE_FOR_VERSION, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				return;
			}
		}
		throw new CallOtherModuleApiErrorException();
	}

	public static String findMerchantDefaultName(final Long merchantId) {
		String  result = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(MERCHANT_DEFAULT_NAME_URL, params);
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
