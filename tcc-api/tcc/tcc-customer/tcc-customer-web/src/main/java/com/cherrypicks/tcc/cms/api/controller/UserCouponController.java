package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.customer.service.UserCouponService;
import com.cherrypicks.tcc.cms.customer.service.UserService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.User;
import com.cherrypicks.tcc.model.UserCoupon;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.Json;

@RestController
public class UserCouponController extends BaseController<UserCoupon>{
	
	@Autowired
	private UserCouponService userCouponService;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<UserCoupon> userCouponService) {
		super.setBaseService(userCouponService);
	}
	
	@RequestMapping(value="/getUserCouponList",method=RequestMethod.POST)
	public PagingResultVo getUserCouponList(final Long userId, final Long customerId, final Integer status,final String name, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(customerId, "User id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("userId", customerId);
		criteriaMap.put("status", status);

		final User user = userService.findById(customerId);

		AuthorizeRequestUtil.checkUserMerchantPermission(userId, user.getMerchantId(), lang);

		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/addUserCoupon",method=RequestMethod.POST)
	public SuccessVO addUserCoupon(final Long userId,final Long customerId,final Long couponId, final Integer qty,final String remark, final String lang) {
		
		AssertUtil.notNull(customerId, "Customer id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(couponId, "Coupon id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(qty, "Qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(remark, "Remark "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(qty < 1) {
			throw new IllegalArgumentException("Qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		userCouponService.addUserCoupon(userId, customerId, couponId, qty, remark, lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/revokeUserCoupon",method=RequestMethod.POST)
	public SuccessVO revokeUserCoupon(final Long userId, final Long userCouponId,final String remark, final String lang) {
		
		AssertUtil.notNull(userCouponId, "User coupon id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		userCouponService.revokeUserCoupon(userId, userCouponId,remark, lang);
		
		return new SuccessVO();
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/exportUserCouponReport",method=RequestMethod.POST)
	public ResultVO exportUserCouponReport(final Long merchantId, final Long campaignId, final String startTime,
											final String endTime, final Integer status, final Boolean isRedeemCouponReport) {
		
		ResultVO result = new ResultVO();
		result.setResult(Json.toJson(userCouponService.findUserCouponReport(merchantId, campaignId, startTime, endTime, status, isRedeemCouponReport)));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getUserCouponCountByCampaignId",method=RequestMethod.POST)
	public ResultVO getUserCouponCountByCampaignId(final Long campaignId, final Integer status) {
		
		ResultVO result = new ResultVO();
		result.setResult(userCouponService.findTotalCountByCampaignId(campaignId, status));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/viewUserCouponReport",method=RequestMethod.POST)
	public ResultVO viewUserCouponReport(final Long merchantId,final Long campaignId,final String startTime,final String endTime,
			final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final Boolean isRedeemCouponReport) {
		
		final Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("campaignId", campaignId);
		criteriaMap.put("startTime", startTime);
		criteriaMap.put("endTime", endTime);
		criteriaMap.put("status", status);
		criteriaMap.put("isRedeemCouponReport", isRedeemCouponReport);
		
		ResultVO result = new ResultVO();
		result.setResult(userCouponService.pagingFindUserCoupon(criteriaMap, sortField, sortType, startRow, maxRows));
		return result;
	}
}
