package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.MerchantPermissionAuth;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.HomePageDetailDTO;
import com.cherrypicks.tcc.cms.promotion.service.HomePageService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.HomePage;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class HomePageController extends BaseController<HomePage>{
	
	@Autowired
	private HomePageService homePageService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<HomePage> homePageService) {
		// TODO Auto-generated method stub
		super.setBaseService(homePageService);
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/createchantHomePage",method=RequestMethod.POST)
	public ResultVO createchantHomePage(final Long userId,final Long merchantId,final String homePageFrame,final String homePageData,final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(homePageFrame, "Home page frame "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(homePageData, "Home page data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		homePageService.createHomePage(userId,merchantId,homePageFrame,homePageData,lang);
		
		ResultVO result = new ResultVO();
		return result;
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/getMerchantHomePage",method=RequestMethod.POST)
	public ResultVO getMerchantHomePage(final Long userId,final Long merchantId,final Integer status, final String langCode,final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(status, "Status "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
	

		ResultVO result = new ResultVO();
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Merchant merchant = MerchantRequestUtil.findById(merchantId);
		if(status.intValue() == HomePage.EditStatus.ON_LINE.getCode()){
			if(StringUtils.isNotBlank(merchant.getHomePage())){
				List<HomePageDetailDTO> homePage = homePageService.findMerchantHomePageByStatus(userId,merchantId,langCode,HomePage.EditStatus.ON_LINE.getCode(), lang);
				resultMap.put("homePageFrame", merchant.getHomePage());
				resultMap.put("homePageData", homePage);
			}else{
				resultMap.put("homePageFrame", null);
				resultMap.put("homePageData", null);
			}
		}else{
			if(StringUtils.isNotBlank(merchant.getHomePageDraft())){
				List<HomePageDetailDTO> homePage = homePageService.findMerchantHomePageByStatus(userId,merchantId,langCode,HomePage.EditStatus.DRAFT.getCode(), lang);
				resultMap.put("homePageFrame", merchant.getHomePageDraft());
				resultMap.put("homePageData", homePage);
			}else{
				resultMap.put("homePageFrame", null);
				resultMap.put("homePageData", null);
			}
		}
		result.setResult(resultMap);
		return result;
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/publishMerchantHomePage",method=RequestMethod.POST)
	public SuccessVO publishMerchantHomePage(final Long userId,final Long merchantId,final String lang){
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		homePageService.publishMerchantHomePage(userId,merchantId,lang);
		
		return new SuccessVO();
	}
}
