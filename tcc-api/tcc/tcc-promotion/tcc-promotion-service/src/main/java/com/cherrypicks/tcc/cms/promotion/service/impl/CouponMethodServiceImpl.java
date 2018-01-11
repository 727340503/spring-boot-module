package com.cherrypicks.tcc.cms.promotion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.dto.CouponCollectMethodDTO;
import com.cherrypicks.tcc.cms.dto.CouponCollectQuotaDTO;
import com.cherrypicks.tcc.cms.dto.CouponRedeemMethodDTO;
import com.cherrypicks.tcc.cms.dto.CouponRedeemQuotaDTO;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.promotion.dao.CouponCollectMethodMapDao;
import com.cherrypicks.tcc.cms.promotion.dao.CouponCollectQuotaMapDao;
import com.cherrypicks.tcc.cms.promotion.dao.CouponRedeemMethodMapDao;
import com.cherrypicks.tcc.cms.promotion.dao.CouponRedeemQuotaMapDao;
import com.cherrypicks.tcc.cms.promotion.service.CouponMethodService;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.model.CouponCollectMethodMap;
import com.cherrypicks.tcc.model.CouponCollectQuotaMap;
import com.cherrypicks.tcc.model.CouponRedeemMethodMap;
import com.cherrypicks.tcc.model.CouponRedeemQuotaMap;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.JsonUtil;



@Service
public class CouponMethodServiceImpl implements CouponMethodService {
	
	@Autowired
	private CouponCollectMethodMapDao couponCollectMethodMapDao;
	
	@Autowired
	private CouponCollectQuotaMapDao couponCollectQuotaMapDao;
	
	@Autowired
	private CouponRedeemMethodMapDao couponRedeemMethodMapDao;
	
	@Autowired
	private CouponRedeemQuotaMapDao couponRedeemQuotaMapDao;

	@Override
	public void createCouponRedeemQuota(final Coupon coupon, final String redeemQuotaData, final String lang) {
		final List<CouponRedeemQuotaMap> redeemQuotaMaps = JsonUtil.toListObject(redeemQuotaData, CouponRedeemQuotaMap.class);
		if(null == redeemQuotaMaps) {
			throw new JsonParseException();
		}
		
		for(CouponRedeemQuotaMap redeemQuota : redeemQuotaMaps) {
			if(null == redeemQuota.getRedeemQuotaType()) {
				throw new IllegalArgumentException("Redeem quota type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			int redeemQuotatype = redeemQuota.getRedeemQuotaType().intValue();
			if(redeemQuotatype == CouponRedeemQuotaMap.CouponRedeemQuota.ONCE.toValue() || redeemQuotatype == CouponRedeemQuotaMap.CouponRedeemQuota.DAY.toValue()
					|| redeemQuotatype == CouponRedeemQuotaMap.CouponRedeemQuota.MONTH.toValue() || redeemQuotatype == CouponRedeemQuotaMap.CouponRedeemQuota.YEAR.toValue()
						|| redeemQuotatype == CouponRedeemQuotaMap.CouponRedeemQuota.CAMPAIGN.toValue() || redeemQuotatype == CouponRedeemQuotaMap.CouponRedeemQuota.COUPON_PERIOD.toValue()) {
				redeemQuota.setRedeemQuotaType(redeemQuotatype);
			}else {
				throw new IllegalArgumentException("Redeem quota type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			redeemQuota.setId(null);
			redeemQuota.setCouponId(coupon.getId());
			redeemQuota.setCreatedBy(coupon.getCreatedBy());
			redeemQuota.setCreatedTime(coupon.getCreatedTime());
			redeemQuota.setIsDeleted(false);
			redeemQuota.setUpdatedBy(null);
			redeemQuota.setUpdatedTime(null);
			
			couponRedeemQuotaMapDao.add(redeemQuota);
		}
	}
	
	@Override
	public void createCouponCollectQuota(final Coupon coupon, final String collectQuotaData, final String lang) {
		final List<CouponCollectQuotaMap> collectQuotaMaps = JsonUtil.toListObject(collectQuotaData, CouponCollectQuotaMap.class);
		if(null == collectQuotaMaps) {
			throw new JsonParseException();
		}
		
		for(CouponCollectQuotaMap collectQuota : collectQuotaMaps) {
			if(null == collectQuota.getCollectQuotaType()) {
				throw new IllegalArgumentException("Collect quota type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			int collectQuotatype = collectQuota.getCollectQuotaType().intValue();
			if(collectQuotatype == CouponCollectQuotaMap.CouponCollectQuota.ONCE.toValue() || collectQuotatype == CouponCollectQuotaMap.CouponCollectQuota.DAY.toValue()
					|| collectQuotatype == CouponCollectQuotaMap.CouponCollectQuota.MONTH.toValue() || collectQuotatype == CouponCollectQuotaMap.CouponCollectQuota.YEAR.toValue()
						|| collectQuotatype == CouponCollectQuotaMap.CouponCollectQuota.CAMPAIGN.toValue() || collectQuotatype == CouponCollectQuotaMap.CouponCollectQuota.COUPON_PERIOD.toValue()) {
				collectQuota.setCollectQuotaType(collectQuotatype);
			}else {
				throw new IllegalArgumentException("Collect quota type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			collectQuota.setId(null);
			collectQuota.setCouponId(coupon.getId());
			collectQuota.setCreatedBy(coupon.getCreatedBy());
			collectQuota.setCreatedTime(coupon.getCreatedTime());
			collectQuota.setIsDeleted(false);
			collectQuota.setUpdatedBy(null);
			collectQuota.setUpdatedTime(null);
			
			couponCollectQuotaMapDao.add(collectQuota);
		}
	}

	@Override
	public void createCouponRedeemMethod(final Coupon coupon, final List<CouponRedeemMethodMap> couponRedeemMethods, final String lang) {
		for(CouponRedeemMethodMap redeemMethod : couponRedeemMethods) {
			
			if(null == redeemMethod.getRedeemMethod()) {
				throw new IllegalArgumentException("Redeem method type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			int redeemMethodType = redeemMethod.getRedeemMethod().intValue();
			if(redeemMethodType == CouponRedeemMethodMap.CouponRedeemMethod.NO_LIMIT.toValue() || redeemMethodType == CouponRedeemMethodMap.CouponRedeemMethod.BY_PURCHASE_AMOUNT.toValue()
					|| redeemMethodType == CouponRedeemMethodMap.CouponRedeemMethod.BY_PURCHASE_PRODUCT.toValue() || redeemMethodType == CouponRedeemMethodMap.CouponRedeemMethod.BY_PURCHASE_PRODUCT_AMOUNT.toValue()
					 	|| redeemMethodType == CouponRedeemMethodMap.CouponRedeemMethod.BY_PURCHASE_PRODUCT_BRAND.toValue() || redeemMethodType == CouponRedeemMethodMap.CouponRedeemMethod.BY_PURCHASE_PRODUCT_BRAND_AMOUNT.toValue()) {
				redeemMethod.setRedeemMethod(redeemMethodType);
			}else {
				throw new IllegalArgumentException("Redeem method type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			redeemMethod.setId(null);
			redeemMethod.setCouponId(coupon.getId());
			redeemMethod.setCreatedBy(coupon.getCreatedBy());
			redeemMethod.setCreatedTime(coupon.getCreatedTime());
			redeemMethod.setIsDeleted(false);
			redeemMethod.setUpdatedBy(null);
			redeemMethod.setUpdatedTime(null);
			
			couponRedeemMethodMapDao.add(redeemMethod);
		}
	}

	@Override
	public void createCouponCollectMethod(final Coupon coupon, final List<CouponCollectMethodMap> couponCollectMethods,
			final String lang) {
		for(CouponCollectMethodMap couponCollectMethod : couponCollectMethods) {
			
			if(null == couponCollectMethod.getCollectMethod()) {
				throw new IllegalArgumentException("Collect method type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			int collectMethodType = couponCollectMethod.getCollectMethod().intValue();
			if(collectMethodType == CouponCollectMethodMap.CouponCollectMethod.REGISTRATION.toValue() || collectMethodType == CouponCollectMethodMap.CouponCollectMethod.FREE_TO_COLLECT.toValue()
					|| collectMethodType == CouponCollectMethodMap.CouponCollectMethod.BEACON.toValue() || collectMethodType == CouponCollectMethodMap.CouponCollectMethod.COMPLETE_PROFILE.toValue()
							|| collectMethodType == CouponCollectMethodMap.CouponCollectMethod.PURCHASE_PRODUCT.toValue() || collectMethodType == CouponCollectMethodMap.CouponCollectMethod.PURCHASE_AMOUNT.toValue()
									|| collectMethodType == CouponCollectMethodMap.CouponCollectMethod.PURCHASE_PRODUCT_BRAND.toValue() || collectMethodType == CouponCollectMethodMap.CouponCollectMethod.PURCHASE_PRODUCT_AMOUNT.toValue()
											|| collectMethodType == CouponCollectMethodMap.CouponCollectMethod.PURCHASE_PRODUCT_BRAND_AMOUNT.toValue() || collectMethodType == CouponCollectMethodMap.CouponCollectMethod.REFERRAL.toValue()
													|| collectMethodType == CouponCollectMethodMap.CouponCollectMethod.BY_STAMP.toValue() || collectMethodType == CouponCollectMethodMap.CouponCollectMethod.BY_PIONTS.toValue()) {
				couponCollectMethod.setCollectMethod(collectMethodType);
			}else {
				throw new IllegalArgumentException("Collect method type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			couponCollectMethod.setId(null);
			couponCollectMethod.setCouponId(coupon.getId());
			couponCollectMethod.setCreatedBy(coupon.getCreatedBy());
			couponCollectMethod.setCreatedTime(coupon.getCreatedTime());
			couponCollectMethod.setIsDeleted(false);
			couponCollectMethod.setUpdatedBy(null);
			couponCollectMethod.setUpdatedTime(null);
			
			couponCollectMethodMapDao.add(couponCollectMethod);
		}
	}

	@Override
	public List<CouponCollectMethodDTO> findCollectMethodsByCouponId(Long couponId) {
		return couponCollectMethodMapDao.findByCouponId(couponId);
	}

	@Override
	public List<CouponCollectQuotaDTO> findCollectQuotasByCouponId(Long couponId) {
		return couponCollectQuotaMapDao.findByCouponId(couponId);
	}

	@Override
	public List<CouponRedeemMethodDTO> findRedeemMethodsByCouponId(Long couponId) {
		return couponRedeemMethodMapDao.findByCouponId(couponId);
	}

	@Override
	public List<CouponRedeemQuotaDTO> findRedeemQuotasByCouponId(Long couponId) {
		return couponRedeemQuotaMapDao.findByCouponId(couponId);
	}

	@Override
	public void delMethodsAndQuotaByCouponIds(final List<Object> couponIds) {
		couponCollectMethodMapDao.delByCouponIds(couponIds);
		couponCollectQuotaMapDao.delByCouponIds(couponIds);
		couponRedeemMethodMapDao.delByCouponIds(couponIds);
		couponRedeemQuotaMapDao.delByCouponIds(couponIds);
	}

	@Override
	public void updateCouponMethodAndQuota(final Coupon updateCoupon, final String collectMethodData, final String collectQuotaData,
			final String redeemMethodData, final String redeemQuotaData,final String lang) {
		
		final List<CouponCollectMethodMap> collectMethods = JsonUtil.toListObject(collectMethodData, CouponCollectMethodMap.class);
		if(null == collectMethods) {
			throw new JsonParseException();
		}
		
		final List<CouponRedeemMethodMap> redeemMethods = JsonUtil.toListObject(redeemMethodData, CouponRedeemMethodMap.class);
		if(null == redeemMethods) {
			throw new JsonParseException();
		}
		
		final List<Object> couponIds = new ArrayList<Object>();
		couponIds.add(updateCoupon.getId());
		
		this.delMethodsAndQuotaByCouponIds(couponIds);
		
		this.createCouponCollectMethod(updateCoupon, collectMethods, lang);
		
		this.createCouponRedeemMethod(updateCoupon, redeemMethods, lang);
		
		if(StringUtils.isNotEmpty(collectQuotaData)) {
			this.createCouponCollectQuota(updateCoupon, collectQuotaData, lang);
		}
		
		if(StringUtils.isNotEmpty(redeemQuotaData)) {
			this.createCouponRedeemQuota(updateCoupon, redeemQuotaData, lang);
		}
		
	}
}
