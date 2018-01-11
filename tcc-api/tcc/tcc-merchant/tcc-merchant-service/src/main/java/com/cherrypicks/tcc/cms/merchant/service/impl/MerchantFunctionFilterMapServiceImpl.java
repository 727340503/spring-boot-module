package com.cherrypicks.tcc.cms.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantFunctionFilterMapDao;
import com.cherrypicks.tcc.cms.merchant.service.MerchantFunctionFilterMapService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.MerchantFunctionFilterMap;
import com.cherrypicks.tcc.model.SystemFunctionFilter;

@Service
public class MerchantFunctionFilterMapServiceImpl extends AbstractBaseService<MerchantFunctionFilterMap> implements MerchantFunctionFilterMapService {
	
//	@Autowired
//	private SystemFunctionFilterDao systemFunctionFilterDao;
	
	@Autowired
	private MerchantFunctionFilterMapDao merchantFunctionFilterMapDao;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<MerchantFunctionFilterMap> merchantFunctionFilterMapDao) {
		super.setBaseDao(merchantFunctionFilterMapDao);
	}

	@Override
	public void addCouponManagementModule(final Long merchantId) {
		
//		List<Long> systemFunctionIds = systemFunctionFilterDao.findByFilterCode(SystemFunctionFilter.SystemFunctionFilerCode.COUPON_MANAGEMENT_FILTER.toValue());
		
//		merchantFunctionFilterMapDao.delByMerchantIdAndSystemFuncs(merchantId,systemFunctionIds);
		
	}

	@Override
	public void removeCouponManagementModule(final Long userId,final Long merchantId) {
		
		final int couponManagementFilterCode = SystemFunctionFilter.SystemFunctionFilerCode.COUPON_MANAGEMENT_FILTER.toValue();
		
		int filterCount = merchantFunctionFilterMapDao.findByMerchantIdAndFilterCode(merchantId,couponManagementFilterCode);
		
		if(filterCount > 0){//数量不为0,则说明已经添加控制
			return;
		}
		
//		List<Long> systemFunctionIds = systemFunctionFilterDao.findByFilterCode(couponManagementFilterCode);
		List<MerchantFunctionFilterMap> merchantFuncFilterMaps = new ArrayList<MerchantFunctionFilterMap>();
		
//		for(final Long systemFuncId : systemFunctionIds){
//			MerchantFunctionFilterMap merchantFuncFilterMap = new MerchantFunctionFilterMap();
//			merchantFuncFilterMap.setFuncId(systemFuncId);
//			merchantFuncFilterMap.setMerchantId(merchantId);
//			merchantFuncFilterMap.setCreatedBy(String.valueOf(userId));
//			merchantFuncFilterMap.setCreatedTime(DateUtil.getCurrentDate());
//			merchantFuncFilterMap.setIsDeleted(false);
//			
//			merchantFuncFilterMaps.add(merchantFuncFilterMap);
//		}
		
		merchantFunctionFilterMapDao.batchAdd(merchantFuncFilterMaps);
	}
	
	
}
