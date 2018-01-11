package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class CouponRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(CouponRequestUtil.class);
	
	
	private final static String COUPON_BY_ID = CmsConstants.COUPON_BY_ID;

	public static Coupon findById(final Long couponId) {
		Coupon result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("couponId", String.valueOf(couponId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(COUPON_BY_ID, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), Coupon.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
}
