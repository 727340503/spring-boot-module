package com.cherrypicks.tcc.cms.customer.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.customer.dao.UserCouponDao;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.UserCouponItemDTO;
import com.cherrypicks.tcc.cms.dto.UserCouponReportDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.model.UserCoupon;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Repository
public class UserCouponDaoImpl extends AbstractBaseDao<UserCoupon> implements UserCouponDao {

	@Override
	public List<UserCouponItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		final String alias = "TEMP";

		final Long userId = (Long) criteriaMap.get("userId");
		final Integer status = (Integer) criteriaMap.get("status");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(UC.ID) FROM USER_COUPON UC ")
				.append("LEFT JOIN USER U ON U.ID = UC.USER_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN COUPON C ON C.ID = UC.COUPON_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT M ON M.ID = UC.MERCHANT_ID AND M.IS_DELETED = 0 ")
				.append("LEFT JOIN COUPON_LANG_MAP CM ON CM.COUPON_ID = C.ID AND CM.IS_DELETED = 0 AND CM.LANG_CODE = U.LANG_CODE ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = C.CAMPAIGN_ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
				.append("WHERE UC.IS_DELETED = 0 ");
		
		sql.append("SELECT * FROM ( ")
			.append("SELECT ")
			.append("UC.ID,UC.CREATED_TIME,UC.REDEEMED_DATE,UC.STATUS,UC.REMARK, ")
			.append("IFNULL(UC.UPDATED_BY, UC.CREATED_BY) AS HANDLED_BY, ")
			.append("CM.NAME AS COUPON_NAME,C.TYPE,C.IS_AFTER_COLLECT,C.END_DAYS_AFTER_COLLECT,C.REDEEM_END_TIME, ")
			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE, ")
			.append("CL.NAME AS CAMPAIGN_NAME ")
			.append("FROM USER_COUPON UC ")
			.append("LEFT JOIN USER U ON U.ID = UC.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN COUPON C ON C.ID = UC.COUPON_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UC.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN COUPON_LANG_MAP CM ON CM.COUPON_ID = C.ID AND CM.IS_DELETED = 0 AND CM.LANG_CODE = U.LANG_CODE ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = C.CAMPAIGN_ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("WHERE UC.IS_DELETED = 0 ");
		
		if(null != userId) {
			sqlCount.append("AND UC.USER_ID = ? ");
			sql.append("AND UC.USER_ID = ? ");
			
			param.setLong(userId);
		}
		
		if(null != status) {
			if(status.intValue() == UserCoupon.UserCouponStatus.INACTIVE.toValue() || status.intValue() == UserCoupon.UserCouponStatus.REDEEMED.toValue()) {
				sqlCount.append("AND UC.STATUS = ? ");
				sql.append("AND UC.STATUS = ? ");
				
				param.setInt(status);
			}else if(status.intValue() == UserCoupon.UserCouponStatus.EXPIRED.toValue()) {
				sqlCount.append("AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) <= ?");
				sql.append("AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) <= ?");
				
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue()) {
				sqlCount.append("AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) > ?");
				sql.append("AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) > ?");
				
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}
		}
		
		sql.append(" ) AS TEMP ");
		
		return getPagingList(UserCouponItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public Long findByTotalByCampaignId(final Long campaignId, final Integer status) {
		
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT COUNT(UC.ID) FROM USER_COUPON UC ")
			.append("LEFT JOIN COUPON C ON C.ID = UC.COUPON_ID AND C.IS_DELETED = 0 ")
			.append("WHERE UC.IS_DELETED = 0 AND C.CAMPAIGN_ID = ? ");

		param.setLong(campaignId);
		if(null != status) {
			if(UserCoupon.UserCouponStatus.EXPIRED.toValue() == status.intValue()) {
				sql.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) <= ?");
				
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}else if(UserCoupon.UserCouponStatus.REDEEMED.toValue() == status.intValue()) {
				sql.append("AND UC.STATUS = ? ");
				
				param.setInt(UserCoupon.UserCouponStatus.REDEEMED.toValue());
			}else if(status.intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue()) {
				sql.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) > ?");
				
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserCoupon.UserCouponStatus.INACTIVE.toValue()) {
				sql.append("AND ( UC.STATUS = ? OR ( C.STATUS = ? AND UC.STATUS= ? ) ) ");
				
				param.setInt(status);
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
			}
		}
		
		return queryForLong(sql.toString(), param);
	}

	@Override
	public List<UserCouponReportDTO> findUserCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final Integer status,final boolean isRedeemCouponReport) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ")
			.append("UC.CREATED_TIME,UC.REDEEMED_DATE,UC.STATUS, ")
			.append("CM.NAME AS COUPON_NAME,C.TYPE,C.REWARD_QTY,C.IS_AFTER_COLLECT,C.END_DAYS_AFTER_COLLECT,C.REDEEM_END_TIME, ")
			.append("ML.NAME AS MERCHANT_NAME,CL.NAME AS CAMPAIGN_NAME,M.TIME_ZONE AS MERCHANT_TIME_ZONE, ")
			.append("C.STATUS AS COUPON_STATUS, ")
			.append("U.PHONE_AREA_CODE,U.PHONE,U.USER_NAME ")
			.append("FROM USER_COUPON UC ")
			.append("LEFT JOIN USER U ON U.ID = UC.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN COUPON C ON C.ID = UC.COUPON_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UC.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = M.ID AND ML.LANG_CODE = U.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN COUPON_LANG_MAP CM ON CM.COUPON_ID = C.ID AND CM.IS_DELETED = 0 AND CM.LANG_CODE = ML.LANG_CODE ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = C.CAMPAIGN_ID AND CL.LANG_CODE = ML.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("WHERE UC.IS_DELETED = 0 ");
		
		if(null != merchantId) {
			sql.append("AND UC.MERCHANT_ID = ? ");
			
			param.setLong(merchantId);
		}
		
		if(null != campaignId) {
			sql.append("AND C.CAMPAIGN_ID = ? ");
			
			param.setLong(campaignId);
		}
		
		if(null != status) {
			if(status.intValue() == UserCoupon.UserCouponStatus.REDEEMED.toValue()) {
				sql.append("AND UC.STATUS = ? ");
				
				param.setInt(status);
			}else if(status.intValue() == UserCoupon.UserCouponStatus.EXPIRED.toValue()) {
				sql.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) <= ? ");
				
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue()) {
				sql.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) > ?");
				
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserCoupon.UserCouponStatus.INACTIVE.toValue()) {
				sql.append("AND ( UC.STATUS = ? OR ( C.STATUS = ? AND UC.STATUS= ? ) ) ");
				
				param.setInt(status);
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
			}
		}else {
			if(!isRedeemCouponReport) {
				sql.append("AND UC.STATUS != ? ");
				
				param.setInt(UserCoupon.UserCouponStatus.REDEEMED.toValue());
			}
		}
		
		if(StringUtils.isNotBlank(startTime)) {
			if(!isRedeemCouponReport) {
				sql.append("AND UC.CREATED_TIME >= CONVERT_TZ(? , M.TIME_ZONE, ?)");
			}else {
				sql.append("AND UC.REDEEMED_DATE >= CONVERT_TZ(? , M.TIME_ZONE, ?)");
			}
			
			param.setString(startTime+" 00:00:00 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(endTime)) {
			if(!isRedeemCouponReport) {
				sql.append("AND UC.CREATED_TIME <= CONVERT_TZ(? , M.TIME_ZONE, ?)");
			}else {
				sql.append("AND UC.REDEEMED_DATE <= CONVERT_TZ(? , M.TIME_ZONE, ?)");
			}
			
			param.setString(endTime+" 00:00:00 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(!isRedeemCouponReport) {
			sql.append("ORDER BY UC.CREATED_TIME ASC");
		}else if(status.intValue() == UserCoupon.UserCouponStatus.REDEEMED.toValue()) {
			sql.append("ORDER BY UC.REDEEMED_DATE ASC");
		}
		
		return queryForList(sql.toString(), UserCouponReportDTO.class, param);
	}

	@Override
	public List<UserCouponReportDTO> pagingFindUserCoupon(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final int[] args) {
		final String alias = "TEMP";

		final Long campaignId = (Long) criteriaMap.get("campaignId");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final String startTime = (String) criteriaMap.get("startTime");
		final String endTime = (String) criteriaMap.get("endTime");
		final Integer status = (Integer) criteriaMap.get("status");
		final Boolean isRedeemCouponReport = (Boolean) criteriaMap.get("isRedeemCouponReport");//该字段用来区分是redeem coupon report or collect coupon report
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(UC.ID) FROM USER_COUPON UC ")
				.append("LEFT JOIN USER U ON U.ID = UC.USER_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN COUPON C ON C.ID = UC.COUPON_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT M ON M.ID = UC.MERCHANT_ID AND M.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = M.ID AND ML.LANG_CODE = U.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("LEFT JOIN COUPON_LANG_MAP CM ON CM.COUPON_ID = C.ID AND CM.IS_DELETED = 0 AND CM.LANG_CODE = ML.LANG_CODE ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = C.CAMPAIGN_ID AND CL.LANG_CODE = ML.LANG_CODE AND CL.IS_DELETED = 0 ")
				.append("WHERE UC.IS_DELETED = 0 ");
		
		sql.append("SELECT * FROM ( ")
			.append("SELECT ")
			.append("UC.CREATED_TIME,UC.REDEEMED_DATE,UC.STATUS, ")
			.append("CM.NAME AS COUPON_NAME,C.TYPE,C.REWARD_QTY,C.IS_AFTER_COLLECT,C.END_DAYS_AFTER_COLLECT,C.REDEEM_END_TIME, ")
			.append("C.STATUS AS COUPON_STATUS, ")
			.append("ML.NAME AS MERCHANT_NAME,CL.NAME AS CAMPAIGN_NAME,M.TIME_ZONE AS MERCHANT_TIME_ZONE, ")
			.append("U.PHONE_AREA_CODE,U.PHONE,U.USER_NAME ")
			.append("FROM USER_COUPON UC ")
			.append("LEFT JOIN USER U ON U.ID = UC.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN COUPON C ON C.ID = UC.COUPON_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UC.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = M.ID AND ML.LANG_CODE = U.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN COUPON_LANG_MAP CM ON CM.COUPON_ID = C.ID AND CM.IS_DELETED = 0 AND CM.LANG_CODE = ML.LANG_CODE ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = C.CAMPAIGN_ID AND CL.LANG_CODE = ML.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("WHERE UC.IS_DELETED = 0 ");
		
		if(null != status){
			if(status.intValue() == UserCoupon.UserCouponStatus.REDEEMED.toValue()) {
				sqlCount.append("AND UC.STATUS = ? ");
				sql.append("AND UC.STATUS = ? ");
				
				param.setInt(status);
			}else if(status.intValue() == UserCoupon.UserCouponStatus.EXPIRED.toValue()) {
				sqlCount.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) <= ?");
				sql.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) <= ?");
				
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue()) {
				sqlCount.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) > ?");
				sql.append("AND C.STATUS != ? AND UC.STATUS = ? AND ( IF ( C.IS_AFTER_COLLECT = ? ,DATE_ADD( UC.CREATED_TIME,INTERVAL C.END_DAYS_AFTER_COLLECT DAY ), C.REDEEM_END_TIME)) > ?");
				
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
				param.setBool(true);
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserCoupon.UserCouponStatus.INACTIVE.toValue()) {
				sqlCount.append("AND ( UC.STATUS = ? OR ( C.STATUS = ? AND UC.STATUS= ? ) ) ");
				sql.append("AND ( UC.STATUS = ? OR ( C.STATUS = ? AND UC.STATUS= ? ) ) ");
				
				param.setInt(status);
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
				param.setInt(UserCoupon.UserCouponStatus.ACTIVE.toValue());
			}
		}else {
			if(!isRedeemCouponReport) {
				sqlCount.append("AND UC.STATUS != ? ");
				sql.append("AND UC.STATUS != ? ");
				
				param.setInt(UserCoupon.UserCouponStatus.REDEEMED.toValue());
			}
		}
		
		if(null != merchantId){
			sqlCount.append("AND UC.MERCHANT_ID = ? ");
			sql.append("AND UC.MERCHANT_ID = ? ");
			
			param.setLong(merchantId);
		}
		
		if(null != campaignId){
			sqlCount.append("AND C.CAMPAIGN_ID = ? ");
			sql.append("AND C.CAMPAIGN_ID = ? ");
			
			param.setLong(campaignId);
		}
		
		if(StringUtils.isNotBlank(startTime)){
			if(!isRedeemCouponReport){
				sqlCount.append("AND UC.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				sql.append("AND UC.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				
			}else {
				sqlCount.append("AND UC.REDEEMED_DATE >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				sql.append("AND UC.REDEEMED_DATE >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				
			}
			
			param.setString(startTime + " 00:00:00");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(endTime)){
			if(!isRedeemCouponReport){
				sqlCount.append("AND UC.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				sql.append("AND UC.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				
			}else {
				sqlCount.append("AND UC.REDEEMED_DATE <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				sql.append("AND UC.REDEEMED_DATE <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
				
			}
			
			param.setString(endTime + " 23:59:59");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		sql.append(" ) AS TEMP ");
		
		return getPagingList(UserCouponReportDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}
	
	@Override
    public UserCoupon findByQrCode(final Long merchantId, final String qrCode) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM USER_COUPON WHERE QR_CODE = ? AND MERCHANT_ID = ? AND IS_DELETED = 0 ");
        final StatementParameter param = new StatementParameter();
        param.setString(qrCode);
        param.setLong(merchantId);
        return super.query(sb.toString(), UserCoupon.class, param);
    }


}
