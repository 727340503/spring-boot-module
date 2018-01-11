package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.PrivateRequestVerifyAnno;
import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.MerchantDetailDTO;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class MerchantController extends BaseController<Merchant>{
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private MerchantLangMapService merchantLangMapService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Merchant> merchantService) {
		super.setBaseService(merchantService);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/createMerchant",method=RequestMethod.POST)
	public SuccessVO addMerchant(final Long userId, final String securityKey,final String timeZone,final String loginMethod,final Integer issueStampMethod,
								final Integer status,final Boolean isCouponManagement,final Long currencyUnitId,final String dateFormat,final String hoursFormat,
								final String phoneDistrictCode,final Integer reservationType, final Integer mapType, final String langData, final String lang){
		
		AssertUtil.notBlank(securityKey, "Security key "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(timeZone, "Time zone"+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(loginMethod, "Longin method "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(issueStampMethod,"Issue stamp methos "+ I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(status, "Status "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isCouponManagement, "Coupon on/off "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(dateFormat, "Date format "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(hoursFormat, "Hours format "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(phoneDistrictCode, "Phone district code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(reservationType, "Reservation type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(mapType, "Map type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(reservationType.intValue() != Merchant.ReservationType.PAY_WHEN_MAKE_RESERVATION.toValue() && reservationType.intValue() != Merchant.ReservationType.PAY_WHEN_PICK_UP_AT_STORE.toValue()){
			throw new IllegalArgumentException("Reservation type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT,null, lang));
		}
		
		merchantService.createMerchant(userId, securityKey, timeZone, loginMethod, issueStampMethod, status, isCouponManagement,currencyUnitId,dateFormat,hoursFormat,phoneDistrictCode,reservationType, mapType, langData, lang);
		
		return new SuccessVO();
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/updateMerchant",method=RequestMethod.POST)
	public ResultVO updateMerchant(final Long userId,final Long merchantId,final String securityKey,final String loginMethod,final Integer issueStampMethod,
				final Integer status,final Boolean isCouponManagement,final String defaultLangCode,final String phoneDistrictCode,final String dateFormat,final String hoursFormat,
				final Integer mapType, final String langData, final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(defaultLangCode, "Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		merchantService.updateMerchant(userId, merchantId, securityKey, loginMethod, issueStampMethod, status, isCouponManagement,defaultLangCode,phoneDistrictCode,dateFormat,hoursFormat,mapType,langData, lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/getMerchantList",method=RequestMethod.POST)
	public PagingResultVo getMerchantList(final Long userId,final String merchantCode,final Integer status, final String name,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		
		//判断用户属于Platform admin
		SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			Long merchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
			criteriaMap.put("merchantId", merchantId);
		}
		
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("name", name);
		criteriaMap.put("status", status);
		criteriaMap.put("merchantCode", merchantCode);
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/getMerchantDetail",method=RequestMethod.POST)
	public ResultVO getMerchantDetail(final Long userId,final Long merchantId,final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		MerchantDetailDTO merchantDetail = merchantService.findDetailById(userId,merchantId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(merchantDetail);
		return result;
	}
	
	@RequestMapping(value="/generateMerchantSecurityKey",method=RequestMethod.POST)
	public ResultVO generateMerchantSecurityKey(){
		
		String securityKey = merchantService.generateMerchantSecurityKey();
		
		ResultVO result = new ResultVO();
		result.setResult(securityKey);
		return result;
	}
	
	@RequestMapping(value="/getMerchantSecurityKey",method=RequestMethod.POST)
	public ResultVO getMerchantSecurityKey(final Long userId,final String password,final Long merchantId,final String lang){
		
		AssertUtil.notBlank(password, "Password "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		String securityKey = merchantService.getMerchantSecurityKey(userId,password,merchantId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(securityKey);
		return result;
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/updateMerchantSecurityKey",method=RequestMethod.POST)
	public SuccessVO updateMerchantSecurityKey(final Long userId,final String securityKey,final Long merchantId,final String lang){
		
		AssertUtil.notBlank(securityKey, "Security key "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		merchantService.updateMerchantSecurityKey(userId,merchantId,securityKey,lang);
		
		return new SuccessVO();
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getMerchantLangCodes",method=RequestMethod.POST)
	public ResultVO getMerchantLangCodes(final Long merchantId) {
		
		AssertUtil.notNull(merchantId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(merchantLangMapService.findMerchantLangCodes(merchantId));
		return result;
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getMerchantById",method=RequestMethod.POST)
	public ResultVO getMerchantById(final Long merchantId) {
		
		AssertUtil.notNull(merchantId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(merchantService.findById(merchantId));
		return result;
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getMerchantDefaultLangCode",method=RequestMethod.POST)
	public ResultVO getMerchantDefaultLangCode(final Long merchantId) {
		
		AssertUtil.notNull(merchantId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(merchantLangMapService.findMerchantDefaultLangCode(merchantId));
		return result;
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/updateMerchantForVersion",method=RequestMethod.POST)
	public ResultVO updateMerchantForVersion(final Merchant merchant) {
		
		AssertUtil.notNull(merchant.getId(), "Merchant "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		merchantService.updateMerchantForVersion(merchant);
		
		ResultVO result = new ResultVO();
		return result;
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getMerchantDefaultName",method=RequestMethod.POST)
	public ResultVO getMerchantDefaultName(final Long merchantId) {
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		final String name = merchantService.findMerchantDefaultNameById(merchantId);
		
		ResultVO result = new ResultVO();
		result.setResult(name);
		return result;
	}
}
