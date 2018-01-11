package com.cherrypicks.tcc.cms.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.api.http.util.ReportRequestUtil;
import com.cherrypicks.tcc.cms.dto.CampaignReportDTO;
import com.cherrypicks.tcc.cms.dto.ExcelElement;
import com.cherrypicks.tcc.cms.dto.UserCouponReportDTO;
import com.cherrypicks.tcc.cms.dto.UserReportDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationReportDTO;
import com.cherrypicks.tcc.cms.dto.UserStampHistoryReportDTO;
import com.cherrypicks.tcc.cms.report.service.ReportService;
import com.cherrypicks.tcc.cms.report.service.util.ViewExcel;
import com.cherrypicks.tcc.model.UserCoupon;
import com.cherrypicks.tcc.model.UserStampHistory;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Override
	public HSSFWorkbook getUserReport(final Long merchantId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception {
		
		List<UserReportDTO> users = ReportRequestUtil.findUserReport(merchantId,startTime,endTime);
		
		final List<ExcelElement> userListTitle = this.getUserReportExcelSheetTitles();
		
		return viewExcel.resultSetToExcel(userListTitle, users, UserReportDTO.class, true); 
		
	}
	
	private List<ExcelElement> getUserReportExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Registration Time", "createdTime", false, 25));
    	titles.add(new ExcelElement("Merchant name", "merchantName", false, 30));
    	titles.add(new ExcelElement("User Mobile area code", "phoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User Mobile", "phone", false, 15));
    	titles.add(new ExcelElement("User name", "userName", false, 20));
    	titles.add(new ExcelElement("Contact email", "email", false, 15));
    	titles.add(new ExcelElement("Email verification status", "isEmailValidation", false, 28));
    	titles.add(new ExcelElement("Facebook account", "snsId", false, 20));
    	titles.add(new ExcelElement("Gender", "gender", false, 20));
    	titles.add(new ExcelElement("Date of bitrh", "birthdayValue", false, 20));
    	titles.add(new ExcelElement("Receive marketing information", "isMarketingInfo", false, 30));
    	titles.add(new ExcelElement("Last mobile verification time", "lastMobileVerifyTime", false, 30));
    	titles.add(new ExcelElement("No. of mobile verification requested after registration", "totalSendSmsQty", false, 30));
    	return titles;
	}

	@Override
	public HSSFWorkbook getRedemptionReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception {
		
		List<Integer> userStampHistoryTypes = new ArrayList<Integer>(); 
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.REDEEM.toValue());
		
		List<UserStampHistoryReportDTO> redemptionList = ReportRequestUtil.findUserStampHistoryReport(merchantId,campaignId,startTime,endTime,userStampHistoryTypes);
		
		final List<ExcelElement> redemptionListTitle = this.getRedemptionExcelSheetTitles();
		
		return viewExcel.resultSetToExcel(redemptionListTitle, redemptionList, UserStampHistoryReportDTO.class, true); 
	}
	
	private List<ExcelElement> getRedemptionExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Redeem Time", "createdTime", false, 20));
    	titles.add(new ExcelElement("Merchant name", "merchantName", false, 30));
    	titles.add(new ExcelElement("Campaign name", "campaignName", false, 25));
    	titles.add(new ExcelElement("User Mobile area code", "userPhoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User Mobile", "userPhone", false, 25));
    	titles.add(new ExcelElement("User name", "userName", false, 20));
    	titles.add(new ExcelElement("Redemption external Store id", "externalStoreId", false, 28));
    	titles.add(new ExcelElement("Reward name", "giftName", false, 20));
    	titles.add(new ExcelElement("Redemption code/Reservation code", "redeemCode", false, 30));
    	titles.add(new ExcelElement("External rule code", "externalRuleCode", false, 20));
    	titles.add(new ExcelElement("No. of used stamps", "stampQty", false, 20));
    	titles.add(new ExcelElement("Cash paid", "cashQty", false, 15));
    	titles.add(new ExcelElement("Reservation time", "reservationTime", false, 20));
    	titles.add(new ExcelElement("POS transaction time", "transDateTime", false, 20));
    	titles.add(new ExcelElement("POS transaction ref. no.", "transNo", false, 25));
    	return titles;
	}

	@Override
	public HSSFWorkbook getCollectStampsReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception {
		List<Integer> userStampHistoryTypes = new ArrayList<Integer>(); 
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.COLLECT_STAMPS.toValue());
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.SYSTEM_IN_STAMPS.toValue());
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.SYSTEM_OUT_STAMPS.toValue());
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.GRANT.toValue());
		
		List<UserStampHistoryReportDTO> collectStamps = ReportRequestUtil.findUserStampHistoryReport(merchantId,campaignId,startTime,endTime,userStampHistoryTypes);
		
		final List<ExcelElement> collectStampsListTitle = this.getCollectStampsExcelSheetTitles();
		
		return viewExcel.resultSetToExcel(collectStampsListTitle, collectStamps, UserStampHistoryReportDTO.class, true); 
	}
	
	private List<ExcelElement> getCollectStampsExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Collect Time", "createdTime", false, 25));
    	titles.add(new ExcelElement("Merchant name", "merchantName", false, 30));
    	titles.add(new ExcelElement("Campaign name", "campaignName", false, 25));
    	titles.add(new ExcelElement("User Mobile area code", "userPhoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User Mobile", "userPhone", false, 25));
    	titles.add(new ExcelElement("User name", "userName", false, 20));
    	titles.add(new ExcelElement("External Store id", "externalStoreId", false, 20));
    	titles.add(new ExcelElement("Type", "typeValue", false, 20));
    	titles.add(new ExcelElement("No. of stamps", "stampQty", false, 15));
    	titles.add(new ExcelElement("Purchase amount", "transAmt", false, 20));
    	titles.add(new ExcelElement("Transaction with couponcms", "isUsedCoupon", false, 30));
    	titles.add(new ExcelElement("POS transaction time", "transDateTime", false, 20));
    	titles.add(new ExcelElement("POS transaction ref. no.", "transNo", false, 25));
    	return titles;
	}

	@Override
	public HSSFWorkbook getReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime,final String reservationEndTime, 
												final String pickupStartTime, final String pickupEndTime, final Integer status, final ViewExcel viewExcel) throws Exception {
		
		List<UserReservationReportDTO> userReservationReports = ReportRequestUtil.findUserReservationReport(merchantId,campaignId,reservationStartTime,reservationEndTime,pickupStartTime,pickupEndTime,status);
		
		final List<ExcelElement> userReservationsTitleList = this.getReservationExcelSheetTitles();
		
		return viewExcel.resultSetToExcel(userReservationsTitleList, userReservationReports, UserReservationReportDTO.class, true); 
	}
	
	private List<ExcelElement> getReservationExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Reservation Time", "createdTime", false, 25));
    	titles.add(new ExcelElement("Merchant name", "merchantName", false, 30));
    	titles.add(new ExcelElement("Campaign name", "campaignName", false, 25));
    	titles.add(new ExcelElement("User Mobile area code", "phoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User Mobile", "phone", false, 25));
    	titles.add(new ExcelElement("User name", "userName", false, 20));
    	titles.add(new ExcelElement("Reservation external Store id", "externalStoreId", false, 28));
    	titles.add(new ExcelElement("Reward name", "giftName", false, 20));
    	titles.add(new ExcelElement("Reservation status", "statusValue", false, 20));
    	titles.add(new ExcelElement("External rule code", "externalRuleCode", false, 20));
    	titles.add(new ExcelElement("No. of used stamps", "stampQty", false, 13));
    	titles.add(new ExcelElement("Cash paid", "cashQty", false, 13));
    	titles.add(new ExcelElement("Reservation pickup time", "pickupTime", false, 25));
    	titles.add(new ExcelElement("Reservation expiry time", "reservationExpiredTime", false, 25));
    	return titles;
	}

	@Override
	public HSSFWorkbook getCollectCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final Integer status, final ViewExcel viewExcel) throws Exception {
		
		List<UserCouponReportDTO> userCouponReports = ReportRequestUtil.findUserCouponReport(merchantId,campaignId,startTime,endTime, status,false);
		
		final List<ExcelElement> userCouponsTitleList = this.getCollectCouponExcelSheetTitles();
		
		return viewExcel.resultSetToExcel(userCouponsTitleList, userCouponReports, UserCouponReportDTO.class, true); 
	}
	
	private List<ExcelElement> getCollectCouponExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Collect Time", "createdTime", false, 25));
    	titles.add(new ExcelElement("Merchant name", "merchantName", false, 30));
    	titles.add(new ExcelElement("Campaign name", "campaignName", false, 25));
    	titles.add(new ExcelElement("User Mobile area code", "phoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User Mobile", "phone", false, 25));
    	titles.add(new ExcelElement("User name", "userName", false, 20));
    	titles.add(new ExcelElement("Collected coupon title", "couponName", false, 25));
    	titles.add(new ExcelElement("Coupon status", "statusValue", false, 20));
    	titles.add(new ExcelElement("Coupon type", "typeValue", false, 20));
    	return titles;
	}


	@Override
	public HSSFWorkbook getRedeemCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final ViewExcel viewExcel) throws Exception {
		
		List<UserCouponReportDTO> userCouponReports = ReportRequestUtil.findUserCouponReport(merchantId,campaignId,startTime,endTime, UserCoupon.UserCouponStatus.REDEEMED.toValue(),true);
		
		final List<ExcelElement> userCouponsTitleList = this.getRedeemCouponExcelSheetTitles();
		
		return viewExcel.resultSetToExcel(userCouponsTitleList, userCouponReports, UserCouponReportDTO.class, true); 
	}
	
	private List<ExcelElement> getRedeemCouponExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Redeem Time", "redeemedDate", false, 25));
    	titles.add(new ExcelElement("Collect Time", "createdTime", false, 25));
    	titles.add(new ExcelElement("Merchant name", "merchantName", false, 30));
    	titles.add(new ExcelElement("Campaign name", "campaignName", false, 25));
    	titles.add(new ExcelElement("User Mobile area code", "phoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User Mobile", "phone", false, 25));
    	titles.add(new ExcelElement("User name", "userName", false, 20));
    	titles.add(new ExcelElement("Redeemed coupon title", "couponName", false, 25));
    	titles.add(new ExcelElement("Coupon type", "typeValue", false, 20));
    	titles.add(new ExcelElement("No. of extra stamps", "rewardQty", false, 20));
    	return titles;
	}

	@Override
	public HSSFWorkbook getTransferStampsReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime,final ViewExcel viewExcel) throws Exception {
		List<Integer> userStampHistoryTypes = new ArrayList<Integer>(); 
		userStampHistoryTypes.add(UserStampHistory.UserStampHistoryType.TRANSFER_OUT_STAMPS.toValue());
		
		List<UserStampHistoryReportDTO> transferStamps = ReportRequestUtil.findUserStampHistoryReport(merchantId,campaignId,startTime,endTime,userStampHistoryTypes);
		
		final List<ExcelElement> transStampsListTitle = this.getTransferStampsExcelSheetTitles();
		
		return viewExcel.resultSetToExcel(transStampsListTitle, transferStamps, UserStampHistoryReportDTO.class, true); 
	}
	
	private List<ExcelElement> getTransferStampsExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Transfer Time", "createdTime", false, 25));
    	titles.add(new ExcelElement("Merchant name", "merchantName", false, 30));
    	titles.add(new ExcelElement("Campaign name", "campaignName", false, 25));
    	titles.add(new ExcelElement("User Mobile area code (Sender)", "userPhoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User Mobile (Sender)", "userPhone", false, 25));
    	titles.add(new ExcelElement("User name (Sender)", "userName", false, 25));
    	titles.add(new ExcelElement("User Mobile area code (Receiver)", "receiverUserPhone", false, 25));
    	titles.add(new ExcelElement("User Mobile (Receiver)", "receiverUserPhoneAreaCode", false, 25));
    	titles.add(new ExcelElement("User name (Receiver)", "receiverUserName", false, 25));
    	titles.add(new ExcelElement("No. of stamps transferred", "stampQty", false, 15));
    	
    	return titles;
	}

	@Override
	public HSSFWorkbook getCampaignReport(final Long campaignId, final ViewExcel viewExcel) throws Exception {
		
		CampaignReportDTO campaignReport = ReportRequestUtil.getCampaignReport(campaignId);
		
		final List<ExcelElement> campaignListTitle = this.getCampaignExcelSheetTitles();
		
		List<CampaignReportDTO> campaignReports = new ArrayList<CampaignReportDTO>();
		campaignReports.add(campaignReport);
		
		return viewExcel.resultSetToExcel(campaignListTitle, campaignReports, CampaignReportDTO.class, false); 
	}

	private List<ExcelElement> getCampaignExcelSheetTitles() {
		final List<ExcelElement> titles = new ArrayList<ExcelElement>();
		
    	titles.add(new ExcelElement("Total no. of stamp issuance", "totalStamps", false, 20));
    	titles.add(new ExcelElement("Total no. of stamp used ", "totalUsedStamps", false, 20));
    	titles.add(new ExcelElement("Total no. of redemption item", "totalRedemptionCount", false, 25));
    	titles.add(new ExcelElement("Total no. of reservation item", "totalReservationCount", false, 25));
    	titles.add(new ExcelElement("Average no. of used stamps/ campaign user", "averageUsedStamps", false, 30));
    	titles.add(new ExcelElement("Average no. of collected stamps/ campaign use", "averageCollectStamps", false, 30));
    	titles.add(new ExcelElement("Average no. of redeemed awards/campaign user", "averageRedemptionCount", false, 30));
    	titles.add(new ExcelElement("Total no. of coupon collected", "totalCollectCoupon", false, 25));
    	titles.add(new ExcelElement("Total no. of active coupon", "totalActiveCoupon", false, 25));
    	titles.add(new ExcelElement("Total no. of inactive coupon", "totalInactiveCoupon", false, 25));
    	titles.add(new ExcelElement("Total no. of coupon used", "totalRedeemCoupon", false, 23));
    	titles.add(new ExcelElement("Total no of coupon expired", "totalExpiredCoupon", false, 20));
    	
    	return titles;
	}
	
}
