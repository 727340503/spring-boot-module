package com.cherrypicks.tcc.cms.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.service.CurrencyUnitService;
import com.cherrypicks.tcc.model.CurrencyUnit;



@Service
public class CurrencyUnitServiceImpl extends AbstractBaseService<CurrencyUnit> implements CurrencyUnitService {
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<CurrencyUnit> currencyUnitDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(currencyUnitDao);
	}

}
