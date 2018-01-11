package com.cherrypicks.tcc.cms.merchant.service;

import com.cherrypicks.tcc.cms.dto.MerchantDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.Merchant;

public interface MerchantService extends IBaseService<Merchant>{

	Merchant createMerchant(final Long userId,final String securityKey,final String timeZone,final String loginMethod,final Integer issueStampMethod,
			final Integer status,final Boolean isCouponManagement,final Long currencyUnitId,final String dateFormat,final String hoursFormat,
			final String phoneDistrictCode,final Integer reservationType, final Integer mapType, final String langData, final String lang);

	Merchant updateMerchant(final Long userId, final Long merchantId,final String securityKey,final String loginMethod,final Integer issueStampMethod,
			final Integer status,final Boolean isCouponManagement,final String defaultLangCode,final String phoneDistrictCode,final String dateFormat,final String hoursFormat,
			final Integer mapType, final String langData, final String lang);

	MerchantDetailDTO findDetailById(final Long userId,final Long merchantId, final String lang);

	String generateMerchantSecurityKey();

	String findMerchantDefaultNameById(Long merchantId);

	String getMerchantSecurityKey(final Long userId, final String password, final Long merchantId, final String lang);

	void updateMerchantSecurityKey(final Long userId, final Long merchantId, final String securityKey, final String lang);

	void updateMerchantForVersion(final Merchant merchant);

}
