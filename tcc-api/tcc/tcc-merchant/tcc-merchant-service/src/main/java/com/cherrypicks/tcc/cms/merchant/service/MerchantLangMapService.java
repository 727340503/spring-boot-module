package com.cherrypicks.tcc.cms.merchant.service;

import java.util.List;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.MerchantLangMap;

public interface MerchantLangMapService extends IBaseService<MerchantLangMap>{

	List<MerchantLangMap> createMerchantLangMap(final Merchant merchant, final String langData, final String lang);

	List<MerchantLangMap> updateMerchantLangMap(final Merchant merchant,final String defaultLangCode, final String langData, final String lang);

	List<MerchantLangMap> findByMerchantId(final Long merchantId);

	MerchantLangMap findMerchantDefaultNameById(final Long merchantId);
	
	List<String> findMerchantLangCodes(final Long merchantId);

	String findMerchantDefaultLangCode(final Long merchantId);

//	List<MerchantLangMap> findDetailByMerchantId(final Long merchantId);

}
