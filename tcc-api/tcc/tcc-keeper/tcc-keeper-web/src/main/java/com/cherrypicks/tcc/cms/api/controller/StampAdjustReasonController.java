package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.MerchantPermissionAuth;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.StampAdjustReasonDetailDTO;
import com.cherrypicks.tcc.cms.keeper.service.StampAdjustReasonService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.StampAdjustReason;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class StampAdjustReasonController extends BaseController<StampAdjustReason>{
	
//	@Autowired
//	private IAuthorizeService authorizeService;
	
	@Autowired
	private StampAdjustReasonService stampAdjustReasonService;
	

	@Override
	@Autowired
	public void setBaseService(IBaseService<StampAdjustReason> stampAdjustReasonService) {
		super.setBaseService(stampAdjustReasonService);
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/getStampAdjustReasonList",method=RequestMethod.POST)
	public PagingResultVo getStampAdjustReasonList(final Long userId, final Long merchantId, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("merchantId", merchantId);

		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/addStampAdjustReason",method=RequestMethod.POST)
	public ResultVO addStampAdjustReason(final Long userId, final Long merchantId, final String langData, final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		StampAdjustReason stampAdjustReason = stampAdjustReasonService.createStampAdjustReason(userId,merchantId,langData,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(stampAdjustReason);
		return result;
	}
	
	@RequestMapping(value="/updateStampAdjustReason",method=RequestMethod.POST)
	public SuccessVO updateStampAdjustReason(final Long userId, final Long id, final String langData, final String lang){
		
		AssertUtil.notNull(id, "Id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		stampAdjustReasonService.updateStampAdjustReason(userId,id,langData,lang);
	
		return new SuccessVO();
	}
	
	@RequestMapping(value="/getStampAdjustReasonDetail",method=RequestMethod.POST)
	public ResultVO getStampAdjustReasonDetail(final Long userId, final Long id, final String lang){
		
		AssertUtil.notNull(id, "Id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		StampAdjustReasonDetailDTO reasonDetail = stampAdjustReasonService.getStampAdjustReasonDetail(userId,id,lang);
	
		ResultVO result = new ResultVO();
		result.setResult(reasonDetail);
		return result;
	}
	
	@RequestMapping(value="/deleteStampAdjustReason",method=RequestMethod.POST)
	public SuccessVO deleteStampAdjustReason(final Long userId, final String ids, final String lang){
		
		AssertUtil.notBlank(ids, "Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		stampAdjustReasonService.deleteStampAdjustReason(userId,ids,lang);
	
		return new SuccessVO();
	}
	
}
