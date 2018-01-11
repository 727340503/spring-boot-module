package com.cherrypicks.tcc.cms.merchant.service;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.MerchantFunctionFilterMap;

public interface MerchantFunctionFilterMapService extends IBaseService<MerchantFunctionFilterMap>{

	
	void addCouponManagementModule(final Long merchantId);
	
	void removeCouponManagementModule(final Long userId,final Long merchantId);

}
