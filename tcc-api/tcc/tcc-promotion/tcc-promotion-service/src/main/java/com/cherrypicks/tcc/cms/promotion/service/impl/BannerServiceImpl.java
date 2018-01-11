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
import com.cherrypicks.tcc.cms.api.http.util.MerchantConfigRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.BannerIDetailDTO;
import com.cherrypicks.tcc.cms.dto.BannerItemDTO;
import com.cherrypicks.tcc.cms.dto.BannerLangMapDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.exception.BannerNotExistException;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.exception.RecordIsReferencedException;
import com.cherrypicks.tcc.cms.exception.UpdateRecordStatusException;
import com.cherrypicks.tcc.cms.promotion.dao.BannerDao;
import com.cherrypicks.tcc.cms.promotion.dao.BannerLangMapDao;
import com.cherrypicks.tcc.cms.promotion.service.BannerService;
import com.cherrypicks.tcc.cms.promotion.service.HomePageService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.Banner;
import com.cherrypicks.tcc.model.BannerLangMap;
import com.cherrypicks.tcc.model.Game;
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
public class BannerServiceImpl extends AbstractBaseService<Banner> implements BannerService {
	
	@Autowired
	private BannerDao bannerDao;
	
	@Autowired
	private BannerLangMapDao bannerLangMapDao;
	
	@Autowired
	private HomePageService homePageService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Banner> bannerDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(bannerDao);
	}


	@Override
	public BannerIDetailDTO findDetailById(final Long userId,  final Long bannerId,  final String lang) {
		Banner banner = bannerDao.get(bannerId);
		if(null != banner){
			
			AuthorizeRequestUtil.checkUserMerchantPermission(userId, banner.getMerchantId(),lang);
			
			Merchant merchant = MerchantRequestUtil.findById(banner.getMerchantId());
			
			BannerIDetailDTO bannerDetail = new BannerIDetailDTO();
			BeanUtils.copyProperties(banner, bannerDetail);
			
			if(bannerDetail.getStatus().intValue() == Banner.BannerStatus.ACTIVE.toValue()){
				long nowTime = DateUtil.getCurrentDate().getTime();
				if(bannerDetail.getStartTime().getTime() > nowTime){
					bannerDetail.setStatus(Banner.BannerStatus.PENDING.toValue());
				}else if(bannerDetail.getEndTime().getTime() < nowTime){
					bannerDetail.setStatus(Banner.BannerStatus.EXPIRED.toValue());
				}
			}
			
			bannerDetail.setStartTime(TimeZoneConvert.dateTimezoneToUI(banner.getStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			bannerDetail.setEndTime(TimeZoneConvert.dateTimezoneToUI(banner.getEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			
			List<BannerLangMapDTO> bannerLangMaps = bannerLangMapDao.findByBannerId(bannerId);		
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(banner.getMerchantId());

			for(BannerLangMapDTO bannerLangMap : bannerLangMaps){
				bannerLangMap.setFullImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), bannerLangMap.getImg()));
			}
			
			bannerDetail.setBannerLangMaps(bannerLangMaps);
			
			return bannerDetail;
		}
		
		return null;
	}


	@Override
	@Transactional
	public Banner createBanner(final Long userId, final Long merchantId, final String webUrl, final Integer inappOpen,final Date startTime,final Date endTime,final String langData, final String lang) {
		List<BannerLangMap> bannerLangMaps = JsonUtil.toListObject(langData, BannerLangMap.class);
		if(null == bannerLangMaps){
			throw new JsonParseException();
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, merchantId, lang);
		
		Merchant merchant = MerchantRequestUtil.findById(merchantId);
		
		Banner banner = new Banner();
		banner.setCreatedBy(String.valueOf(userId));
		banner.setCreatedTime(DateUtil.getCurrentDate());
		banner.setStartTime(TimeZoneConvert.dateTimezoneToDB(startTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		banner.setEndTime(TimeZoneConvert.dateTimezoneToDB(endTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		banner.setMerchantId(merchantId);
		banner.setInappOpen(Banner.BannerInappOpenStatus.IN_APP.toValue());
		banner.setStatus(Banner.BannerStatus.ACTIVE.toValue());
		banner.setSortOrder(bannerDao.findSortOrderByMerchantId(merchantId));
		if(inappOpen.intValue() == Banner.BannerInappOpenStatus.NATIVE_BROWSER.toValue()){
			banner.setInappOpen(inappOpen);
		}
		
		if(StringUtils.isNotBlank(webUrl)){
			banner.setWebUrl(webUrl);
		}
		
		bannerDao.add(banner);
		
		createBannerLangMap(banner,bannerLangMaps,lang);
		
		return banner;
	}


	private void createBannerLangMap(Banner banner, List<BannerLangMap> bannerLangMaps, String lang) {
		
		List<String> langCodes = MerchantRequestUtil.findMerchantLangCodes(banner.getMerchantId());
		String merchantDefaultLangCode = MerchantRequestUtil.findMerchantDefaultLang(banner.getMerchantId());
		
		BannerLangMap bannerDefaultLangMap = null;
		for(BannerLangMap bannerLangMap : bannerLangMaps){
			if(StringUtils.isBlank(bannerLangMap.getImg())){
				throw new IllegalArgumentException("Img "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(bannerLangMap.getName())){
				throw new IllegalArgumentException("Name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(bannerLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(!langCodes.contains(bannerLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			bannerLangMap.setBannerId(banner.getId());
			bannerLangMap.setCreatedBy(banner.getCreatedBy());
			bannerLangMap.setCreatedTime(banner.getCreatedTime());
			
			bannerLangMapDao.add(bannerLangMap);

			if(merchantDefaultLangCode.equalsIgnoreCase(bannerLangMap.getLangCode())){
				bannerDefaultLangMap = bannerLangMap;
			}
			
			langCodes.remove(bannerLangMap.getLangCode());
		}
		
		if(null == bannerDefaultLangMap){
			throw new IllegalArgumentException("Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		for(String langCode : langCodes){
			bannerDefaultLangMap.setId(null);
			bannerDefaultLangMap.setLangCode(langCode);
			
			bannerLangMapDao.add(bannerDefaultLangMap);
		}
		
	}


	@Override
	@Transactional
	public void updateBanner(Long userId, Long bannerId, Integer status, String webUrl, Integer inappOpen,final Date startTime,final Date endTime,String langData, String lang) {
		Banner banner = bannerDao.get(bannerId);
		if(null == banner){
			throw new BannerNotExistException();
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, banner.getMerchantId(), lang);
		
		Merchant merchant = MerchantRequestUtil.findById(banner.getMerchantId());
		
		banner.setWebUrl(webUrl);
	
		if(status.intValue() == Banner.BannerStatus.ACTIVE.toValue() || status.intValue() == Banner.BannerStatus.IN_ACTIVE.toValue()){
			if(status.intValue() == Game.Status.IN_ACTIVE.getCode()){
				long count = homePageService.findCountByReftId(banner.getId(),HomePage.HomePageType.BANNER.toValue());
				if(count > 0 ){
					throw new UpdateRecordStatusException();
				}
			}
			banner.setStatus(status);
		}
		if(inappOpen.intValue() == Banner.BannerInappOpenStatus.IN_APP.toValue() || inappOpen.intValue() == Banner.BannerInappOpenStatus.NATIVE_BROWSER.toValue()){
			banner.setInappOpen(inappOpen);
		}
		if(null != startTime){
			if(null == endTime){
				if(TimeZoneConvert.dateTimezoneToDB(startTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE).getTime() > banner.getEndTime().getTime()){
					throw new IllegalArgumentException("Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
			}
			banner.setStartTime(TimeZoneConvert.dateTimezoneToDB(startTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		}
		if(null != endTime){
			if(null == startTime){
				if(TimeZoneConvert.dateTimezoneToDB(endTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE).getTime() < banner.getStartTime().getTime()){
					throw new IllegalArgumentException("End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
			}
			banner.setEndTime(TimeZoneConvert.dateTimezoneToDB(endTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		}
		
		banner.setUpdatedBy(String.valueOf(userId));
		bannerDao.updateForVersion(banner);
		
		if(StringUtils.isNotBlank(langData)){
			List<BannerLangMap> bannerLangMaps = JsonUtil.toListObject(langData, BannerLangMap.class);
			if(null == bannerLangMaps){
				throw new JsonParseException();
			}
			
			updateBannerLangMaps(banner,bannerLangMaps,lang);
		}
		
		//update merchat home page cache
		homePageService.updateMerchantHomePageCacheByRefId(userId, banner.getMerchantId(), banner.getId(), HomePage.HomePageType.BANNER.toValue(), lang);

	}


	private void updateBannerLangMaps(Banner banner, List<BannerLangMap> bannerLangMaps, String lang) {
		for(BannerLangMap bannerLangMap : bannerLangMaps){
			if(null == bannerLangMap.getId()){
				throw new IllegalArgumentException("Banner lang map id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			if(StringUtils.isBlank(bannerLangMap.getName())){
				throw new IllegalArgumentException("Banner name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			BannerLangMap updBannerLangMap = bannerLangMapDao.get(bannerLangMap.getId());
			
			if(null == updBannerLangMap){
				throw new BannerNotExistException();
			}
			if(updBannerLangMap.getBannerId().intValue() != banner.getId().intValue()){
				throw new ForbiddenException();
			}
			
			if(StringUtils.isNotBlank(bannerLangMap.getName())){
				updBannerLangMap.setName(bannerLangMap.getName());
			}
			if(StringUtils.isNotBlank(bannerLangMap.getImg())){
				updBannerLangMap.setImg(bannerLangMap.getImg());
			}
			updBannerLangMap.setUpdatedBy(banner.getUpdatedBy());
			
			bannerLangMapDao.updateForVersion(updBannerLangMap);
			
		}
	}


	@Override
	@Transactional
	public void deleteBanner(final Long userId, final String ids, final Long userMerchantId,final String lang) {
		String[] idArr = ids.split(",");
		List<Object> idList = new ArrayList<Object>();
		for(String idStr : idArr){
			idList.add(Long.parseLong(idStr));
		}
		
		List<Banner> banners = bannerDao.findByIds(idList);
		for(Banner banner : banners){
			if(null != userMerchantId && userMerchantId.intValue() > 0 ){
				if(banner.getMerchantId().intValue() != userMerchantId.intValue()){
					throw new ForbiddenException();
				}
			}
			long count = homePageService.findCountByReftId(banner.getId(),HomePage.HomePageType.BANNER.toValue());
			if(count > 0 ){
				throw new RecordIsReferencedException();
			}
		}
		
		bannerLangMapDao.delByBannerIds(idList);
		
		bannerDao.delByIds(idList);
	}


	@Override
	@Transactional
	public void updateBannerSortOrder(final Long userId, final String sortOrderData, final Long userMerchantId,final String lang) {
		List<Banner> banners = JsonUtil.toListObject(sortOrderData, Banner.class);
		if(null == banners){
			throw new JsonParseException();
		}
		
		for(Banner banner : banners){
			if(null == banner.getId()){
				throw new IllegalArgumentException("Banner id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(null == banner.getSortOrder()){
				throw new IllegalArgumentException("Sort order "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(null == banner.getUpdatedTime()){
				throw new IllegalArgumentException("Updated time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			Banner updBanner = bannerDao.get(banner.getId());
			if(null != userMerchantId && userMerchantId.intValue() > 0 ){
				if(updBanner.getMerchantId().intValue() != userMerchantId.intValue()){
					throw new ForbiddenException();
				}
			}
			
			updBanner.setSortOrder(banner.getSortOrder());
			updBanner.setUpdatedBy(String.valueOf(userId));
			updBanner.setUpdatedTime(banner.getUpdatedTime());
			
			bannerDao.updateForVersion(updBanner);
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BannerItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		List<BannerItemDTO> banners = (List<BannerItemDTO>) super.findByFilter(criteriaMap, sortField, sortType, args);
		
		if(null != banners && banners.size() > 0 ){
			for(BannerItemDTO banner : banners){
				if(banner.getStatus().intValue() == Banner.BannerStatus.ACTIVE.toValue()){
					long nowTime = DateUtil.getCurrentDate().getTime();
					if(banner.getStartTime().getTime() > nowTime){
						banner.setStatus(Banner.BannerStatus.PENDING.toValue());
					}else if(banner.getEndTime().getTime() < nowTime){
						banner.setStatus(Banner.BannerStatus.EXPIRED.toValue());
					}
				}
			}
		}
		
		return banners;
	}


	@Override
	public List<HomePageItemDTO> findHomePageBannerList(final Long userId, final Long merchantId, final Integer status, final String lang) {
		
		List<HomePageItemDTO> homePageBanners = bannerDao.findHomePageBannerList(merchantId,status);
		
		if(null != homePageBanners && homePageBanners.size() > 0){
			
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(merchantId);
			
			for(HomePageItemDTO homePageBanner : homePageBanners){
				homePageBanner.setImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), homePageBanner.getImg()));
				if(homePageBanner.getStatus().intValue() == Banner.BannerStatus.ACTIVE.toValue()){
					long nowTime = DateUtil.getCurrentDate().getTime();
					if(homePageBanner.getStartTime().getTime() > nowTime){
						homePageBanner.setStatus(Banner.BannerStatus.PENDING.toValue());
					}else if(homePageBanner.getEndTime().getTime() < nowTime){
						homePageBanner.setStatus(Banner.BannerStatus.EXPIRED.toValue());
					}
				}
			}
		}
		
		return homePageBanners;
	}

}
