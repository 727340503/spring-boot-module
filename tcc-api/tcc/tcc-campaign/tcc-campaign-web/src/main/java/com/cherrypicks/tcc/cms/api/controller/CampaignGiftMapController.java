package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.campaign.service.CampaignGiftMapService;
import com.cherrypicks.tcc.cms.dto.CampaignGiftMapDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.CampaignGiftMap;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class CampaignGiftMapController extends BaseController<CampaignGiftMap> {

	@Autowired
	private CampaignGiftMapService campaignGiftMapService;

	@Override
	@Autowired
	public void setBaseService(IBaseService<CampaignGiftMap> campaignGiftService) {
		// TODO Auto-generated method stub
		super.setBaseService(campaignGiftService);
	}

	@RequestMapping(value = "/addCampaignGift", method = RequestMethod.POST)
	public ResultVO addCampaignGift(final Long userId, final Long campaignId, final Long giftId,
			final String externalGiftId, final Integer isReservation, final String giftExchangeTypeData, String lang) {

		AssertUtil.notNull(campaignId,"Campaign id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(giftId, "Gift id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(externalGiftId,"External gift id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(isReservation,"Is reservation " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(giftExchangeTypeData,"Gift exchange type " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		campaignGiftMapService.addCampaignGifts(userId, campaignId, giftId, externalGiftId, isReservation,giftExchangeTypeData, lang);

		ResultVO result = new ResultVO();
		return result;
	}

	@RequestMapping(value = "/udpateCampaignGift", method = RequestMethod.POST)
	public SuccessVO udpateCampaignGift(final Long userId, final Long campaignGiftMapId, final String externalGiftId,
			final Integer status, final Integer isReservation,final String giftExchangeTypeData, String lang) {

		AssertUtil.notNull(campaignGiftMapId,"Camp gift map id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(giftExchangeTypeData,"Gift exchange type " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		campaignGiftMapService.updateCampaignGifts(userId, campaignGiftMapId, externalGiftId, status, isReservation, giftExchangeTypeData, lang);

		return new SuccessVO();
	}

	@RequestMapping(value = "/getCampaignGiftMapDetail", method = RequestMethod.POST)
	public ResultVO getCampaignGiftMapDetail(final Long userId, final Long campaignId, final Long giftId,
			final String lang) {

		AssertUtil.notNull(campaignId,"Campaign id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(giftId, "Gift id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		CampaignGiftMapDetailDTO campGiftMaps = campaignGiftMapService.findByCampaignIdAndGiftID(userId,campaignId, giftId,lang);

		ResultVO result = new ResultVO();
		result.setResult(campGiftMaps);
		return result;
	}
	
	@RequestMapping(value="/getCampaignGiftList",method=RequestMethod.POST)
	public PagingResultVo getCampaignGiftList(final Long campaignId,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(campaignId,"Campaign id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("campaignId", campaignId);
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}

}