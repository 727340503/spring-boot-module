package com.cherrypicks.tcc.cms.report.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cherrypicks.tcc.cms.report.service.util.ViewExcel;

public interface ReportService {

	HSSFWorkbook getCollectStampsReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception;

	HSSFWorkbook getRedemptionReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception;

	HSSFWorkbook getUserReport(final Long merchantId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception;

	HSSFWorkbook getReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, final String pickupEndTime, final Integer status, final ViewExcel viewExcel) throws Exception;

	HSSFWorkbook getCollectCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final Integer status, final ViewExcel viewExcel) throws Exception;

	HSSFWorkbook getRedeemCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final ViewExcel viewExcel) throws Exception;

	HSSFWorkbook getTransferStampsReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception;

	HSSFWorkbook getCampaignReport(final Long campaignId, final ViewExcel excel) throws Exception;

}
