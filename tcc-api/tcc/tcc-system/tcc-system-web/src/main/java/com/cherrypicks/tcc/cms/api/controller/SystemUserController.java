package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.PrivateRequestVerifyAnno;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.SystemUserDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.service.SystemRoleService;
import com.cherrypicks.tcc.cms.system.service.SystemUserMerchantMapService;
import com.cherrypicks.tcc.cms.system.service.SystemUserService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.model.SystemUser;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class SystemUserController extends BaseController<SystemUser>{
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private SystemRoleService systemRoleService;
	
	@Autowired
	private SystemUserMerchantMapService systemUserMerchantMapService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<SystemUser> systemUserService) {
		// TODO Auto-generated method stub
		super.setBaseService(systemUserService);
	}
	
	@RequestMapping(value="/createSystemUser")
	public ResultVO addSystemUser(final Long userId,final Long merchantId,final String userName, final String password,final Long roleId, final String mobile, final String email, final Integer status,final String lang){
		
		AssertUtil.notBlank(userName, "User name " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(password, "Password " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(roleId, "Role " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(status, "Status " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(email, "Email " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		SystemUser systemUser = systemUserService.createSystemUser(userId,merchantId,userName,password,roleId,mobile,email,status,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(systemUser);
		return result;
	}
	
	@RequestMapping(value="/delSystemUser")
	public SuccessVO remove(final Long userId,final String ids,final String lang){
		
		AssertUtil.notBlank(ids, "Deleted user id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		systemUserService.delByIds(userId,ids,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/updateSystemUser",method=RequestMethod.POST)
	public ResultVO updateSystemUser(final Long userId,final Long updUserId,final String userName,final String password,final String rePassword,final Long roleId,final String mobile,final String email,final Integer status, final String lang){
		
		AssertUtil.notNull(updUserId, "Update user "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		SystemUser updSystemUser = systemUserService.updateSystemUser(userId,updUserId,userName,password,roleId,mobile,email,status,lang);
		
		final ResultVO result = new ResultVO();
		result.setResult(updSystemUser);
		return result;
	}
	
	@RequestMapping(value="/getSystemUserList",method=RequestMethod.POST)
	public PagingResultVo getSystemUserList(final Long userId, final String name,final String roleName,final String sortField,final String sortType,final Integer startRow,final Integer maxRows){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Long merchantId = null;
		SystemRole userRole = systemRoleService.findByUserId(userId);
		if(userRole.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			merchantId = systemUserMerchantMapService.findMerchantIdBySystemUserId(userId);
		}
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>(); 
		criteriaMap.put("name", name);
		criteriaMap.put("roleName", roleName);
		criteriaMap.put("merchantId", merchantId);
		
		return doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	
	@RequestMapping(value="/getSystemUserDetail",method=RequestMethod.POST)
	public ResultVO getSystemUserDetail(final Long systemUserId,final String lang){
		
		AssertUtil.notNull(systemUserId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		SystemUserDetailDTO user = systemUserService.findSystemUserDetail(systemUserId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(user);
		return result;
		
	}

	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getSystemUserById",method=RequestMethod.POST)
	public ResultVO getSystemUserById(final Long id) {
		
		AssertUtil.notNull(id, "Id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
	
		final SystemUser systemUser = systemUserService.findById(id);
		
		final ResultVO result = new ResultVO();
		result.setResult(systemUser);
		return result;
	}
	
}
