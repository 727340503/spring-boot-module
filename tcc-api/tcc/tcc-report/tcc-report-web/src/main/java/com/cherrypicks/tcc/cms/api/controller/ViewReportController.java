package com.cherrypicks.tcc.cms.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.CampaignRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.util.ReportUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.exception.CampaignNotExistException;
import com.cherrypicks.tcc.cms.report.service.ViewReportService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.model.UserCoupon;
import com.cherrypicks.tcc.model.UserStampHistory;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.Json;

@RestController
public class ViewReportController{
	
	@Autowired
	private ViewReportService viewReportService;
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/viewCollectStampsReport", method = RequestMethod.POST)
	public PagingResultVo viewCollectStampsReport(final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final String sortField,
												final String sortType,final Integer startRow,final Integer maxRows, final String lang) throws Exception {

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(null != campaignId && null == merchantId){
			throw new IllegalArgumentException("Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);

		final List<Integer> types = new ArrayList<Integer>();
		types.add(UserStampHistory.UserStampHistoryType.COLLECT_STAMPS.toValue());
		types.add(UserStampHistory.UserStampHistoryType.SYSTEM_IN_STAMPS.toValue());
		types.add(UserStampHistory.UserStampHistoryType.SYSTEM_OUT_STAMPS.toValue());
		types.add(UserStampHistory.UserStampHistoryType.GRANT.toValue());

		return viewReportService.pagingFindUserStampHistory(userId, merchantId, campaignId, startTime, endTime, sortField, sortType, startRow, maxRows, null, Json.toJson(types),null);
	}

	@UserMerchanVerifyAnno
	@RequestMapping(value = "/viewRedemptionReport", method = RequestMethod.POST)
	public PagingResultVo viewRedemptionReport(final Long userId, final Long merchantId, final Long campaignId, final String startTime,final String endTime,final String sortField,
											final String sortType,final Integer startRow,final Integer maxRows,final String lang) throws Exception {

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(null != campaignId && null == merchantId){
			throw new IllegalArgumentException("Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);

		return viewReportService.pagingFindUserStampHistory(userId, merchantId, campaignId, startTime, endTime, sortField, sortType, startRow, maxRows, null, null, UserStampHistory.UserStampHistoryType.REDEEM.toValue());
	}

	@UserMerchanVerifyAnno
	@RequestMapping(value = "/viewTransferStampsReport", method = RequestMethod.POST)
	public PagingResultVo viewTransferStampsReport(final Long userId, final Long merchantId, final Long campaignId,final String startTime,final String endTime,final String sortField,final String sortType,final Integer startRow,final Integer maxRows,final String lang) throws Exception {

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(null != campaignId && null == merchantId){
			throw new IllegalArgumentException("Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);

		return viewReportService.pagingFindUserStampHistory(userId, merchantId, campaignId, startTime, endTime, sortField, sortType, startRow, maxRows, true, null, UserStampHistory.UserStampHistoryType.REDEEM.toValue());
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/viewCollectCouponReport", method = RequestMethod.POST)
	public PagingResultVo viewCollectCouponReport(final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String lang) throws Exception {

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(null != campaignId && null == merchantId){
			throw new IllegalArgumentException("Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);

		return viewReportService.pagingFindUserCouponReport(merchantId, campaignId, startTime, endTime, status, sortField, sortType, startRow, maxRows, false);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/viewRedeemedtCouponReport", method = RequestMethod.POST)
	public PagingResultVo viewRedeemedtCouponReport(final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String lang) throws Exception {

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(null != campaignId && null == merchantId){
			throw new IllegalArgumentException("Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);

		return viewReportService.pagingFindUserCouponReport(merchantId, campaignId, startTime, endTime, UserCoupon.UserCouponStatus.REDEEMED.toValue(), sortField, sortType, startRow, maxRows, true);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/viewUserReport", method = RequestMethod.POST)
	public PagingResultVo viewUserReport(final Long userId,final Long merchantId, final String startTime, final String endTime, final String sortField, final String sortType, final Integer startRow, final Integer maxRows, final String lang) throws Exception {
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);
		
		return viewReportService.pagingFindUserReport(merchantId, startTime, endTime, sortField, sortType, startRow, maxRows);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value="/viewUserReservationReport",method=RequestMethod.POST)
	public PagingResultVo viewUserReservationReport(final Long userId, final Long merchantId, final Long campaignId,final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, 
												final String pickupEndTime, final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		if(null != campaignId && null == merchantId){
			throw new IllegalArgumentException("Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		if(StringUtils.isNotBlank(reservationStartTime) && StringUtils.isNotBlank(reservationEndTime)){
			ReportUtil.checkReportSearchDatePeriod(reservationStartTime, reservationEndTime, lang);
		}else if(StringUtils.isNotBlank(pickupStartTime) && StringUtils.isNotBlank(pickupEndTime)){
			ReportUtil.checkReportSearchDatePeriod(pickupStartTime, pickupEndTime, lang);
		}else{
			throw new IllegalArgumentException("Time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		return viewReportService.pagingFindUserReservationReport(merchantId, campaignId, reservationStartTime, reservationEndTime, pickupStartTime, pickupEndTime, status, sortField, sortType, startRow, maxRows);
	}
	
	@RequestMapping(value="/viewCampaignReport",method=RequestMethod.POST)
	public ResultVO viewCampaignReport(final Long userId,final Long campaignId,final String lang){

		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final Campaign campaign = CampaignRequestUtil.findById(campaignId);
		if(null == campaign){
			throw new CampaignNotExistException();
		}

		AuthorizeRequestUtil.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);

		final ResultVO result = new ResultVO();

		result.setResult(viewReportService.getCampaignReport(campaignId));

		return result;
	}
	
}
