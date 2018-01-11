package com.cherrypicks.tcc.cms.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.CampaignRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.util.ReportUtil;
import com.cherrypicks.tcc.cms.exception.CampaignNotExistException;
import com.cherrypicks.tcc.cms.report.service.ReportService;
import com.cherrypicks.tcc.cms.report.service.util.ViewExcel;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.Constants;
import com.cherrypicks.tcc.util.I18nUtil;

@Controller
public class ReportController{
	
	@Autowired
	private ReportService reportService;
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/exportCollectStampsReport", method = RequestMethod.GET)
	public void exportCollectStampsReport(final HttpServletRequest request, final HttpServletResponse response,final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final String lang) throws Exception {

		AssertUtil.notNull(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);
		
		final String fileName = getReportFieName(merchantId, campaignId, startTime, endTime, Constants.COLLECT_STAMPS_REPORT);

		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getCollectStampsReport(merchantId,campaignId,startTime,endTime,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}

	@UserMerchanVerifyAnno
	@RequestMapping(value = "/exportRedemptionReport", method = RequestMethod.GET)
	public void exportRedemptionReport(final HttpServletRequest request, final HttpServletResponse response,final Long userId, final Long merchantId, final Long campaignId, final String startTime,final String endTime,final String lang) throws Exception {

		AssertUtil.notNull(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);
		
		final String fileName = getReportFieName(merchantId, campaignId, startTime, endTime, Constants.REDEMPTION_REPORT);

		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getRedemptionReport(merchantId,campaignId,startTime,endTime,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/exportReservationReport", method = RequestMethod.GET)
	public void exportReservationReport(final HttpServletRequest request, final HttpServletResponse response, final Long userId, final Long merchantId, final Long campaignId,
								final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, final String pickupEndTime, final Integer status, final String lang) throws Exception {

		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		String fileName = null;

		if(StringUtils.isNotBlank(reservationStartTime) && StringUtils.isNotBlank(reservationEndTime)){
			ReportUtil.checkReportSearchDatePeriod(reservationStartTime, reservationEndTime, lang);
			
			fileName = getReportFieName(merchantId, campaignId, reservationStartTime, reservationEndTime, Constants.RESERVATION_REPORT);
		}else if(StringUtils.isNotBlank(pickupStartTime) && StringUtils.isNotBlank(pickupEndTime)){
			ReportUtil.checkReportSearchDatePeriod(pickupStartTime, pickupEndTime, lang);
			
			fileName = getReportFieName(merchantId, campaignId, pickupStartTime, pickupEndTime, Constants.RESERVATION_REPORT);
		}else{
			throw new IllegalArgumentException("Time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getReservationReport(merchantId,campaignId,reservationStartTime,reservationEndTime,pickupStartTime,pickupEndTime,status,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/exportTransferStampsReport", method = RequestMethod.GET)
	public void exportTransferStampsReport(final HttpServletRequest request, final HttpServletResponse response,final Long userId, final Long merchantId, final Long campaignId,final String startTime,final String endTime,final String lang) throws Exception {

		AssertUtil.notNull(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);
		
		final String fileName = getReportFieName(merchantId, campaignId, startTime, endTime, Constants.TRANSFER_STAMPS_REPORT);

		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getTransferStampsReport(merchantId,campaignId,startTime,endTime,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/exportCollectCouponReport", method = RequestMethod.GET)
	public void exportCollectCouponReport(final HttpServletRequest request, final HttpServletResponse response,final Long userId, final Long merchantId, final Long campaignId,final Integer status, final String startTime,final String endTime,final String lang) throws Exception {

		AssertUtil.notNull(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(campaignId, "Campaign id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		final String fileName = getReportFieName(merchantId, campaignId, startTime, endTime, Constants.COLLECT_COUPON_REPORT);

		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getCollectCouponReport(merchantId,campaignId,startTime,endTime, status,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/exportRedeemCouponReport", method = RequestMethod.GET)
	public void exportRedeemCouponReport(final HttpServletRequest request, final HttpServletResponse response,final Long userId, final Long merchantId, final Long campaignId,final String startTime,final String endTime,final String lang) throws Exception {

		AssertUtil.notNull(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(campaignId, "Campaign id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		final String fileName = getReportFieName(merchantId, campaignId, startTime, endTime, Constants.REDEEM_COUPON_REPORT);

		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getRedeemCouponReport(merchantId,campaignId,startTime,endTime,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}
	
	@UserMerchanVerifyAnno
	@RequestMapping(value = "/exportUserReport", method = RequestMethod.GET)
	public void exportUserReport(final HttpServletRequest request, final HttpServletResponse response,final Long userId,final Long merchantId,final String startTime,final String endTime,final String lang) throws Exception {
		
		AssertUtil.notNull(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		ReportUtil.checkReportSearchDatePeriod(startTime, endTime, lang);
		
		final String fileName = getReportFieName(merchantId,null,startTime,endTime,Constants.USER_REPORT);
				
		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getUserReport(merchantId,startTime,endTime,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}
	
	@RequestMapping(value = "/exportCampaignReport", method = RequestMethod.GET)
	public void exportCampaignReport(final HttpServletRequest request, final HttpServletResponse response,final Long userId,final Long campaignId,final String lang) throws Exception {

		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		final Campaign campaign = CampaignRequestUtil.findById(campaignId);
		if(null == campaign){
			throw new CampaignNotExistException();
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);
		
		final String fileName = getReportFieName(campaign.getMerchantId(), campaignId,null,null,Constants.CAMPAIGN_REPORT);
		
		ViewExcel excel = new ViewExcel();

		final Map<String, Object> obj = null;
		final HSSFWorkbook workbook = reportService.getCampaignReport(campaignId,excel);

		request.setAttribute("xlsFileName", fileName + ".xls");
		excel.buildExcelDocument(obj, workbook, request, response);
	}
	
	private String getReportFieName(final Long merchantId, final Long campaignId, final String startTime, final String endTime,final String reportFileName) {
		final String merchantDefaultName = MerchantRequestUtil.findMerchantDefaultName(merchantId);
		
		String fileName = "";
		
		if(reportFileName.equals(Constants.CAMPAIGN_REPORT)){
			final String campaignFefaultName = CampaignRequestUtil.findCampaignDefaultName(campaignId);
			fileName = String.format(reportFileName, campaignFefaultName,merchantDefaultName);
		}else if(reportFileName.equals(Constants.USER_REPORT)){
			final String formatStartTime = startTime.replaceAll("-","");
			final String formatEndTime = endTime.replaceAll("-","");
			fileName = String.format(reportFileName, merchantDefaultName,formatStartTime,formatEndTime);
		}else{
			final String campaignFefaultName = CampaignRequestUtil.findCampaignDefaultName(campaignId);
			final String formatStartTime = startTime.replaceAll("-","");
			final String formatEndTime = endTime.replaceAll("-","");
			fileName = String.format(reportFileName, campaignFefaultName,formatStartTime,formatEndTime);
		}
		
		return replaceSpecial(fileName);
	}
	
	private String replaceSpecial(String fileName){
		if (StringUtils.isNotBlank(fileName)) {  
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|","<",">","/",":","\"",",","，"};  
			for (String key : fbsArr) {  
				if (fileName.contains(key)) {  
	            	fileName = fileName.replace(key, " ");  
	            }  
	        }  
	    }  
	    return fileName;  
	}
	
}
