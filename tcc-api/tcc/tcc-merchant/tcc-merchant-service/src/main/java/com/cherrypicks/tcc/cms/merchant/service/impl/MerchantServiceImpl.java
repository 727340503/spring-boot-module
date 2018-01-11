package com.cherrypicks.tcc.cms.merchant.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.CurrencyUnitRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserRequestUtil;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.MerchantDetailDTO;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.InvalidUserNameOrPasswordException;
import com.cherrypicks.tcc.cms.exception.MerchantNotExistException;
import com.cherrypicks.tcc.cms.exception.MerchantSecurityKeyNotExistException;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantDao;
import com.cherrypicks.tcc.cms.merchant.dao.cache.MerchantCacheDao;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantFunctionFilterMapService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.CurrencyUnit;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.model.MerchantLangMap;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.model.SystemUser;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.EncryptionUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.RandomUtil;



@Service
public class MerchantServiceImpl extends AbstractBaseService<Merchant> implements MerchantService {
	
	@Value("${merchant.code.length:10}")
	private Integer merchantCodeLength;
	
	@Autowired
	private MerchantDao merchantDao;
	
	@Autowired
	private MerchantLangMapService merchantLangMapService;
	
	@Autowired
	private MerchantCacheDao merchantCacheDao;
	
	@Autowired
	private MerchantConfigService merchantConfigService;
	
	@Autowired
	private MerchantFunctionFilterMapService merchantFunctionFilterMapService;
	
	@Value("${user.expiry.mins:30}")
	private Integer userExpiryMins;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Merchant> merchantDao) {
		super.setBaseDao(merchantDao);
	}

	@Override
	@Transactional
	public Merchant createMerchant(final Long userId,final String securityKey, final String timeZone, final String loginMethod,final Integer issueStampMethod,
			final Integer status, final Boolean isCouponManagement,final Long currencyUnitId,final String dateFormat,final String hoursFormat,
			final String phoneDistrictCode,final Integer reservationType, final Integer mapType, final String langData, final String lang) {
		
		final String merchantSecurityKey = merchantCacheDao.getString(securityKey);
		if(StringUtils.isBlank(merchantSecurityKey)){
			throw new MerchantSecurityKeyNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_SECURITY_KEY_NOT_EXIST, null, lang));
		}

		final String merchantCode = generateMerchantCode();
		Merchant merchant = new Merchant();
		merchant.setCreatedBy(String.valueOf(userId));
		merchant.setCreatedTime(DateUtil.getCurrentDate());
		merchant.setIsDeleted(false);
		merchant.setIssueStampMethod(issueStampMethod.intValue() == Merchant.IssueStampMethod.POS_INTEGRATION.getCode()?Merchant.IssueStampMethod.POS_INTEGRATION.getCode():Merchant.IssueStampMethod.MERCHANT_APP.getCode());
		merchant.setSecurityKey(merchantSecurityKey);
		merchant.setTimeZone(timeZone);
		merchant.setMerchantCode(merchantCode);
		merchant.setLoginMethod(loginMethod);
		merchant.setIsCouponManagement(isCouponManagement);
		merchant.setCurrencyUnitId(currencyUnitId);
		merchant.setDateFormat(dateFormat);
		merchant.setHoursFormat(hoursFormat);
		merchant.setPhoneDistrictCode(phoneDistrictCode);
		merchant.setReservationType(reservationType);
		merchant.setMapType(Merchant.MapType.GOOGLE_MAP.getCode());
		
		if(mapType.intValue() == Merchant.MapType.BAIDU_MAP.getCode()){
			merchant.setMapType(mapType);
		}
		
		merchant.setStatus(status.intValue() == Merchant.Status.ACTIVE.getCode()?status.intValue():Merchant.Status.INACTIVE.getCode());
		merchant = merchantDao.add(merchant);
		
		merchantLangMapService.createMerchantLangMap(merchant, langData, lang);
		
		//remove coupon management module
		if(!isCouponManagement){
			merchantFunctionFilterMapService.removeCouponManagementModule(userId, merchant.getId());
		}
		
		return merchant;
	}
	
	private String generateMerchantCode() {
		String merchantCode = null;
		//Generate merchant code
		while(true){
			merchantCode = RandomUtil.generateString(merchantCodeLength).toUpperCase();
			Merchant checkMerchant = merchantDao.findByMerchantCode(merchantCode);
			if(null == checkMerchant){
				break;
			}
		}
		return merchantCode;
	}

	

	@Override
	@Transactional
	public Merchant updateMerchant(final Long userId, final Long merchantId,final String securityKey, final String loginMethod,final Integer issueStampMethod,
			final Integer status,final Boolean isCouponManagement,final String defaultLangCode,final String phoneDistrictCode,final String dateFormat,final String hoursFormat,
			final Integer mapType, final String langData,final String lang) {
		
		Merchant merchant = merchantDao.get(merchantId);
		if(null == merchant){
			throw new MerchantNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_NOT_EXIST, null, lang));
		}
		
		if(null != isCouponManagement){
			if(!isCouponManagement && merchant.getIsCouponManagement()){//if coupon is open then do not update to off
				throw new IllegalArgumentException("Coupon on/off "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			//add coupon management module
			if(isCouponManagement && !merchant.getIsCouponManagement()){
				merchantFunctionFilterMapService.addCouponManagementModule(merchantId);
			}
			
			merchant.setIsCouponManagement(isCouponManagement);
		}
		
		if(null != mapType){
			if(mapType.intValue() == Merchant.MapType.BAIDU_MAP.getCode() || mapType.intValue() == Merchant.MapType.GOOGLE_MAP.getCode()){
				merchant.setMapType(mapType);
			}
		}
		
		if(StringUtils.isNotBlank(loginMethod)){
			merchant.setLoginMethod(loginMethod);
		}
		
		if(null != issueStampMethod){
			merchant.setIssueStampMethod(issueStampMethod.intValue() == Merchant.IssueStampMethod.POS_INTEGRATION.getCode()?issueStampMethod.intValue():Merchant.IssueStampMethod.MERCHANT_APP.getCode());
		}
		
		if(null != status){
			merchant.setStatus(status.intValue() == Merchant.Status.ACTIVE.getCode()?status.intValue():Merchant.Status.INACTIVE.getCode());
		}
		
		if(null != dateFormat){
			merchant.setDateFormat(dateFormat);
		}
		
		if(null != hoursFormat){
			merchant.setHoursFormat(hoursFormat);
		}
		
		if(StringUtils.isNotBlank(phoneDistrictCode)){
			merchant.setPhoneDistrictCode(phoneDistrictCode);
		}
		merchant.setUpdatedBy(String.valueOf(userId));
		merchantDao.updateForVersion(merchant);
		
		if(StringUtils.isNotBlank(langData)){
			merchantLangMapService.updateMerchantLangMap(merchant,defaultLangCode,langData,lang);
		}

		return merchant;
	}

	@Override
	public MerchantDetailDTO findDetailById(final Long userId,final Long merchantId, final String lang) {
		Merchant merchant = merchantDao.get(merchantId);
		if(null == merchant){
			throw new MerchantNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_NOT_EXIST, null, lang));
		}
		
		MerchantDetailDTO merchantDetail = new MerchantDetailDTO();
		BeanUtils.copyProperties(merchant, merchantDetail);
		
		List<MerchantLangMap> merchantLangMapList = merchantLangMapService.findByMerchantId(merchantId);
		MerchantConfig merchantConfig = merchantConfigService.findByMerchantId(merchantId);
		
		for(MerchantLangMap merchantLangMap : merchantLangMapList){
			merchantLangMap.setImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(),merchantLangMap.getImg()));
			merchantLangMap.setLogo(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(),merchantLangMap.getLogo()));
		}
		
		merchantDetail.setMerchantLangMapList(merchantLangMapList);
		merchantDetail.setSecurityKey(encryMerchantSecurityKey(merchantDetail.getSecurityKey()));
		
		CurrencyUnit currencyUnit = CurrencyUnitRequestUtil.findById(merchant.getCurrencyUnitId());
		merchantDetail.setCurrencyCode(currencyUnit.getCurrencyCode());
		merchantDetail.setCurrencyFontCode(currencyUnit.getCurrencyFontCode());
		
		return merchantDetail;
	}

	@Override
	public String generateMerchantSecurityKey() {
		String securityKey = UUID.randomUUID().toString().replaceAll("-", "");
		String encrySecurityKey = encryMerchantSecurityKey(securityKey);
		
		merchantCacheDao.addString(encrySecurityKey, securityKey);
		if(userExpiryMins > 0 ){
			merchantCacheDao.expire(encrySecurityKey, userExpiryMins*60);
		}
		return encrySecurityKey;
	}
	
	private static String encryMerchantSecurityKey(String securityKey){
		String securityKeyPrefix = securityKey.substring(0, 4);
		String securityKeySuffix = securityKey.substring(securityKey.length()-4,securityKey.length());
		
		return new StringBuilder().append(securityKeyPrefix).append("******************").append(securityKeySuffix).toString();
	}

	@Override
	public String findMerchantDefaultNameById(Long merchantId) {
		return merchantLangMapService.findMerchantDefaultNameById(merchantId).getName();
	}

	@Override
	public String getMerchantSecurityKey(Long userId, String password, Long merchantId, String lang) {
		final SystemUser user = SystemUserRequestUtil.findById(userId);
		String userPwd = user.getPassword();
		
		if(!userPwd.equals(EncryptionUtil.getMD5(password))){
			log.debug("密码错误");
			throw new InvalidUserNameOrPasswordException();
		}
		
		SystemRole userRole = SystemRoleRequestUtil.findByUserId(userId);
		if(userRole.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			//check user merchant id
			final Long userMerchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
			if(userMerchantId.intValue() != merchantId.intValue()){
				throw new ForbiddenException();
			}
		}
		
		Merchant merchant = merchantDao.get(merchantId);
		return merchant.getSecurityKey();
	}

	@Override
	@Transactional
	public void updateMerchantSecurityKey(final Long userId, final Long merchantId, final String securityKey, final String lang) {
		Merchant merchant = merchantDao.get(merchantId);
		if(null == merchant){
			throw new MerchantNotExistException();
		}
		
		final String merchantSecurityKey = merchantCacheDao.getString(securityKey);
		if(StringUtils.isBlank(merchantSecurityKey)){
			throw new MerchantSecurityKeyNotExistException();
		}
		merchant.setSecurityKey(merchantSecurityKey);
		
		merchant.setUpdatedBy(String.valueOf(userId));
		
		merchantDao.updateForVersion(merchant);
	}

	@Override
	public void updateMerchantForVersion(Merchant merchant) {
		merchantDao.updateForVersion(merchant);
	}
}
