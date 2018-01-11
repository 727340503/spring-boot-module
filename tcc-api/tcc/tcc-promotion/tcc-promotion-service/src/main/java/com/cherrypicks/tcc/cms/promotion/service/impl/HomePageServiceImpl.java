package com.cherrypicks.tcc.cms.promotion.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.MerchantConfigRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.HomePageDetailDTO;
import com.cherrypicks.tcc.cms.dto.MerchantHomePageDTO;
import com.cherrypicks.tcc.cms.exception.HomePageDraftNotExistException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.jedis.RedisKey;
import com.cherrypicks.tcc.cms.promotion.dao.HomePageDao;
import com.cherrypicks.tcc.cms.promotion.dao.cache.HomePageCacheDao;
import com.cherrypicks.tcc.cms.promotion.service.HomePageService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.Banner;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.model.Game;
import com.cherrypicks.tcc.model.HomePage;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.Json;
import com.cherrypicks.tcc.util.JsonUtil;



@Service
public class HomePageServiceImpl extends AbstractBaseService<HomePage> implements HomePageService {


	@Autowired
	private HomePageDao homePageDao;

	@Autowired
	private HomePageCacheDao homePageCacheDao;
	
	@Override
	@Autowired
	public void setBaseDao(final IBaseDao<HomePage> homePageDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(homePageDao);
	}

	@Override
	@Transactional
	public void createHomePage(final Long userId, final Long merchantId, final String homePageFrame, final String homePageData, final String lang) {

		final Merchant merchant = MerchantRequestUtil.findById(merchantId);
		if(StringUtils.isNotBlank(merchant.getHomePageDraft())){
			homePageDao.delByMerchantIdAndStatus(merchantId,HomePage.EditStatus.DRAFT.getCode());
		}

		final List<HomePage> homePages = JsonUtil.toListObject(homePageData, HomePage.class);
		if(null == homePages){
			throw new JsonParseException();
		}

		for(final HomePage homePage : homePages){
			if(null == homePage.getRefId()){
				throw new IllegalArgumentException("Ref id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(null == homePage.getType()){
				throw new IllegalArgumentException("Type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(null == homePage.getSortOrder()){
				homePage.setSortOrder(0);
			}
			homePage.setId(null);
			homePage.setCreatedBy(String.valueOf(userId));
			homePage.setCreatedTime(DateUtil.getCurrentDate());
			homePage.setMerchantId(merchantId);
			homePage.setStatus(HomePage.EditStatus.DRAFT.getCode());

			homePageDao.add(homePage);
		}
		
		merchant.setHomePageDraft(homePageFrame);
		merchant.setUpdatedBy(String.valueOf(userId));
		MerchantRequestUtil.updateMerchantForVersion(merchant);
	}

	@Override
    @Transactional
	public void updateMerchantHomePageCache(final Long userId,final Long merchantId,final String lang) {
		final List<String> merchantLangMaps = MerchantRequestUtil.findMerchantLangCodes(merchantId);
		final Merchant merchant = MerchantRequestUtil.findById(merchantId);

		for(final String langCode : merchantLangMaps){
			final String key = RedisKey.getHomePageKey(merchantId, langCode);

			final MerchantHomePageDTO merchantHomePage = new MerchantHomePageDTO();
			merchantHomePage.setHomePage(merchant.getHomePage());
			merchantHomePage.setRecords(findMerchantHomePageByStatus(userId, merchantId, langCode, HomePage.EditStatus.ON_LINE.getCode(), lang));

			homePageCacheDao.addString(key, Json.toJson(merchantHomePage));
		}

	}

	@Override
	public List<HomePageDetailDTO> findMerchantHomePageByStatus(final Long userId, final Long merchantId, String langCode, final Integer status,final String lang) {

		if(StringUtils.isBlank(langCode)){
			langCode = MerchantRequestUtil.findMerchantDefaultLang(merchantId);
		}
		
		final MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(merchantId);
		final List<HomePageDetailDTO> homePageDetails = homePageDao.findMerchantHomePageList(merchantId,status,langCode);
		for(final HomePageDetailDTO homePageDetail : homePageDetails){
			homePageDetail.setImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(),homePageDetail.getImg()));
			setHomePageStatu(homePageDetail);
		}

		return homePageDetails;
	}
	
	private void setHomePageStatu(final HomePageDetailDTO homePageDetail) {
		final long nowTime = DateUtil.getCurrentDate().getTime();
		if(homePageDetail.getType().intValue() == HomePage.HomePageType.COUPON.toValue()){
			if(homePageDetail.getStatus().intValue() == Coupon.CouponStatus.ACTIVE.toValue()){
				if(homePageDetail.getStartTime().getTime() > nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.PENDING.getCode());
				}else if(homePageDetail.getEndTime().getTime() < nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.EXPIRED.getCode());
				}
			}
		}else if(homePageDetail.getType().intValue() == HomePage.HomePageType.BANNER.toValue()){
			if(homePageDetail.getStatus().intValue() == Banner.BannerStatus.ACTIVE.toValue()){
				if(homePageDetail.getStartTime().getTime() > nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.PENDING.getCode());
				}else if(homePageDetail.getEndTime().getTime() < nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.EXPIRED.getCode());
				}
			}
		}else if(homePageDetail.getType().intValue() == HomePage.HomePageType.GAME.toValue()){
			if(homePageDetail.getStatus().intValue() == Game.Status.ACTIVE.getCode()){
				if(homePageDetail.getStartTime().getTime() > nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.PENDING.getCode());
				}else if(homePageDetail.getEndTime().getTime() < nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.EXPIRED.getCode());
				}
			}
		}else{
			if(homePageDetail.getStatus().intValue() == Campaign.CampaignStatus.ACTIVE.toValue()){
				if(homePageDetail.getStartTime().getTime() > nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.PENDING.getCode());
				}else if(homePageDetail.getEndTime().getTime() < nowTime){
					homePageDetail.setStatus(HomePage.HomePageRefStatus.EXPIRED.getCode());
				}
			}
		}
	}

	@Override
	@Transactional
	public void updateMerchantHomePageCacheByRefId(final Long userId,final Long merchantId,final Long refId, final Integer type,final String lang) {
		
		final long homePageReferenceCount = homePageDao.findCountByMerchantIdAndRefId(merchantId,refId, type);
		if(homePageReferenceCount > 0){
			updateMerchantHomePageCache(userId, merchantId, lang);
		}

	}

	@Override
	public long findCountByReftId(final Long refId, final Integer type) {
		return homePageDao.findCountByReftId(refId,type);
	}

	@Override
	@Transactional
	public void publishMerchantHomePage(final Long userId, final Long merchantId, final String lang) {

		final Merchant merchant = MerchantRequestUtil.findById(merchantId);
		if(StringUtils.isBlank(merchant.getHomePageDraft())){
			throw new HomePageDraftNotExistException();
		}
		if(StringUtils.isNotBlank(merchant.getHomePage())){
			homePageDao.delByMerchantIdAndStatus(merchantId,HomePage.EditStatus.ON_LINE.getCode());
		}

		List<HomePage> merchantDrafHomePages = homePageDao.findByMerchantIdAndStatus(merchantId, HomePage.EditStatus.DRAFT.getCode());
		
		for(HomePage homePage : merchantDrafHomePages){
			homePage.setCreatedTime(DateUtil.getCurrentDate());
			homePage.setCreatedBy(String.valueOf(userId));
			homePage.setId(null);
			homePage.setStatus(HomePage.EditStatus.ON_LINE.getCode());
		}
		
		homePageDao.batchAdd(merchantDrafHomePages);
		
		updateMerchantHomePageCache(userId,merchantId,lang);
		
		merchant.setHomePage(merchant.getHomePageDraft());
		merchant.setUpdatedBy(String.valueOf(userId));
		MerchantRequestUtil.updateMerchantForVersion(merchant);
	}
}
