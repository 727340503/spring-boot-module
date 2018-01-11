package com.cherrypicks.tcc.cms.promotion.service;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BannerIDetailDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.Banner;

public interface BannerService extends IBaseService<Banner>{

	BannerIDetailDTO findDetailById(final Long userId,  final Long bannerId,  final String lang);

	Banner createBanner( final Long userId,  final Long merchantId,  String webUrl,final Integer inappOpen,final Date startTime,final Date endTime, final String langData,  final String lang);

	void updateBanner( final Long userId,  final Long bannerId,  final Integer status,  final String webUrl, final Integer inappOpen, final Date startTime,final Date endTime,final String langData,
			 final String lang);

	void deleteBanner(final Long userId, final String ids, final Long userMerchantId,final String lang);

	void updateBannerSortOrder(final Long userId, final String sortOrderData, final Long userMerchantId,final String lang);

	List<HomePageItemDTO> findHomePageBannerList(final Long userId, final Long merchantId, final Integer status, final String lang);


}
