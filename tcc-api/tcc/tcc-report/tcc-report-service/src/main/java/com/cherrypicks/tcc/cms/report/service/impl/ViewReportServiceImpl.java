package com.cherrypicks.tcc.cms.report.service.impl;

import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.api.http.util.ReportRequestUtil;
import com.cherrypicks.tcc.cms.dto.CampaignReportDTO;
import com.cherrypicks.tcc.cms.report.service.ViewReportService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;

@Service
public class ViewReportServiceImpl implements ViewReportService{

	@Override
	public PagingResultVo pagingFindUserStampHistory(final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final String sortField,final String sortType,
			final Integer startRow,final Integer maxRows, final Boolean isTransfer, final String types,final Integer type) {
		
		return ReportRequestUtil.pagingFindUserStampHistory(userId, merchantId, campaignId, startTime, endTime, sortField, sortType, startRow, maxRows, isTransfer, types, type);
	}

	@Override
	public PagingResultVo pagingFindUserCouponReport(final Long merchantId,final Long campaignId,final String startTime,final String endTime,final Integer status, 
														final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final Boolean isRedeemCouponReport) {
		return ReportRequestUtil.pagingFindUserCouponReport(merchantId, campaignId, startTime, endTime, status, sortField, sortType, startRow, maxRows, isRedeemCouponReport);
	}

	@Override
	public PagingResultVo pagingFindUserReport(final Long merchantId, final String startTime, final String endTime, final String sortField, final String sortType, final Integer startRow, final Integer maxRows) {
		return ReportRequestUtil.pagingFindUserReport(merchantId, startTime, endTime, sortField, sortType, startRow, maxRows);
	}

	@Override
	public PagingResultVo pagingFindUserReservationReport(final Long merchantId, final Long campaignId,final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, 
			final String pickupEndTime, final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows) {
		
		return ReportRequestUtil.pagingFindUserReservationReport(merchantId, campaignId, reservationStartTime, reservationEndTime, pickupStartTime, pickupEndTime, status, sortField, sortType, startRow, maxRows);
	}

	@Override
	public CampaignReportDTO getCampaignReport(Long campaignId) {
		return ReportRequestUtil.getCampaignReport(campaignId);
	}

}
