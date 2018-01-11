package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.MerchantAreaDTO;
import com.cherrypicks.tcc.cms.merchant.service.MerchantAreaService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.MerchantArea;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class MerchantAreaController extends BaseController<MerchantArea>{
	
	@Autowired
	private MerchantAreaService merchantAreaService;

	@Override
	@Autowired
	public void setBaseService(IBaseService<MerchantArea> merchantAreaService) {
		// TODO Auto-generated method stub
		super.setBaseService(merchantAreaService);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/getmerchantAreaList",method=RequestMethod.POST)
	public ResultVO getMerchantAreaList(Long userId, Long merchantId, String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		List<MerchantAreaDTO> merchantAreas = merchantAreaService.findByMerchantId(userId,merchantId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(merchantAreas);
		return result;
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/createMerchantArea",method=RequestMethod.POST)
	public ResultVO addMerchantArea(Long userId,Long merchantId,String langData,String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		MerchantArea merchantArea = merchantAreaService.createMerchantArea(userId,merchantId,langData,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(merchantArea);
		return result;
		
	}
	
	@RequestMapping(value="/updateMerchantArea",method=RequestMethod.POST)
	public SuccessVO updateMerchantArea(Long userId,Long merchantAreaId, String langData,String lang){
		
		AssertUtil.notNull(merchantAreaId, "Merchant area id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		merchantAreaService.updateMerchantArea(userId,merchantAreaId,langData,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/delMerchantArea",method=RequestMethod.POST)
	public SuccessVO deleteMerchantArea(Long userId,String ids,String lang){
		
		AssertUtil.notBlank(ids, "Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		merchantAreaService.delByIds(userId,ids,lang);
		
		return new SuccessVO();
	}
	
}
