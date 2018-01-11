package com.cherrypicks.tcc.cms.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.annotation.MerchantPermissionAuth;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.campaign.service.CampaignLangMapService;
import com.cherrypicks.tcc.cms.campaign.service.CampaignService;
import com.cherrypicks.tcc.cms.dto.CampaignDetailDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class CampaignController extends BaseController<Campaign>{

	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private CampaignLangMapService campaignLangMapService;

	@Override
	@Autowired
	public void setBaseService(final IBaseService<Campaign> campaignService) {
		// TODO Auto-generated method stub
		super.setBaseService(campaignService);
	}

	@RequestMapping(value="/getCampaignList",method=RequestMethod.POST)
	public PagingResultVo getCampaignList(final Long userId, final Long merchantId, final String name,final Integer status,final String reservationCode, final String sortField,final String sortType,
			final Integer startRow,final Integer maxRows,final String langCode, final String lang){

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("status", status);
		criteriaMap.put("name", name);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("reservationCode", reservationCode);

		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			criteriaMap.put("merchantId", SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId));
		}
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}

	@MerchantPermissionAuth
	@RequestMapping(value="/getHomePageCampaignList",method=RequestMethod.POST)
	public ResultVO getHomePageCampaignList(final Long userId, final Long merchantId,final Integer status, final String lang){

		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final List<HomePageItemDTO> campaigns = campaignService.findHomePageCampaignList(userId,merchantId,status,lang);

		final ResultVO result = new ResultVO();
		result.setResult(campaigns);
		return result;
	}

	@MerchantPermissionAuth
	@RequestMapping(value="/createCampaign",method=RequestMethod.POST)
	public ResultVO addCampaign(final Long userId,final Long merchantId,final Integer stampRatio,final String prmBannerUrl,
			final Date startTime,final Date endTime,final Date collStartTime, final Date collEndTime,final Date redeemStartTime,final Date redeemEndTime,
			/*final Date reservationExpiredTime, */final Integer inappOpen,final Integer grantStampQty,final Integer grantType,final String langData,final String lang){

		AssertUtil.notNull(merchantId, "Merchant "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startTime, "Star time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collStartTime, "Coll start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collEndTime, "Coll end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(redeemStartTime, "Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(redeemEndTime, "Redeem end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(stampRatio, "Stamp ratio "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(startTime.getTime() >= endTime.getTime()){
			throw new IllegalArgumentException("Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(collStartTime.getTime() < startTime.getTime() || collStartTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Coll start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(collEndTime.getTime() < collStartTime.getTime() || collEndTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Coll end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(redeemStartTime.getTime() <= collStartTime.getTime() || redeemStartTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(redeemEndTime.getTime() <= redeemStartTime.getTime() || redeemEndTime.getTime() < collEndTime.getTime() || redeemEndTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Redeem end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		final CampaignDetailDTO campaign = campaignService.createCampiagn(userId, merchantId, stampRatio, prmBannerUrl, startTime, endTime, collStartTime, collEndTime, redeemStartTime, redeemEndTime, inappOpen,grantStampQty,grantType, langData, lang);

		final ResultVO result = new ResultVO();
		result.setResult(campaign);
		return result;
	}

	@RequestMapping(value="/updateCampaign",method=RequestMethod.POST)
	public ResultVO updateCampaign(final Long userId,final Long campaignId,final Integer stampRatio,final String prmBannerUrl,
			final Date startTime,final Date endTime,final Date collStartTime, final Date collEndTime,final Date redeemStartTime,final Date redeemEndTime,
			/*final Date reservationExpiredTime,*/ final Integer status, final Integer inappOpen, final Integer grantStampQty,final Integer grantType,final String langData, final String lang){

		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startTime, "Star time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collStartTime, "Coll start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(collEndTime, "Coll end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(redeemStartTime, "Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(redeemEndTime, "Redeem end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(startTime.getTime() >= endTime.getTime()){
			throw new IllegalArgumentException("Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(collStartTime.getTime() < startTime.getTime() || collStartTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Coll start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(collEndTime.getTime() < collStartTime.getTime() || collEndTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Coll end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(redeemStartTime.getTime() <= collStartTime.getTime() || redeemStartTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Redeem start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		if(redeemEndTime.getTime() <= redeemStartTime.getTime() || redeemEndTime.getTime() < collEndTime.getTime() || redeemEndTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("Redeem end time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		final Campaign campaign = campaignService.updateCampaign(userId, campaignId, stampRatio,  prmBannerUrl, startTime, endTime, collStartTime, collEndTime, redeemStartTime, redeemEndTime, status, inappOpen, grantStampQty,grantType,langData, lang);

		final ResultVO result = new ResultVO();
		result.setResult(campaign);
		return result;
	}

	@RequestMapping(value="/getCampaignDetail",method=RequestMethod.POST)
	public ResultVO getCampaignDetail(final Long userId,final Long campaignId,final String lang){

		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final ResultVO result = new ResultVO();
		result.setResult(campaignService.findDetailById(userId,campaignId,lang));
		return result;
	}

	@RequestMapping(value="/getCustomerCampaign",method=RequestMethod.POST)
	public ResultVO getCustomerCampaign(final Long userId,final Long customerId,final String lang){

		AssertUtil.notNull(customerId, "User id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final ResultVO result = new ResultVO();
		result.setResult(campaignService.findByCustomerId(userId,customerId,lang));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getCampaignById",method=RequestMethod.POST)
	public ResultVO getCampaignById(final Long id) {
		
		AssertUtil.notNull(id, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(campaignService.findById(id));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getCampaignDetailById",method=RequestMethod.POST)
	public ResultVO getCampaignDetailById(final Long userId, final Long campaignId, final String lang) {
		
		AssertUtil.notNull(userId, "User id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(lang, "Lang "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(campaignService.findDetailById(userId, campaignId, lang));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getCampaignDefaultName",method=RequestMethod.POST)
	public ResultVO getCampaignDefaultName(final Long campaignId) {
		
		ResultVO result = new ResultVO();
		result.setResult(campaignLangMapService.findCampaignDefaultLangMap(campaignId).getName());
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/exportCampaignReport",method=RequestMethod.POST)
	public ResultVO exportCampaignReport(final Long campaignId) {
		
		ResultVO result = new ResultVO();
		result.setResult(campaignService.getCampaignReport(campaignId));
		return result;
	}
}
