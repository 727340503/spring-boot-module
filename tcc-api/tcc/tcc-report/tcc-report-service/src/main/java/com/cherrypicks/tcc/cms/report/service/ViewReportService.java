package com.cherrypicks.tcc.cms.report.service;

import com.cherrypicks.tcc.cms.dto.CampaignReportDTO;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;

public interface ViewReportService {

	PagingResultVo pagingFindUserStampHistory(final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final String sortField,final String sortType,
							final Integer startRow,final Integer maxRows, final Boolean isTransfer, final String types,final Integer type);

	PagingResultVo pagingFindUserCouponReport(final Long merchantId,final Long campaignId,final String startTime,final String endTime,final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final Boolean isRedeemCouponReport);

	PagingResultVo pagingFindUserReport(final Long merchantId, final String startTime, final String endTime, final String sortField, final String sortType, final Integer startRow, final Integer maxRows);

	PagingResultVo pagingFindUserReservationReport(final Long merchantId, final Long campaignId,final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, 
			final String pickupEndTime, final Integer status, final String sortField,final String sortType,final Integer startRow,final Integer maxRows);

	CampaignReportDTO getCampaignReport(final Long campaignId);


}
