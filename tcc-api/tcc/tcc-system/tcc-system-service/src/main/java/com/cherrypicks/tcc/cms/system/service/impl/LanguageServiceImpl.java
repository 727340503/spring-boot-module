package com.cherrypicks.tcc.cms.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.service.LanguageService;
import com.cherrypicks.tcc.model.Language;

@Service
public class LanguageServiceImpl extends AbstractBaseService<Language> implements LanguageService {

	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Language> languageDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(languageDao);
	}
	
}
