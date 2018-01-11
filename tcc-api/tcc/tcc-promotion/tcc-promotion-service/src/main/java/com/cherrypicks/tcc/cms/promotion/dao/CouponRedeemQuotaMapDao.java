package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponRedeemQuotaDTO;
import com.cherrypicks.tcc.model.CouponRedeemQuotaMap;

public interface CouponRedeemQuotaMapDao extends IBaseDao<CouponRedeemQuotaMap> {

	List<CouponRedeemQuotaDTO> findByCouponId(final Long couponId);

	void delByCouponIds(List<Object> couponIds);

}
