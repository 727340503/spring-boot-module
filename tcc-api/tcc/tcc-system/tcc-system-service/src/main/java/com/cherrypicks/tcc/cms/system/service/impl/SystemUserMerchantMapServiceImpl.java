package com.cherrypicks.tcc.cms.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.dao.SystemUserMerchantMapDao;
import com.cherrypicks.tcc.cms.system.service.SystemUserMerchantMapService;
import com.cherrypicks.tcc.model.SystemUserMerchantMap;

@Service
public class SystemUserMerchantMapServiceImpl extends AbstractBaseService<SystemUserMerchantMap> implements SystemUserMerchantMapService {

	@Autowired
	private SystemUserMerchantMapDao systemUserMerchantMapDao;

	@Override
	public void setBaseDao(IBaseDao<SystemUserMerchantMap> sysetmUserMerchantMapDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(sysetmUserMerchantMapDao);
	}

	@Override
	public Long findMerchantIdBySystemUserId(final Long userId) {
		SystemUserMerchantMap userMerchantMap = systemUserMerchantMapDao.findByUserId(userId);
		return null == userMerchantMap ? null:userMerchantMap.getMerchantId();
	}
	
	
	
}
