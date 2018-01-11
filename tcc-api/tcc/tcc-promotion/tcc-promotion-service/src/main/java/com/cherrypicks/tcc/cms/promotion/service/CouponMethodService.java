package com.cherrypicks.tcc.cms.promotion.service;

import java.util.List;

import com.cherrypicks.tcc.cms.dto.CouponCollectMethodDTO;
import com.cherrypicks.tcc.cms.dto.CouponCollectQuotaDTO;
import com.cherrypicks.tcc.cms.dto.CouponRedeemMethodDTO;
import com.cherrypicks.tcc.cms.dto.CouponRedeemQuotaDTO;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.model.CouponCollectMethodMap;
import com.cherrypicks.tcc.model.CouponRedeemMethodMap;

public interface CouponMethodService {

	void createCouponRedeemQuota(final Coupon coupon, final String redeemQuotaData, final String lang);
	
	void createCouponCollectQuota(final Coupon coupon, final String collectQuotaData, final String lang);


	void createCouponRedeemMethod(final Coupon coupon, final List<CouponRedeemMethodMap> couponRedeemMethods, final String lang);


	void createCouponCollectMethod(final Coupon coupon, final List<CouponCollectMethodMap> couponCollectMethods, final String lang);

	List<CouponCollectMethodDTO> findCollectMethodsByCouponId(final Long couponId);

	List<CouponCollectQuotaDTO> findCollectQuotasByCouponId(final Long couponId);

	List<CouponRedeemMethodDTO> findRedeemMethodsByCouponId(final Long couponId);

	List<CouponRedeemQuotaDTO> findRedeemQuotasByCouponId(final Long couponId);

	void delMethodsAndQuotaByCouponIds(final List<Object> idList);

	void updateCouponMethodAndQuota(final Coupon updateCoupon, final String collectMethodData, final String collectQuotaData,
			final String redeemMethodData, final String redeemQuotaData,final String lang);

}
