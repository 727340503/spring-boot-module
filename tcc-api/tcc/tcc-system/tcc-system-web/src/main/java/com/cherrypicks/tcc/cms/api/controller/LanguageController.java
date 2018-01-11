package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.service.LanguageService;
import com.cherrypicks.tcc.model.Language;

@RestController
public class LanguageController extends BaseController<Language>{
	
	@Autowired
	private LanguageService languageService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Language> languageService) {
		super.setBaseService(languageService);
	}
	
	@RequestMapping(value="/getLanguageAllList")
	public ResultVO getLanguageAllList(){
		
		List<Language> languageList = languageService.findAll();
		
		ResultVO result = new ResultVO();
		result.setResult(languageList);
		return result;
	}
	
}
