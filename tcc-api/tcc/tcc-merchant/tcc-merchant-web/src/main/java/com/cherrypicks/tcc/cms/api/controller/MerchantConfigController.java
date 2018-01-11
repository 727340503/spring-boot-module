package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.EncryptionUtil;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class MerchantConfigController extends BaseController<MerchantConfig>{
	
	@Autowired
	private MerchantConfigService merchantConfigService;
	
	@Value("${developer.username}")
    private String developUserName;
	
	@Value("${developer.password}")
    private String developerPassword;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<MerchantConfig> merchantConfigService) {
		super.setBaseService(merchantConfigService);
	}
	
	@RequestMapping(value="/getMerchantConfigList",method=RequestMethod.POST)
	public PagingResultVo getMerchantConfigList(final String userName, final String password, final Long merchantId, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		checkDeveloper(userName,password);
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("merchantId", merchantId);

		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/addMerchantConfig",method=RequestMethod.POST)
	public ResultVO addMerchantConfig(final String userName,final String password,final MerchantConfig merchantConfig, final String lang){
	
		checkDeveloper(userName, password);

		AssertUtil.notNull(merchantConfig.getMerchantId(), "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getImgDomain(), "Image domain "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getPushProjectId(), "Push project id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getEmailHost(), "Email host "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getPushProjectId(), "Push project id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailPort(), "Email port "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailSmtpAuth(), "Email smtp auth "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailSmtpStsEnable(), "Email smtp sts enable "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailSmtpStsRequired(), "Email smtp sts required "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getEmailFrom(), "Email from "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getSupportEmailFrom(), "Support email from "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getWebDomain(), "Web domain "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getApiDomain(), "Api domain "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getReservationPushTemplate(), "Reservation push template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getRegisterVerifyEmailTemplate(), "Register verify email template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getResetPasswordEmailTemplate(), "Reset password email template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getIquiryEmailTemplate(), "Iquiry email template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareTemplate(), "Share template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareDefalutTitle(), "Share default title "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareDefalutDescr(), "Share default descr "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareDefalutImage(), "Share default image "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getFbAppId(), "Facebook app id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getFbAppToken(), "Facebook app token "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getFbDebugTokenApi(), "Facebook token api "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		
		ResultVO result = new ResultVO();
		result.setResult(merchantConfigService.createMerchantConfig(merchantConfig,lang));
		return result;
	}

	@RequestMapping(value="/updateMerchantConfig",method=RequestMethod.POST)
	public SuccessVO updateMerchantConfig(final String userName,final String password,final MerchantConfig merchantConfig, final Long id){
	
		checkDeveloper(userName, password);
		
		AssertUtil.notNull(id, "Id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getImgDomain(), "Image domain "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getPushProjectId(), "Push project id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getEmailHost(), "Email host "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getPushProjectId(), "Push project id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailPort(), "Email port "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailSmtpAuth(), "Email smtp auth "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailSmtpStsEnable(), "Email smtp sts enable "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantConfig.getEmailSmtpStsRequired(), "Email smtp sts required "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getEmailFrom(), "Email from "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getSupportEmailFrom(), "Support email from "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getWebDomain(), "Web domain "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getApiDomain(), "Api domain "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getReservationPushTemplate(), "Reservation push template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getRegisterVerifyEmailTemplate(), "Register verify email template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getResetPasswordEmailTemplate(), "Reset password email template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getIquiryEmailTemplate(), "Iquiry email template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareTemplate(), "Share template "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareDefalutTitle(), "Share default title "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareDefalutDescr(), "Share default descr "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getShareDefalutImage(), "Share default image "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getFbAppId(), "Facebook app id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getFbAppToken(), "Facebook app token "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(merchantConfig.getFbDebugTokenApi(), "Facebook token api "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				
		merchantConfigService.updateMerchantConfig(merchantConfig);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/getMerchantConfigInfo",method=RequestMethod.POST)
	public ResultVO getMerchantConfigInfo(final String userName,final String password,final Long id,final String lang){
	
		checkDeveloper(userName, password);
		
		AssertUtil.notNull(id, "Id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(merchantConfigService.findById(id));
		return result;
	}
	
	@RequestMapping(value="/updateMerchantConfigCache",method=RequestMethod.POST)
	public SuccessVO updateMerchantConfigCache(final String userName,final String password,final Long merchantId, final String lang){
	
		checkDeveloper(userName, password);
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		merchantConfigService.updateMerchantConfigCache(merchantId);
		
		return new SuccessVO();
	}
	
	private void checkDeveloper(final String userName, final String password) {
		
		AssertUtil.notBlank(userName, "User name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(password, "Password "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(!developUserName.equals(userName)){
			throw new IllegalArgumentException("User name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(!developerPassword.equals(EncryptionUtil.getMD5(password))){
			throw new IllegalArgumentException("Password "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
	}
	
}
