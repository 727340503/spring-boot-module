package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.MerchantPermissionAuth;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.PushNotifDTO;
import com.cherrypicks.tcc.cms.push.service.PushNotifService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.PushNotif;
import com.cherrypicks.tcc.model.UserReservation;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.Json;
import com.cherrypicks.tcc.util.ListUtil;

@RestController
public class PushNotifController extends BaseController<PushNotif>{
	
	@Autowired
	private PushNotifService pushNotifService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<PushNotif> pushNotifService) {
		// TODO Auto-generated method stub
		super.setBaseService(pushNotifService);
	}
	
	
	@MerchantPermissionAuth
	@RequestMapping(value="/getPushNotifList",method=RequestMethod.POST)
	public PagingResultVo getPushNotifList(final Long userId,final Long merchantId,final Integer type,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("type", type);
		criteriaMap.put("merchantId", merchantId);
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/getPushNotifDetail",method=RequestMethod.POST)
	public ResultVO getPushNotif(final Long userId,final Long pushNotifId,final String lang){
		
		AssertUtil.notNull(pushNotifId, "Push notif id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		PushNotifDTO pushNotifDetail = pushNotifService.findDetailById(userId,pushNotifId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(pushNotifDetail);
		return result;
	}
	
	@MerchantPermissionAuth
	@RequestMapping(value="/createPushNotif",method=RequestMethod.POST)
	public ResultVO addPushNotif(final Long userId,final Long merchantId,final Integer pageRedirectType,final String langData,final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(pageRedirectType, "Page redirect type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		PushNotif pushNotif = pushNotifService.createPushNotif(userId,merchantId,pageRedirectType,langData,lang);
		if(pageRedirectType.intValue() != PushNotif.PageRedirectType.PROMOTION.getCode() && pageRedirectType.intValue() != PushNotif.PageRedirectType.STAMP_ACTIVE_CAMPAIGN_LIST.getCode()){
			throw new IllegalArgumentException("Page redirect type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		ResultVO result = new ResultVO();
		result.setResult(pushNotif);
		return result;
	}
	
	@RequestMapping(value="/updatePushNotif",method=RequestMethod.POST)
	public SuccessVO updatePushNotif(final Long userId,final Long pushNotifId, final Integer pageRedirectType,final String langData,final String lang){
		
		AssertUtil.notNull(pushNotifId, "Push notif id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		if(null != pageRedirectType){
			if(pageRedirectType.intValue() != PushNotif.PageRedirectType.PROMOTION.getCode() && pageRedirectType.intValue() != PushNotif.PageRedirectType.STAMP_ACTIVE_CAMPAIGN_LIST.getCode()){
				throw new IllegalArgumentException("Page redirect type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}
		
		pushNotifService.updatePushNotif(userId,pushNotifId,pageRedirectType,langData,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/publishPushNotif",method=RequestMethod.POST)
	public SuccessVO publishPushNotif(final Long userId,final Long pushNotifId,final String lang) throws Exception{
	
		AssertUtil.notNull(pushNotifId, "Push notif id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		pushNotifService.sendPushNotif(userId,pushNotifId,lang);
		return new SuccessVO();
	}
	
	@RequestMapping(value="/private/sendUserReservationPushMsg",method=RequestMethod.POST)
	public SuccessVO sendUserReservationPushMsg(final String userReservations) throws Exception{
	
		AssertUtil.notBlank(userReservations, "User reservation list "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		List<UserReservation> lists = Json.toListObject(userReservations, UserReservation.class);
		if(ListUtil.isEmpty(lists)) {
			throw new IllegalArgumentException("User reservation list "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		pushNotifService.sendUserReservationPushMsg(lists);
		
		return new SuccessVO();
	}
	
}
