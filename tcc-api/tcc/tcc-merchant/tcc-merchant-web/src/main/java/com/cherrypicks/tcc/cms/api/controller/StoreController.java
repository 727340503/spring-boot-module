package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.PrivateRequestVerifyAnno;
import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.StoreDetailDTO;
import com.cherrypicks.tcc.cms.merchant.service.StoreService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Store;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class StoreController extends BaseController<Store>{
	
	@Autowired
	private StoreService storeService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Store> storeService) {
		// TODO Auto-generated method stub
		super.setBaseService(storeService);
	}
	
	@RequestMapping(value="/getStoreList",method=RequestMethod.POST)
	public PagingResultVo getStoreList(final Long userId,Long merchantId, final String merchantName, final String name,final Integer status,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		
		//判断用户属于Platform admin
		SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			merchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("name", name);
		criteriaMap.put("merchantName", merchantName);
		criteriaMap.put("status", status);
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/getStoreDetail",method=RequestMethod.POST)
	public ResultVO getStoreDetail(final Long userId,final Long storeId,final String lang){
		
		AssertUtil.notNull(storeId, "Store id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		StoreDetailDTO storeDetail = storeService.findDetailById(userId,storeId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(storeDetail);
		return result;
	}

	@UserMerchanVerifyAnno
	@RequestMapping(value="/createStore",method=RequestMethod.POST)
	public SuccessVO addStore(final Long userId,final String externalStoreId,final Long merchantId,final Long merchantAreaId,final String phone,final Integer status,
						final String lat, final String lng, final String langData, final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(externalStoreId, "External store id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(lat, "Lat "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(lng, "Lng "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		storeService.createStore(userId,externalStoreId,merchantId,merchantAreaId,phone,status,lat,lng,langData,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/updateStore",method=RequestMethod.POST)
	public SuccessVO updateStore(final Long userId,final Long storeId,final Long merchantAreaId,final String externalStoreId,final String phone,final Integer status,
						final String lat, final String lng, final String langData, final String lang){
		
		AssertUtil.notNull(storeId, "Store id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		storeService.updateStore(userId,storeId,merchantAreaId,externalStoreId,phone,status,lat,lng,langData,lang);
		
		return new SuccessVO();
	}
	
	@PrivateRequestVerifyAnno
	@RequestMapping(value="/private/getStoreById",method=RequestMethod.POST)
	public ResultVO getStoreById(final Long storeId) {
		
		AssertUtil.notNull(storeId, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(storeService.findById(storeId));
		return result;
	}
	
}
