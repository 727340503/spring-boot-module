package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponCollectQuotaDTO;
import com.cherrypicks.tcc.model.CouponCollectQuotaMap;

public interface CouponCollectQuotaMapDao extends IBaseDao<CouponCollectQuotaMap> {

	List<CouponCollectQuotaDTO> findByCouponId(final Long couponId);

	void delByCouponIds(List<Object> couponIds);


}
