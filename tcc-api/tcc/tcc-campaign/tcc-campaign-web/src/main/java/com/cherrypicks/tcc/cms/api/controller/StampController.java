package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.campaign.service.StampService;
import com.cherrypicks.tcc.cms.dto.StampDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.Stamp;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class StampController extends BaseController<Stamp>{
	
	@Autowired
	private StampService stampService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Stamp> stampService) {
		// TODO Auto-generated method stub
		super.setBaseService(stampService);
	}
	
	@RequestMapping(value="/getStampList",method=RequestMethod.POST)
	public ResultVO getStampList(final Long userId, final Long campaignId, final String lang){
		
		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		List<StampDetailDTO> stamps = stampService.findByCampaignId(userId,campaignId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(stamps);
		return result;
	}
	
	@RequestMapping(value="/updateStamp",method=RequestMethod.POST)
	public SuccessVO updateStamp(final Long userId,final Long campaignId, final String langData,final String lang){
		
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		stampService.updateStamp(userId,campaignId,langData,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/deleteStamp",method=RequestMethod.POST)
	public SuccessVO deleteStamp(final Long userId,final String stampIds,final Long campaignId,final String lang){
		
		AssertUtil.notBlank(stampIds, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		stampService.delByIds(userId,stampIds,campaignId,lang);
		
		return new SuccessVO();
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getStampListByStampCardId",method=RequestMethod.POST)
	public ResultVO getStampListByStampCardId(final Long stampCardId){
		
		AssertUtil.notNull(stampCardId, "Stamp card id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(stampService.findStampsByStampCardId(stampCardId));
		return result;
	}
	
}