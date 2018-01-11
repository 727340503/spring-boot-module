package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.model.Coupon;

public interface CouponDao extends IBaseDao<Coupon> {

	List<HomePageItemDTO> findHomePageCouponList(final Long merchantId, final Integer status);

	Integer findSortOrderByMerchantId(final Long merchantId);

}
