package com.cherrypicks.tcc.cms.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.CampaignRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantConfigRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponDetailDTO;
import com.cherrypicks.tcc.cms.dto.CouponItemDTO;
import com.cherrypicks.tcc.cms.dto.CouponLangMapDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.exception.CampaignNotExistException;
import com.cherrypicks.tcc.cms.exception.CoupomNotExistException;
import com.cherrypicks.tcc.cms.exception.CouponQuotaQtyLittleException;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.exception.RecordIsReferencedException;
import com.cherrypicks.tcc.cms.exception.UpdateRecordStatusException;
import com.cherrypicks.tcc.cms.promotion.dao.CouponDao;
import com.cherrypicks.tcc.cms.promotion.dao.CouponLangMapDao;
import com.cherrypicks.tcc.cms.promotion.service.CouponMethodService;
import com.cherrypicks.tcc.cms.promotion.service.CouponService;
import com.cherrypicks.tcc.cms.promotion.service.HomePageService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.Banner;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.model.CouponCollectMethodMap;
import com.cherrypicks.tcc.model.CouponLangMap;
import com.cherrypicks.tcc.model.CouponRedeemMethodMap;
import com.cherrypicks.tcc.model.HomePage;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.JsonUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Service
public class CouponServiceImpl extends AbstractBaseService<Coupon> implements CouponService {
	
	@Autowired
	private CouponDao couponDao;
	
	@Autowired
	private CouponLangMapDao couponLangMapDao;
	
	@Autowired
	private CouponMethodService couponMethodService;
	
	@Autowired
	private HomePageService homePageService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Coupon> couponDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(couponDao);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<CouponItemDTO> findByFilter(final Map<String, Object> criteriaMap, final String sortField, final String sortType,final int... args) {
		List<CouponItemDTO> coupons = (List<CouponItemDTO>) super.findByFilter(criteriaMap, sortField, sortType, args);
		
		if(null != coupons && coupons.size() > 0){
			for(CouponItemDTO coupon : coupons){
				if(coupon.getStatus().intValue() == Coupon.CouponStatus.ACTIVE.toValue()){
					long nowTime = DateUtil.getCurrentDate().getTime();
					if(coupon.getCollectStartTime().getTime() > nowTime){
						coupon.setStatus(Banner.BannerStatus.PENDING.toValue());
					}else if(coupon.getCollectEndTime().getTime() < nowTime){
						coupon.setStatus(Banner.BannerStatus.EXPIRED.toValue());
					}
				}
			}
		}
		
		return coupons;
	}


	@Override
	public CouponDetailDTO findDetailById(final Long userId, final Long couponId, final String lang) {
		Coupon coupon = couponDao.get(couponId);
		
		if(null != coupon){
			
			Campaign campaign = CampaignRequestUtil.findById(coupon.getCampaignId());
			
			AuthorizeRequestUtil.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);
			
			Merchant merchant = MerchantRequestUtil.findById(campaign.getMerchantId());
			
			CouponDetailDTO couponDetail = new CouponDetailDTO();
			BeanUtils.copyProperties(coupon, couponDetail);
			
			couponDetail.setCollectStartTime(TimeZoneConvert.dateTimezoneToUI(coupon.getCollectStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			couponDetail.setCollectEndTime(TimeZoneConvert.dateTimezoneToUI(coupon.getCollectEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			couponDetail.setRedeemStartTime(TimeZoneConvert.dateTimezoneToUI(coupon.getRedeemStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			if(null != coupon.getRedeemEndTime()) {
				couponDetail.setRedeemEndTime(TimeZoneConvert.dateTimezoneToUI(coupon.getRedeemEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			}
			
			if(coupon.getStatus().intValue() == Coupon.CouponStatus.ACTIVE.toValue()){
				long nowTime = DateUtil.getCurrentDate().getTime();
				if(coupon.getCollectStartTime().getTime() > nowTime){
					couponDetail.setStatus(Banner.BannerStatus.PENDING.toValue());
				}else if(coupon.getCollectEndTime().getTime() < nowTime){
					couponDetail.setStatus(Banner.BannerStatus.EXPIRED.toValue());
				}
			}
			
			List<CouponLangMapDTO> couponLangMaps = couponLangMapDao.findByCouponId(coupon.getId());
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(campaign.getMerchantId());

			for(CouponLangMapDTO couponLangMap : couponLangMaps){
				couponLangMap.setFullImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), couponLangMap.getImg()));
			}
			
			couponDetail.setCouponLangMaps(couponLangMaps);
			couponDetail.setCollectMethodData(couponMethodService.findCollectMethodsByCouponId(couponId));
			couponDetail.setCollectQuotaData(couponMethodService.findCollectQuotasByCouponId(couponId));
			couponDetail.setRedeemMethodData(couponMethodService.findRedeemMethodsByCouponId(couponId));
			couponDetail.setRedeemQuotaData(couponMethodService.findRedeemQuotasByCouponId(couponId));
			
			return couponDetail;
		}
		
		return null;
	}


	@Override
	public List<HomePageItemDTO> findHomePageCouponList(final Long userId, final Long merchantId, final Integer status, final String lang) {
		
		List<HomePageItemDTO> homePageCoupons = couponDao.findHomePageCouponList(merchantId,status);
		
		if(null != homePageCoupons && homePageCoupons.size() > 0){
			
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(merchantId);
			
			for(HomePageItemDTO homePageCoupon : homePageCoupons){
				homePageCoupon.setImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), homePageCoupon.getImg()));
				if(homePageCoupon.getStatus().intValue() == Coupon.CouponStatus.ACTIVE.toValue()){
					//update status
					Long currentTime = DateUtil.getCurrentDate().getTime();
					if(homePageCoupon.getStartTime().getTime() > currentTime){
						homePageCoupon.setStatus(Coupon.CouponStatus.PENDING.toValue());
					}else if(homePageCoupon.getEndTime().getTime() < currentTime){
						homePageCoupon.setStatus(Coupon.CouponStatus.EXPIRED.toValue());
					}
				}
			}
		}
		
		return homePageCoupons;
	}


	@Override
	@Transactional
	public void updateCouponSortOrder(final Long userId, final String sortOrderData, final Long userMerchantId, final String lang) {
		List<Coupon> coupons = JsonUtil.toListObject(sortOrderData, Coupon.class);
		if(null == coupons){
			throw new JsonParseException();
		}
		
		for(Coupon coupon : coupons){
			if(null == coupon.getId()){
				throw new IllegalArgumentException("Coupon id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(null == coupon.getSortOrder()){
				throw new IllegalArgumentException("Sort order "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			Coupon updCoupon = couponDao.get(coupon.getId());
			if(null != userMerchantId && userMerchantId.intValue() > 0 ){
				Campaign campaign = CampaignRequestUtil.findById(updCoupon.getCampaignId());
				if(campaign.getMerchantId().intValue() != userMerchantId.intValue()){
					throw new ForbiddenException();
				}
			}
			
			updCoupon.setSortOrder(coupon.getSortOrder());
			updCoupon.setUpdatedBy(String.valueOf(userId));
			updCoupon.setUpdatedTime(coupon.getUpdatedTime());

			couponDao.updateForVersion(updCoupon);
		}
	}


	@Override
	@Transactional
	public void deleteCoupon(Long userId, String ids, Long userMerchantId, String lang) {
		String[] idArr = ids.split(",");
		List<Object> idList = new ArrayList<Object>();
		for(String idStr : idArr){
			idList.add(Long.parseLong(idStr));
		}
		
		List<Coupon> coupons = couponDao.findByIds(idList);
		for(Coupon coupon : coupons){
			if(null != userMerchantId && userMerchantId.intValue() > 0 ){
				Campaign campaign = CampaignRequestUtil.findById(coupon.getCampaignId());
				if(campaign.getMerchantId().intValue() != userMerchantId.intValue()){
					throw new ForbiddenException();
				}
			}
			long count = homePageService.findCountByReftId(coupon.getId(),HomePage.HomePageType.BANNER.toValue());
			if(count > 0 ){
				throw new RecordIsReferencedException();
			}
		}
		
		couponLangMapDao.delByCouponIds(idList);
		
		couponMethodService.delMethodsAndQuotaByCouponIds(idList);
		
		couponDao.delByIds(idList);
	}


	@Override
	@Transactional
	public Coupon createCoupon(final Long userId,final Long campaignId,final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
			final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
			final Boolean isUseWithSameCoupon, final Boolean isUseWithOtherCoupon,final Boolean isTransferrable, final String langData, 
			final String collectMethodData, final String collectQuotaData,final String redeemMethodData,final String redeemQuotaData, final String lang) {
		
		List<CouponLangMap> couponLangMaps = JsonUtil.toListObject(langData, CouponLangMap.class);
		if(null == couponLangMaps){
			throw new JsonParseException();
		}
		
		List<CouponCollectMethodMap> CouponCollectMethods = JsonUtil.toListObject(collectMethodData, CouponCollectMethodMap.class);
		if(null == CouponCollectMethods){
			throw new JsonParseException();
		}
		
		List<CouponRedeemMethodMap> CouponRedeemMethods = JsonUtil.toListObject(redeemMethodData, CouponRedeemMethodMap.class);
		if(null == CouponRedeemMethods){
			throw new JsonParseException();
		}
		
		Campaign campaign = CampaignRequestUtil.findById(campaignId);
		if(null == campaign){
			throw new CampaignNotExistException();
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);
		Merchant merchant = MerchantRequestUtil.findById(campaign.getMerchantId());

		checkCouponTime(collectStartTime,collectEndTime,redeemStartTime,redeemEndTime,isAfterCollect,endDaysAfterCollect,campaign,merchant,lang);

		Coupon coupon = covertToNewCoupon(userId, merchant, campaign, collectStartTime, collectEndTime, redeemStartTime, redeemEndTime, isAfterCollect, endDaysAfterCollect, totalQty, type, rewardQty, maxRewardQty, issuedQuotaType, issuedQuotaQty, isUseWithSameCoupon, isUseWithOtherCoupon, isTransferrable);
		
		coupon = couponDao.add(coupon);
		
		createCouponLangMap(coupon,couponLangMaps,merchant,lang);
		
		couponMethodService.createCouponCollectMethod(coupon,CouponCollectMethods,lang);
		
		couponMethodService.createCouponRedeemMethod(coupon,CouponRedeemMethods,lang);
		
		if(StringUtils.isNotEmpty(collectQuotaData)) {
			couponMethodService.createCouponCollectQuota(coupon,collectQuotaData,lang);
		}
		
		if(StringUtils.isNotEmpty(redeemQuotaData)) {
			couponMethodService.createCouponRedeemQuota(coupon,redeemQuotaData,lang);
		}
		
		return coupon;
	}
	
	@Override
	@Transactional
	public void updateCoupon(final Long userId,final Long couponId,final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
			final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
			final Boolean isUseWithSameCoupon, final Boolean isUseWithOtherCoupon, final Boolean isTransferrable, final Integer status, final String langData, 
			final String collectMethodData, final String collectQuotaData,final String redeemMethodData,final String redeemQuotaData, final String lang) {
		
		final Coupon sourceCoupon = couponDao.get(couponId);
		if(null == sourceCoupon) {
			throw new CoupomNotExistException();
		}
		
		Campaign campaign = CampaignRequestUtil.findById(sourceCoupon.getCampaignId());
		if(null == campaign){
			throw new CampaignNotExistException();
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);
		Merchant merchant = MerchantRequestUtil.findById(campaign.getMerchantId());
		
		checkCouponTime(collectStartTime,collectEndTime,redeemStartTime,redeemEndTime,isAfterCollect,endDaysAfterCollect,campaign,merchant,lang);
		
		final Coupon updateCoupon = covertToUpdateCoupon(sourceCoupon,userId, merchant, campaign, collectStartTime, collectEndTime, redeemStartTime, redeemEndTime, isAfterCollect, endDaysAfterCollect, 
														totalQty, type, rewardQty, maxRewardQty, issuedQuotaType, issuedQuotaQty, 
														isUseWithSameCoupon, isUseWithOtherCoupon, isTransferrable,status);
		
		couponDao.updateForVersion(updateCoupon);
		
		if(StringUtils.isNotBlank(langData)){
			List<CouponLangMap> couponLangMaps = JsonUtil.toListObject(langData, CouponLangMap.class);
			if(null == couponLangMaps){
				throw new JsonParseException();
			}
			updateCouponLangMaps(updateCoupon, couponLangMaps,lang);
		}
		
		couponMethodService.updateCouponMethodAndQuota(updateCoupon,collectMethodData,collectQuotaData,redeemMethodData,redeemQuotaData,lang);
		
		//update merchat home page cache
		homePageService.updateMerchantHomePageCacheByRefId(userId, merchant.getId(), couponId, HomePage.HomePageType.COUPON.toValue(), lang);
		
		
	}
	
	private Coupon covertToUpdateCoupon(final Coupon sourceCoupon,final Long userId, final Merchant merchant, final Campaign campaign, final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
										final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
										final Boolean isUseWithSameCoupon,final Boolean isUseWithOtherCoupon, final Boolean isTransferrable,final Integer status) {
		
		Date couponCollectStartTime = TimeZoneConvert.dateTimezoneToDB(collectStartTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		Date couponCollectEndTime = TimeZoneConvert.dateTimezoneToDB(collectEndTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		Date couponRedeemStartTime = TimeZoneConvert.dateTimezoneToDB(redeemStartTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		
		sourceCoupon.setCollectStartTime(couponCollectStartTime);
		sourceCoupon.setCollectEndTime(couponCollectEndTime);
		sourceCoupon.setRedeemStartTime(couponRedeemStartTime);
		if(null != redeemEndTime) {
			Date couponRedeemEndTime = TimeZoneConvert.dateTimezoneToDB(redeemEndTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
			sourceCoupon.setRedeemEndTime(couponRedeemEndTime);
		}
		
		if(null != isAfterCollect && isAfterCollect) {
			sourceCoupon.setIsAfterCollect(isAfterCollect);
			sourceCoupon.setEndDaysAfterCollect(endDaysAfterCollect);
		}

		if(null != totalQty) {
			if(totalQty.intValue() != Coupon.NO_LIMIT_TOTAL_QTY.intValue()) {
				if(totalQty.intValue() < sourceCoupon.getIssuedQty().intValue()) {
					throw new CouponQuotaQtyLittleException();
				}
			}
			
			sourceCoupon.setTotalQty(totalQty);
		}
		
		sourceCoupon.setRewardQty(rewardQty);
		sourceCoupon.setMaxRewardQty(maxRewardQty);
		
		if(null != type) {
			if(type.intValue() == Coupon.CouponType.POINT.toValue() || type.intValue() == Coupon.CouponType.POINT_MULTIPLY.toValue() || type.intValue() == Coupon.CouponType.STAMPS_MULTIPLY.toValue()
					|| type.intValue() == Coupon.CouponType.CASH.toValue() || type.intValue() == Coupon.CouponType.DISCOUNT.toValue()) {
				sourceCoupon.setType(type);
			}
		}
		
		if(null != issuedQuotaType) {
			if(issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.NO_LIMIT.toValue() || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.DAY.toValue()
					 || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.MONTH.toValue()  || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.YEAR.toValue()
					 || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.CAMPAIGN.toValue() || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.COUPON_PERIOD.toValue()) {
				sourceCoupon.setIssuedQuotaType(issuedQuotaType);
				sourceCoupon.setIssuedQuotaQty(issuedQuotaQty);
			}
		}
		
		if(null != isUseWithSameCoupon) {
			sourceCoupon.setIsUseWithSameCoupon(isUseWithSameCoupon);
		}
		
		if(null != isUseWithOtherCoupon) {
			sourceCoupon.setIsUseWithOtherCoupon(isUseWithOtherCoupon);
		}
		
		if(null != isTransferrable) {
			sourceCoupon.setIsTransferrable(isTransferrable);
		}
		
		if(null != status){
			if(status.intValue() == Coupon.CouponStatus.ACTIVE.toValue() || status.intValue() == Coupon.CouponStatus.IN_ACTIVE.toValue()){
				if(status.intValue() == Coupon.CouponStatus.IN_ACTIVE.toValue()){
					long count = homePageService.findCountByReftId(sourceCoupon.getId(),HomePage.HomePageType.COUPON.toValue());
					if(count > 0 ){
						throw new UpdateRecordStatusException();
					}
				}
				sourceCoupon.setStatus(status);
			}
		}
		
		sourceCoupon.setUpdatedBy(String.valueOf(userId));
		
		return sourceCoupon;
	}


	private void checkCouponTime(final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,
							final Boolean isAfterCollect,final Integer endDaysAfterCollect,final Campaign campaign, final Merchant merchant,final String lang) {
		Date couponCollectStartTime = TimeZoneConvert.dateTimezoneToDB(collectStartTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		Date couponCollectEndTime = TimeZoneConvert.dateTimezoneToDB(collectEndTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		Date couponRedeemEndTime = null;
		if(!isAfterCollect) {
			couponRedeemEndTime = TimeZoneConvert.dateTimezoneToDB(redeemEndTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		}else{
			couponRedeemEndTime = TimeZoneConvert.dateTimezoneToDB(DateUtil.addDays(redeemStartTime, endDaysAfterCollect), merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(couponCollectStartTime.getTime() < campaign.getCollStartTime().getTime()){
			throw new IllegalArgumentException("Collect start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(couponCollectEndTime.getTime() > campaign.getCollEndTime().getTime()){
			throw new IllegalArgumentException("Collect end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(couponRedeemEndTime.getTime() > campaign.getCollEndTime().getTime()){
			throw new IllegalArgumentException("Collect end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
	}
	
	private Coupon covertToNewCoupon(final Long userId, final Merchant merchant, final Campaign campaign, final Date collectStartTime,final Date collectEndTime,final Date redeemStartTime,final Date redeemEndTime,final Boolean isAfterCollect,final Integer endDaysAfterCollect,
			final Integer totalQty,final Integer type, final Double rewardQty, final Double maxRewardQty,final Integer issuedQuotaType,final Integer issuedQuotaQty,
			final Boolean isUseWithSameCoupon,final Boolean isUseWithOtherCoupon, final Boolean isTransferrable) {
		final Coupon coupon = new Coupon();
		
		Date couponCollectStartTime = TimeZoneConvert.dateTimezoneToDB(collectStartTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		Date couponCollectEndTime = TimeZoneConvert.dateTimezoneToDB(collectEndTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		Date couponRedeemStartTime = TimeZoneConvert.dateTimezoneToDB(redeemStartTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		
		coupon.setMerchantId(campaign.getMerchantId());
		coupon.setCampaignId(campaign.getId());
		coupon.setCollectStartTime(couponCollectStartTime);
		coupon.setCollectEndTime(couponCollectEndTime);
		coupon.setRedeemStartTime(couponRedeemStartTime);
		coupon.setIssuedQty(0);
		if(null != redeemEndTime) {
			Date couponRedeemEndTime = TimeZoneConvert.dateTimezoneToDB(redeemEndTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
			coupon.setRedeemEndTime(couponRedeemEndTime);
		}
		
		if(null != isAfterCollect && isAfterCollect) {
			coupon.setIsAfterCollect(isAfterCollect);
			coupon.setEndDaysAfterCollect(endDaysAfterCollect);
		}

		coupon.setTotalQty(Coupon.NO_LIMIT_TOTAL_QTY);
		if(null != totalQty) {
			coupon.setTotalQty(totalQty);
		}
		
		coupon.setRewardQty(rewardQty);
		coupon.setMaxRewardQty(maxRewardQty);
		
		coupon.setType(Coupon.CouponType.STAMPS.toValue());
		if(null != type) {
			if(type.intValue() == Coupon.CouponType.POINT.toValue() || type.intValue() == Coupon.CouponType.POINT_MULTIPLY.toValue() || type.intValue() == Coupon.CouponType.STAMPS_MULTIPLY.toValue()
					|| type.intValue() == Coupon.CouponType.CASH.toValue() || type.intValue() == Coupon.CouponType.DISCOUNT.toValue()) {
				coupon.setType(type);
			}
		}
		
		if(null != issuedQuotaType) {
			if(issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.NO_LIMIT.toValue() || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.DAY.toValue()
					 || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.MONTH.toValue()  || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.YEAR.toValue()
					 || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.CAMPAIGN.toValue() || issuedQuotaType.intValue() == Coupon.CouponIssuedQuotaType.COUPON_PERIOD.toValue()) {
				coupon.setIssuedQuotaType(issuedQuotaType);
				coupon.setIssuedQuotaQty(issuedQuotaQty);
			}
		}
		
		if(null != isUseWithSameCoupon) {
			coupon.setIsUseWithSameCoupon(isUseWithSameCoupon);
		}
		
		if(null != isUseWithOtherCoupon) {
			coupon.setIsUseWithOtherCoupon(isUseWithOtherCoupon);
		}
		
		if(null != isTransferrable) {
			coupon.setIsTransferrable(isTransferrable);
		}
		
		coupon.setStatus(Coupon.CouponStatus.ACTIVE.toValue());
		coupon.setCreatedBy(String.valueOf(userId));
		coupon.setCreatedTime(DateUtil.getCurrentDate());
		coupon.setIsDeleted(false);
		
		coupon.setSortOrder(couponDao.findSortOrderByMerchantId(campaign.getMerchantId()));
		return coupon;
	}
	
	private void createCouponLangMap(Coupon coupon, List<CouponLangMap> couponLangMaps, Merchant merchant, String lang) {
		List<CouponLangMap> addCouponLangMaps = new ArrayList<CouponLangMap>();
		
		List<String> langCodes = MerchantRequestUtil.findMerchantLangCodes(merchant.getId());
		String merchantDefaultLangCode = MerchantRequestUtil.findMerchantDefaultLang(merchant.getId());
		
		CouponLangMap defaultCouponLangMap = null;
		for(CouponLangMap couponLangMap : couponLangMaps){
			if(StringUtils.isBlank(couponLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(couponLangMap.getName())){
				throw new IllegalArgumentException("Name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			if(!langCodes.contains(couponLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			CouponLangMap newCouponLangMap = new CouponLangMap();
			BeanUtils.copyProperties(couponLangMap, newCouponLangMap);
			newCouponLangMap.setCreatedBy(coupon.getCreatedBy());
			newCouponLangMap.setCreatedTime(coupon.getCreatedTime());
			newCouponLangMap.setLangCode(couponLangMap.getLangCode());
			newCouponLangMap.setCouponId(coupon.getId());
			newCouponLangMap.setName(couponLangMap.getName());
			
			addCouponLangMaps.add(newCouponLangMap);
			if(couponLangMap.getLangCode().equals(merchantDefaultLangCode)){
				defaultCouponLangMap = new CouponLangMap();
				BeanUtils.copyProperties(newCouponLangMap, defaultCouponLangMap);
			}
			langCodes.remove(couponLangMap.getLangCode());
		}
		if(null == defaultCouponLangMap){
			throw new IllegalArgumentException("Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		for(String langCode : langCodes){
			defaultCouponLangMap.setLangCode(langCode);
			
			CouponLangMap newCouponLangMap = new CouponLangMap();
			BeanUtils.copyProperties(defaultCouponLangMap, newCouponLangMap);
			addCouponLangMaps.add(newCouponLangMap);
		}
		couponLangMapDao.batchAdd(addCouponLangMaps);
	}
	
	private void updateCouponLangMaps(Coupon coupon, List<CouponLangMap> couponLangMaps, String lang) {
		for(CouponLangMap couponLangMap : couponLangMaps){
			if(null == couponLangMap.getId()){
				throw new IllegalArgumentException("Coupon lang map id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			CouponLangMap updCouponLangMap = couponLangMapDao.get(couponLangMap.getId());
			if(null == updCouponLangMap){
				throw new CoupomNotExistException();
			}
			if(updCouponLangMap.getCouponId().intValue() != coupon.getId().intValue()){
				throw new ForbiddenException();
			}
			
			if(null != couponLangMap.getDescr()){
				updCouponLangMap.setDescr(couponLangMap.getDescr());
			}
			if(null != couponLangMap.getImg()){
				updCouponLangMap.setImg(couponLangMap.getImg());
			}
			if(null != couponLangMap.getName()){
				updCouponLangMap.setName(couponLangMap.getName());
			}
			if(null != couponLangMap.getTerms()){
				updCouponLangMap.setTerms(couponLangMap.getTerms());
			}
			
			updCouponLangMap.setUpdatedBy(coupon.getUpdatedBy());
			
			couponLangMapDao.updateForVersion(updCouponLangMap);
		}
	}

}