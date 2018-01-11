package com.cherrypicks.tcc.cms.api.http.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.cms.dto.CampaignReportDTO;
import com.cherrypicks.tcc.cms.dto.UserCouponReportDTO;
import com.cherrypicks.tcc.cms.dto.UserReportDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationReportDTO;
import com.cherrypicks.tcc.cms.dto.UserStampHistoryReportDTO;
import com.cherrypicks.tcc.cms.exception.CallOtherModuleApiErrorException;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.util.CMSAPIResult;
import com.cherrypicks.tcc.util.CmsConstants;
import com.cherrypicks.tcc.util.HttpClientUtil;
import com.cherrypicks.tcc.util.Json;

public class ReportRequestUtil{

	private final static Logger log = LoggerFactory.getLogger(ReportRequestUtil.class);
	
	
	private final static String EXPORT_USER_REPORT = CmsConstants.EXPORT_USER_REPORT;
	private final static String EXPORT_USER_STAMP_HISTORY_REPORT = CmsConstants.EXPORT_USER_STAMP_HISTORY_REPORT;
	private final static String EXPORT_USER_RESERVATION_REPORT = CmsConstants.EXPORT_USER_RESERVATION_REPORT;
	private final static String EXPORT_USER_COUPON_REPORT = CmsConstants.EXPORT_USER_COUPON_REPORT;
	private final static String EXPORT_CAMPAIGN_REPORT = CmsConstants.EXPORT_CAMPAIGN_REPORT;
	private final static String VIEW_USER_STAMP_HISTORY_REPORT = CmsConstants.VIEW_USER_STAMP_HISTORY_REPORT;
	private final static String VIEW_USER_COUPON_REPORT = CmsConstants.VIEW_USER_COUPON_REPORT;
	private final static String VIEW_USER_REPORT = CmsConstants.VIEW_USER_REPORT;
	private final static String VIEW_USER_RESERVATION_REPORT = CmsConstants.VIEW_USER_RESERVATION_REPORT;

	public static List<UserReportDTO> findUserReport(final Long merchantId, final String startTime, final String endTime) {
		List<UserReportDTO> result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		
		String json = HttpClientUtil.getInstance().sendHttpPost(EXPORT_USER_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.toListObject(resultMap.get("result").toString(), UserReportDTO.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static List<UserStampHistoryReportDTO> findUserStampHistoryReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, 
																			final List<Integer> userStampHistoryTypes) {
		List<UserStampHistoryReportDTO> result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		params.put("campaignId", String.valueOf(campaignId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("userStampHistoryTypes", Json.toJson(userStampHistoryTypes));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(EXPORT_USER_STAMP_HISTORY_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.toListObject(resultMap.get("result").toString(), UserStampHistoryReportDTO.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static List<UserReservationReportDTO> findUserReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime, final String reservationEndTime, 
																	final String pickupStartTime, final String pickupEndTime, final Integer status) {
		List<UserReservationReportDTO> result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		params.put("campaignId", String.valueOf(campaignId));
		
		if(null != status) {
			params.put("status", String.valueOf(status));
		}
		if(StringUtils.isNotBlank(reservationStartTime)) {
			params.put("reservationStartTime", reservationStartTime);
		}
		if(StringUtils.isNotBlank(reservationEndTime)) {
			params.put("reservationEndTime", reservationEndTime);
		}
		if(StringUtils.isNotBlank(pickupStartTime)) {
			params.put("pickupStartTime", pickupStartTime);
		}
		if(StringUtils.isNotBlank(pickupEndTime)) {
			params.put("pickupEndTime", pickupEndTime);
		}
		
		String json = HttpClientUtil.getInstance().sendHttpPost(EXPORT_USER_RESERVATION_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.toListObject(resultMap.get("result").toString(), UserReservationReportDTO.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static List<UserCouponReportDTO> findUserCouponReport(final Long merchantId, final Long campaignId, final String startTime,
			final String endTime, final Integer status, final boolean isRedeemCouponReport) {
		List<UserCouponReportDTO> result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		params.put("campaignId", String.valueOf(campaignId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("isRedeemCouponReport", String.valueOf(isRedeemCouponReport));
		if(null !=status) {
			params.put("status", String.valueOf(status));
		}
		String json = HttpClientUtil.getInstance().sendHttpPost(EXPORT_USER_COUPON_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.toListObject(resultMap.get("result").toString(), UserCouponReportDTO.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static CampaignReportDTO getCampaignReport(final Long campaignId) {
		CampaignReportDTO result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("campaignId", String.valueOf(campaignId));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(EXPORT_CAMPAIGN_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), CampaignReportDTO.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static PagingResultVo pagingFindUserStampHistory(final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final String sortField,final String sortType,
										final Integer startRow,final Integer maxRows, final Boolean isTransfer, final String types,final Integer type) {
		PagingResultVo result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		params.put("merchantId", String.valueOf(merchantId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("sortField", sortField);
		params.put("sortType", sortType);
		params.put("startRow", String.valueOf(startRow));
		params.put("maxRows", String.valueOf(maxRows));
		
		if(null != isTransfer) {
			params.put("isTransfer", String.valueOf(isTransfer));
		}
		
		if(null != types) {
			params.put("types", String.valueOf(types));
		}
		
		if(null != type) {
			params.put("type", String.valueOf(type));
		}
		
		String json = HttpClientUtil.getInstance().sendHttpPost(VIEW_USER_STAMP_HISTORY_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), PagingResultVo.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static PagingResultVo pagingFindUserCouponReport(final Long merchantId,final Long campaignId,final String startTime,final String endTime,final Integer status, 
			final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final Boolean isRedeemCouponReport) {
		PagingResultVo result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		params.put("campaignId", String.valueOf(campaignId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("sortField", sortField);
		params.put("sortType", sortType);
		params.put("startRow", String.valueOf(startRow));
		params.put("maxRows", String.valueOf(maxRows));
		params.put("isRedeemCouponReport", String.valueOf(isRedeemCouponReport));
		
		if(null != status) {
			params.put("status", String.valueOf(status));
		}
		
		String json = HttpClientUtil.getInstance().sendHttpPost(VIEW_USER_COUPON_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), PagingResultVo.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static PagingResultVo pagingFindUserReport(final Long merchantId, final String startTime, final String endTime, final String sortField, final String sortType, final Integer startRow, final Integer maxRows) {
		PagingResultVo result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("sortField", sortField);
		params.put("sortType", sortType);
		params.put("startRow", String.valueOf(startRow));
		params.put("maxRows", String.valueOf(maxRows));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(VIEW_USER_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), PagingResultVo.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}

	public static PagingResultVo pagingFindUserReservationReport(final Long merchantId, final Long campaignId,final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, 
			final String pickupEndTime, final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows) {
		
		PagingResultVo result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantId", String.valueOf(merchantId));
		params.put("reservationStartTime", reservationStartTime);
		params.put("reservationEndTime", reservationEndTime);
		params.put("pickupStartTime", pickupStartTime);
		params.put("pickupEndTime", pickupEndTime);
		params.put("sortField", sortField);
		params.put("sortType", sortType);
		params.put("startRow", String.valueOf(startRow));
		params.put("maxRows", String.valueOf(maxRows));
		
		String json = HttpClientUtil.getInstance().sendHttpPost(VIEW_USER_RESERVATION_REPORT, params);
		Map<String, Object> resultMap = Json.toNotNullMap(json);
		
		if (null != resultMap) {
			log.info("-------------resultMap--------------" + resultMap);
			if (null != resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME) && resultMap.get(CMSAPIResult.ERROR_CODE_PARAMETER_NAME).equals(0)) {
				result = Json.convert(resultMap.get("result"), PagingResultVo.class);
			} else {
				throw new CallOtherModuleApiErrorException();
			}
		}
		
		return result;
	}
}
