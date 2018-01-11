package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.MerchantPermissionAuth;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.campaign.service.GiftService;
import com.cherrypicks.tcc.cms.dto.GiftDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Gift;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class GiftController extends BaseController<Gift>{

	@Autowired
	private GiftService giftService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Gift> giftService) {
		// TODO Auto-generated method stub
		super.setBaseService(giftService);
	}
	
	@RequestMapping(value="/getGiftList",method=RequestMethod.POST)
	public PagingResultVo getGiftList(final Long userId,final Long merchantId,final Long campaignId,final Boolean isRelatedCampaign,final String name,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("name", name);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("campaignId", campaignId);
		criteriaMap.put("isRelatedCampaign", isRelatedCampaign);
		
		//判断用户属于Platform admin
		SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			criteriaMap.put("merchantId", SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId));
		}
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/getGiftDetail",method=RequestMethod.POST)
	public ResultVO getGiftDetail(final Long userId,final Long giftId,final String lang){
		
		AssertUtil.notNull(giftId, "Gift id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
	
		GiftDetailDTO giftDetail = giftService.findGiftDetail(userId,giftId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(giftDetail);
		return result;
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/createGift",method=RequestMethod.POST)
	public ResultVO createGift(final Long userId,final Long merchantId,final String langData,final String lang){
		
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Gift gift = giftService.createGift(userId,merchantId,langData,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(gift);
		return result;
	}
	
	@RequestMapping(value="/updateGift",method=RequestMethod.POST)
	public SuccessVO updateGift(final Long userId,final Long giftId,final String langData,final String lang){
		
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(giftId, "Gift id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		giftService.updateGift(userId,giftId,langData,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/deleteGift",method=RequestMethod.POST)
	public SuccessVO deleteGift(final Long userId,final String ids,final String lang){

		AssertUtil.notBlank(ids, "Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		giftService.deleteByIds(ids,lang);
		return new SuccessVO();
	}
	
}