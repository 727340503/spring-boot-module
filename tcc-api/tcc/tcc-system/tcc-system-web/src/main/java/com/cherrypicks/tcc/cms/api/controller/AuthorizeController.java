package com.cherrypicks.tcc.cms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.enums.Lang;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.cms.vo.AuthenticatedUserDetails;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class AuthorizeController{

	@Autowired
	private IAuthorizeService authorizeService;
	
	/***
	 * login
	 * @param userName
	 * @param password
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResultVO login(final String userName,final String password,final String lang) throws Exception{
		
		AssertUtil.notBlank(lang, "Lang "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, Lang.getDefaultLang().toValue()));
		AssertUtil.notBlank(userName, "User name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(password, "Password "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		AuthenticatedUserDetails authUser = authorizeService.login(userName, password, lang);
		
		final ResultVO result = new ResultVO();
		result.setResult(authUser);
		return result;
	}
	
	/***
	 * login
	 * @param userId
	 * @param session
	 * @param lang
	 * @return
	 */
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public SuccessVO logout(final Long userId, final String session, final String lang){
		
		authorizeService.logout(userId, session,lang);

        return new SuccessVO();
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/checkUserMerchantPermission", method=RequestMethod.POST)
	public ResultVO checkUserMerchantPermission(final Long userId,final Long merchantId, final String lang) {

		AssertUtil.notNull(userId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(lang, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			
		boolean flag = authorizeService.checkUserMerchantPermission(userId, merchantId, lang);
		
		ResultVO result = new ResultVO();
		result.setResult(flag);
		
		return result;
	}
	
}