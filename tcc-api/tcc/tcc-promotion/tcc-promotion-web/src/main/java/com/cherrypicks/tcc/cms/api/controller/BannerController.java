package com.cherrypicks.tcc.cms.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.cherrypicks.tcc.cms.dto.BannerIDetailDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.promotion.service.BannerService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Banner;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class BannerController extends BaseController<Banner>{
	
	@Autowired
	private BannerService bannerService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Banner> bannerService) {
		// TODO Auto-generated method stub
		super.setBaseService(bannerService);
	}
	
	@RequestMapping(value="/getBannerList",method=RequestMethod.POST)
	public PagingResultVo getBannerList(final Long userId, final Long merchantId, final Integer status,final String name,final String sortField,final String sortType,final Integer startRow,final Integer maxRows,final String langCode, final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("status", status);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("name", name);
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			criteriaMap.put("merchantId", SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId));
		}
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/getHomePageBanerList",method=RequestMethod.POST)
	public ResultVO getHomePageBannerList(final Long userId,Long merchantId,final Integer status,final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		List<HomePageItemDTO> banners = bannerService.findHomePageBannerList(userId,merchantId,status,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(banners);
		return result;
	}
	
	@RequestMapping(value="/getBannerDetail",method=RequestMethod.POST)
	public ResultVO getBannerDetail(final Long userId,final Long bannerId,final String lang){
		
		AssertUtil.notNull(bannerId, "Banner id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		BannerIDetailDTO bannerDetail = bannerService.findDetailById(userId,bannerId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(bannerDetail);
		return result;
	}
	
	@RequestMapping(value="/createBanner",method=RequestMethod.POST)
	public ResultVO addBanner(final Long userId,final Long merchantId,final String webUrl,final Integer inappOpen,final Date startTime,final Date endTime,final String langData,final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(inappOpen, "In app "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(startTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("End time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		Banner banner = bannerService.createBanner(userId,merchantId,webUrl,inappOpen,startTime,endTime,langData,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(banner);
		return result;
	}
	
	@RequestMapping(value="/updateBanner",method=RequestMethod.POST)
	public SuccessVO updateBanner(final Long userId,final Long bannerId,final Integer status,final String webUrl,final Integer inappOpen,final Date startTime,final Date endTime,final String langData,final String lang){
		
		AssertUtil.notNull(bannerId, "Banner id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(null != startTime && null != endTime){
			if(startTime.getTime() > endTime.getTime()){
				throw new IllegalArgumentException("End time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}
		
		bannerService.updateBanner(userId,bannerId,status,webUrl,inappOpen,startTime,endTime,langData,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/deleteBanner",method=RequestMethod.POST)
	public SuccessVO delBanner(final Long userId,final String ids,final String lang){
		
		AssertUtil.notBlank(ids, "Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		Long userMerchantId = null;
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			userMerchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		bannerService.deleteBanner(userId,ids,userMerchantId,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/updateBannerSortOrder",method=RequestMethod.POST)
	public SuccessVO updateBannerSortOrder(final Long userId,final String sortOrderData,final String lang){
		
		AssertUtil.notBlank(sortOrderData, "Sort order data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		Long userMerchantId = null;
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			userMerchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		bannerService.updateBannerSortOrder(userId,sortOrderData,userMerchantId,lang);
		
		return new SuccessVO();
	}
	
}
