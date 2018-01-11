package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.PrivateRequestVerifyAnno;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.service.CurrencyUnitService;
import com.cherrypicks.tcc.model.CurrencyUnit;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class CurrencyUnitController extends BaseController<CurrencyUnit>{
	
	@Autowired
	private CurrencyUnitService currencyUnitService;
	
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<CurrencyUnit> currencyUnitService) {
		// TODO Auto-generated method stub
		super.setBaseService(currencyUnitService);
	}
	
	@RequestMapping(value="/getCurrencyUnitlist",method=RequestMethod.POST)
	public ResultVO getCurrencyUnitlist(){
		
		List<CurrencyUnit> currerncyUnitList = currencyUnitService.findAll();
		
		ResultVO result = new ResultVO();
		result.setResult(currerncyUnitList);
		return result;
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getCurrencyUnitById",method=RequestMethod.POST)
	public ResultVO getCurrencyUnitById(final Long id){
		
		AssertUtil.notNull(id, "Id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		final CurrencyUnit currerncyUnit = currencyUnitService.findById(id);
		
		ResultVO result = new ResultVO();
		result.setResult(currerncyUnit);
		return result;
	}

}
