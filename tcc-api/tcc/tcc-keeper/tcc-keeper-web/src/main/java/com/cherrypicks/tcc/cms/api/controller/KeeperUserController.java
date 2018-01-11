package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.KeeperUserDetailDTO;
import com.cherrypicks.tcc.cms.keeper.service.KeeperUserService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.KeeperUser;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class KeeperUserController extends BaseController<KeeperUser>{

	@Autowired
	private KeeperUserService keeperUserService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<KeeperUser> keeperUserService) {
		super.setBaseService(keeperUserService);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/getKeeperUserList",method=RequestMethod.POST)
	public PagingResultVo getKeeperUserList(final Long userId, final String userName, final Long merchantId, final Long storeId, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(null != storeId){
			AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("userName", userName);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("storeId", storeId);

		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/addKeeperUser",method=RequestMethod.POST)
	public ResultVO addKeeperUser(final Long userId, final Long merchantId, final Long storeId, final String userName,final String email, final String mobile,
									final String staffId,final String password,final Integer status,final String lang){
		
		AssertUtil.notNull(storeId, "Store "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(userName, "User name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(password, "Password "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(staffId, "Staff id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		KeeperUser keeperUser = keeperUserService.createKeeperUser(userId, merchantId, storeId, userName, password, email, mobile, staffId, status, lang);
		
		ResultVO result = new ResultVO();
		result.setResult(keeperUser);
		return result;
	}
	
	@RequestMapping(value="/updateKeeperUser",method=RequestMethod.POST)
	public SuccessVO updateKeeperUser(final Long userId, final Long id, final Long storeId, final String userName,final String email, final String mobile,
									final String staffId,final String password,final Integer status,final String lang){
		
		AssertUtil.notNull(storeId, "Store "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(id, "Keeper user id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
	
		keeperUserService.updateKeeperUser(userId, id, storeId, userName, email, mobile, staffId, password, status, lang);
	
		return new SuccessVO();
	}
	
	@RequestMapping(value="/getKeeperUserDetail",method=RequestMethod.POST)
	public ResultVO getKeeperUserDetail(final Long userId, final Long id, final String lang){
		
		AssertUtil.notNull(id, "Keeper user id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		KeeperUserDetailDTO keeperUserDetail = keeperUserService.findKeeperUserDetail(userId, id, lang);
		
		ResultVO result = new ResultVO();
		result.setResult(keeperUserDetail);
		return result;
	}
}
