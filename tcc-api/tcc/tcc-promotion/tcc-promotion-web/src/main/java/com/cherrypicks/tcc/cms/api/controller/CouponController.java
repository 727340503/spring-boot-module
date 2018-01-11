package com.cherrypicks.tcc.cms.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.PrivateRequestVerifyAnno;
import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.CouponDetailDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.promotion.service.CouponService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class CouponController extends BaseController<Coupon>{
	
	@Autowired
	private CouponService couponService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Coupon> couponService) {
		super.setBaseService(couponService);
	}
	
	@RequestMapping(value="/getCouponList",method=RequestMethod.POST)
	public PagingResultVo getCouponList(final Long userId, final Long merchantId, final Long campaignId, final String status,final String name, final Integer type, final String sortField,final String sortType,final Integer startRow,final Integer maxRows,final String langCode, final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("status", status);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("name", name);
		criteriaMap.put("campaignId", campaignId);
		criteriaMap.put("type", type);
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			criteriaMap.put("merchantId", SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId));
		}
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/getCouponDetail",method=RequestMethod.POST)
	public ResultVO getCouponDetail(final Long userId, final Long couponId, final String lang){
		
		AssertUtil.notNull(couponId, "Coupon id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		CouponDetailDTO couponDetail = couponService.findDetailById(userId,couponId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(couponDetail);
		return result;
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/getHomePageCouponList",method=RequestMethod.POST)
	public ResultVO getHomePageCouponList(final Long userId,final Long merchantId,final Integer status, final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		List<HomePageItemDTO> coupons = couponService.findHomePageCouponList(userId,merchantId,status,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(coupons);
		return result;
	}
	
	@RequestMapping(value="/createCoupon",method=RequestMethod.POST)
	public ResultVO createCoupon(final Long userId,final Long campaignId,final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
			final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
			final Boolean isUseWithSameCoupon, final Boolean isUseWithOtherCoupon,final Boolean isTransferrable, final String langData, 
			final String collectMethodData, final String collectQuotaData,final String redeemMethodData,final String redeemQuotaData, final String lang){
		
		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collectStartTime, "Collect start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collectEndTime, "Collect end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(redeemStartTime, "Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(type, "Type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(rewardQty, "Reward qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isAfterCollect, "Is after collect "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isUseWithOtherCoupon, "Use with other coupon types "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isTransferrable, "Transferrable "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(collectMethodData, "Collect method data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(redeemMethodData, "Redeem method data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(type.intValue() != Coupon.CouponType.STAMPS.toValue()) {
			throw new IllegalArgumentException("Type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(collectStartTime.getTime() > collectEndTime.getTime()){
			throw new IllegalArgumentException("End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(collectStartTime.getTime() > redeemStartTime.getTime()){
			throw new IllegalArgumentException("Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(null == redeemEndTime && !isAfterCollect){
			throw new IllegalArgumentException("Redeem end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(!isAfterCollect){
			if(redeemStartTime.getTime() > redeemEndTime.getTime()){
				throw new IllegalArgumentException("End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}else {
			if(null == endDaysAfterCollect || 0 >= endDaysAfterCollect) {
				throw new IllegalArgumentException("End days after collect "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}
		
		if(type.intValue() == Coupon.CouponType.STAMPS_MULTIPLY.toValue() || type.intValue() == Coupon.CouponType.POINT_MULTIPLY.toValue()) {
			AssertUtil.notNull(maxRewardQty, "Max reward qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		
		Coupon coupon = couponService.createCoupon(userId, campaignId, collectStartTime, collectEndTime, redeemStartTime, redeemEndTime, isAfterCollect, endDaysAfterCollect, 
										totalQty, type, rewardQty, maxRewardQty, issuedQuotaType,issuedQuotaQty,
										isUseWithSameCoupon, isUseWithOtherCoupon, isTransferrable, langData, 
										collectMethodData, collectQuotaData, redeemMethodData, redeemQuotaData, lang);
		
		ResultVO result = new ResultVO();
		result.setResult(coupon);
		return result;
	}
	
	@RequestMapping(value="/updateCoupon",method=RequestMethod.POST)
	public SuccessVO updateCoupon(final Long userId,final Long couponId,final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
			final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
			final Boolean isUseWithSameCoupon, final Boolean isUseWithOtherCoupon, final Boolean isTransferrable, final Integer status, final String langData, 
			final String collectMethodData, final String collectQuotaData,final String redeemMethodData,final String redeemQuotaData, final String lang){
		
		AssertUtil.notNull(couponId, "Coupon id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collectStartTime, "Collect start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collectEndTime, "Collect end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(redeemStartTime, "Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(type, "Type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(rewardQty, "Reward qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isAfterCollect, "Is after collect "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isUseWithOtherCoupon, "Use with other coupon types "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isTransferrable, "Transferrable "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(collectMethodData, "Collect method data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(redeemMethodData, "Redeem method data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(collectStartTime.getTime() > collectEndTime.getTime()){
			throw new IllegalArgumentException("End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(collectStartTime.getTime() > redeemStartTime.getTime()){
			throw new IllegalArgumentException("Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(null == redeemEndTime && !isAfterCollect){
			throw new IllegalArgumentException("Redeem end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(!isAfterCollect){
			if(redeemStartTime.getTime() > redeemEndTime.getTime()){
				throw new IllegalArgumentException("End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}else {
			if(null == endDaysAfterCollect || 0 >= endDaysAfterCollect) {
				throw new IllegalArgumentException("End days after collect "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}
		
		if(type.intValue() == Coupon.CouponType.STAMPS_MULTIPLY.toValue() || type.intValue() == Coupon.CouponType.POINT_MULTIPLY.toValue()) {
			AssertUtil.notNull(maxRewardQty, "Max reward qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		couponService.updateCoupon(userId, couponId, collectStartTime, collectEndTime, redeemStartTime, redeemEndTime, isAfterCollect, endDaysAfterCollect, 
									totalQty, type, rewardQty, maxRewardQty, issuedQuotaType, issuedQuotaQty, 
									isUseWithSameCoupon, isUseWithOtherCoupon, isTransferrable, status, langData, 
									collectMethodData, collectQuotaData, redeemMethodData, redeemQuotaData, lang);
		return new SuccessVO();
	}
	
	@RequestMapping(value="/updateCouponSortOrder",method=RequestMethod.POST)
	public SuccessVO updateCouponSortOrder(final Long userId,final String sortOrderData,final String lang){
		
		AssertUtil.notBlank(sortOrderData, "Sort order data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		Long userMerchantId = null;
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			userMerchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		couponService.updateCouponSortOrder(userId,sortOrderData,userMerchantId,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/deleteCoupon",method=RequestMethod.POST)
	public SuccessVO deleteCopon(final Long userId,final String ids,final String lang){
		AssertUtil.notBlank(ids, "Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		Long userMerchantId = null;
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			userMerchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		couponService.deleteCoupon(userId,ids,userMerchantId,lang);
		
		return new SuccessVO();
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getCouponById",method=RequestMethod.POST)
	public ResultVO deleteCopon(final Long couponId){
		
		AssertUtil.notNull(couponId, "Coupon id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(couponService.findById(couponId));
		return result;
	}
}
