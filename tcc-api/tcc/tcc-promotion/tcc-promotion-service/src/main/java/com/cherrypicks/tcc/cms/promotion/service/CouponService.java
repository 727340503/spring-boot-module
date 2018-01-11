package com.cherrypicks.tcc.cms.promotion.service;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.CouponDetailDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.Coupon;

public interface CouponService extends IBaseService<Coupon>{

	CouponDetailDTO findDetailById(final Long userId, final Long couponId, final String lang);

	List<HomePageItemDTO> findHomePageCouponList(final Long userId, final Long merchantId, final Integer status, final String lang);

	void updateCouponSortOrder(final Long userId, final String sortOrderData, final Long userMerchantId, final String lang);

	void deleteCoupon(Long userId, String ids, Long userMerchantId, String lang);

	Coupon createCoupon(final Long userId,final Long campaignId,final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
			final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
			final Boolean isUseWithSameCoupon, final Boolean isUseWithOtherCoupon, final Boolean isTransferrable, final String langData, 
			final String collectMethodData, final String collectQuotaData,final String redeemMethodData,final String redeemQuotaData, final String lang);

	void updateCoupon(final Long userId,final Long couponId,final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
			final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
			final Boolean isUseWithSameCoupon, final Boolean isUseWithOtherCoupon, final Boolean isTransferrable, final Integer status, final String langData, 
			final String collectMethodData, final String collectQuotaData,final String redeemMethodData,final String redeemQuotaData, final String lang);

}
