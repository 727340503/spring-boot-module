package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponLangMapDTO;
import com.cherrypicks.tcc.model.CouponLangMap;

public interface CouponLangMapDao extends IBaseDao<CouponLangMap> {

	List<CouponLangMapDTO> findByCouponId(final Long couponId);

	boolean delByCouponIds(List<Object> idList);

}
