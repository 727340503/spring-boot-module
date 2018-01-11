package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponRedeemMethodDTO;
import com.cherrypicks.tcc.model.CouponRedeemMethodMap;

public interface CouponRedeemMethodMapDao extends IBaseDao<CouponRedeemMethodMap> {

	List<CouponRedeemMethodDTO> findByCouponId(final Long couponId);

	void delByCouponIds(List<Object> couponIds);

}
