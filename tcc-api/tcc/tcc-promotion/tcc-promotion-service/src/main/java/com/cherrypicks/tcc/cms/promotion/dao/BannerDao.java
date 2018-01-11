package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.model.Banner;

public interface BannerDao extends IBaseDao<Banner> {

	Integer findSortOrderByMerchantId(final Long merchantId);

	List<HomePageItemDTO> findHomePageBannerList(final Long merchantId, final Integer status);


}
