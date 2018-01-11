package com.cherrypicks.tcc.cms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.service.SystemUserMerchantMapService;
import com.cherrypicks.tcc.model.SystemUserMerchantMap;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class SystemUserMerchantMapController extends BaseController<SystemUserMerchantMap>{
	
	@Autowired
	private SystemUserMerchantMapService systemUserMerchantMapService;
	
	@Override
	public void setBaseService(IBaseService<SystemUserMerchantMap> systemUserMerchantMapService) {
		super.setBaseService(systemUserMerchantMapService);
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getMerchantIdBySystemUserId",method=RequestMethod.POST)
	public ResultVO getMerchantIdBySystemUserId(final Long userId) {
		
		AssertUtil.notNull(userId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(systemUserMerchantMapService.findMerchantIdBySystemUserId(userId));
		return result;
	}
	
}
