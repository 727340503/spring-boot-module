package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.service.SystemFuncService;
import com.cherrypicks.tcc.model.SystemFunction;

@RestController
public class SystemFuncController extends BaseController<SystemFunction>{
	
	@Autowired
	private SystemFuncService systemFuncService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<SystemFunction> systemFuncService) {
		// TODO Auto-generated method stub
		super.setBaseService(systemFuncService);
	}
	
	@RequestMapping(value="/getSystemFuncAllList",method=RequestMethod.POST)
	public ResultVO getSystemFuncList(){
		List<SystemFunction> funcs =  systemFuncService.findAll();
		
		ResultVO result = new ResultVO();
		result.setResult(funcs);
		
		return result;
	}
	
}
