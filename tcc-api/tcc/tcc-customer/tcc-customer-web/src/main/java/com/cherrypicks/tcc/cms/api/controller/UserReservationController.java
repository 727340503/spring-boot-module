package com.cherrypicks.tcc.cms.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.customer.service.UserReservationService;
import com.cherrypicks.tcc.cms.dto.UserReservationDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.model.UserReservation;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.Json;

@RestController
public class UserReservationController extends BaseController<UserReservation>{
	
	@Autowired
	private UserReservationService userReservationService;

	@Override
	@Autowired
	public void setBaseService(IBaseService<UserReservation> userReservationService) {
		// TODO Auto-generated method stub
		super.setBaseService(userReservationService);
	}
	
	@RequestMapping(value="/getUserReservationList",method=RequestMethod.POST)
	public PagingResultVo getUserReservationList(final Long userId, Long merchantId, final Long campaignId,final Long giftId,final Integer status,final String userName,final Long customerId,final String reservationCode,
										final String reservationStartDate,final String reservationEndDate,final String storeName,	
										final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		
		final SystemRole userRole = SystemRoleRequestUtil.findByUserId(userId);
		if(userRole.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			merchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		criteriaMap.put("status", status);
		criteriaMap.put("userName", userName);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("campaignId", campaignId);
		criteriaMap.put("giftId", giftId);
		criteriaMap.put("customerId", customerId);
		criteriaMap.put("reservationCode", reservationCode);
		criteriaMap.put("reservationStartDate", reservationStartDate);
		criteriaMap.put("reservationEndDate", reservationEndDate);
		criteriaMap.put("storeName", storeName);
		
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/getUserReservationDetail",method=RequestMethod.POST)
	public ResultVO getUserReservation(final HttpServletRequest request,final Long userId,final Long userReservationId,final String lang){
		
		AssertUtil.notNull(userReservationId, "Id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		UserReservationDetailDTO userReservation = userReservationService.findDetailById(userId,userReservationId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(userReservation);
		return result;
	}
	
	@RequestMapping(value="/updateUserReservationStatus",method=RequestMethod.POST)
	public SuccessVO updateUserReservationStatus(final Long userId,final Long merchantId,final String ids,final Date reservationExpiredTime,final String lang) throws Exception{
		
		AssertUtil.notBlank(ids, "Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(reservationExpiredTime, "Reservation time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		userReservationService.updateUserReservationToAvailable(userId,merchantId,ids,reservationExpiredTime,lang);
		
		return new SuccessVO();
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getUserReservationPushNotif",method=RequestMethod.POST)
	public ResultVO getUserReservationPushNotif(final Long userReservationId) {
		
		AssertUtil.notNull(userReservationId, "User reservation id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ResultVO result = new ResultVO();
		result.setResult(userReservationService.finduserReservationPushNotifInfo(userReservationId));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/exportUserReservationReport",method=RequestMethod.POST)
	public ResultVO exportUserReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime, final String reservationEndTime, 
			final String pickupStartTime, final String pickupEndTime, final Integer status) {
		
		ResultVO result = new ResultVO();
		result.setResult(Json.toJson(userReservationService.findUserReservationReport(merchantId, campaignId, reservationStartTime, reservationEndTime, pickupStartTime, pickupEndTime, status)));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getCampaignReservationCount",method=RequestMethod.POST)
	public ResultVO getCampaignReservationCount(final Long campaignId) {
		
		ResultVO result = new ResultVO();
		result.setResult(userReservationService.findCampaignReservationCount(campaignId));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getViewUserReservationReportData",method=RequestMethod.POST)
	public ResultVO getViewUserReservationReportData(final Long merchantId, final Long campaignId,final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, 
			final String pickupEndTime, final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows) {
		
		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("campaignId", campaignId);
		criteriaMap.put("reservationStartTime", reservationStartTime);
		criteriaMap.put("reservationEndTime", reservationEndTime);
		criteriaMap.put("pickupStartTime", pickupStartTime);
		criteriaMap.put("pickupEndTime", pickupEndTime);
		criteriaMap.put("status", status);
		
		ResultVO result = new ResultVO();
		result.setResult(userReservationService.pagingFindUserReservationReport(criteriaMap, sortField, sortType, startRow, maxRows));
		return result;
	}
	
}
