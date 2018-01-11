package com.cherrypicks.tcc.cms.promotion.service;

import java.util.List;

import com.cherrypicks.tcc.cms.dto.HomePageDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.HomePage;

public interface HomePageService extends IBaseService<HomePage>{

	void createHomePage(Long userId, Long merchantId, String homePageFrame, String homePageData, String lang);

	List<HomePageDetailDTO> findMerchantHomePageByStatus(Long userId, Long merchantId, String langCode,Integer status, String lang);
	
	void updateMerchantHomePageCache(final Long userId,Long merchantId,final String lang);
	
	void updateMerchantHomePageCacheByRefId(final Long userId,final Long merchantId,final Long refId, final Integer type,final String lang);

	long findCountByReftId(Long refId, Integer type);

	void publishMerchantHomePage(Long userId, Long merchantId, String lang);

}
