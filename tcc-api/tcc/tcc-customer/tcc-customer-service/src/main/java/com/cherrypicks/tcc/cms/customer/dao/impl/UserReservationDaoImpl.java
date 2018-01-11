package com.cherrypicks.tcc.cms.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.customer.dao.UserReservationDao;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.UserReservationDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationItemDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationPushNotifDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationReportDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.model.UserReservation;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Repository
public class UserReservationDaoImpl extends AbstractBaseDao<UserReservation> implements UserReservationDao {

	@Override
	public List<UserReservationItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		String alias = "UR";
		
		final Integer status = (Integer) criteriaMap.get("status");
		final String userName = (String) criteriaMap.get("userName");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final Long campaignId = (Long) criteriaMap.get("campaignId");
		final Long giftId = (Long) criteriaMap.get("giftId");
		final Long customerId = (Long) criteriaMap.get("customerId");
		final String reservationCode = (String) criteriaMap.get("reservationCode");
		final String reservationStartDate = (String) criteriaMap.get("reservationStartDate");
		final String reservationEndDate = (String) criteriaMap.get("reservationEndDate");
		final String storeName = (String) criteriaMap.get("storeName");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
//		sqlCount.append("SELECT COUNT(*) FROM USER_RESERVATION UR ")
//				.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
//				.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
//				.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.IS_DELETED = 0 ")
//				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND CL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
//				.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
//				.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
//				.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND ML.LANG_CODE = GL.LANG_CODE ");
//		
//		sql.append("SELECT ")
//			.append("UR.ID,UR.CREATED_TIME,UR.STATUS,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,U.USER_NAME,ML.NAME AS MERCHANT_NAME,U.FIRST_NAME,U.LAST_NAME,UR.RESERVATION_CODE, ")
////			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,C.RESERVATION_EXPIRED_TIME ")
//			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,UR.RESERVATION_EXPIRED_TIME ")
//			.append("FROM USER_RESERVATION UR ")
//			.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
//			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
//			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
//			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
//			.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
//			.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
//			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE ");
		sqlCount.append("SELECT COUNT(*) FROM USER_RESERVATION UR ")
				.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND ML.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.LANG_CODE = ML.LANG_CODE AND CL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
				.append("LEFT JOIN STORE_LANG_MAP SL ON SL.STORE_ID = UR.STORE_ID AND SL.IS_DELETED = 0 AND SL.LANG_CODE = ML.LANG_CODE ")
				.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND ML.LANG_CODE = GL.LANG_CODE ");
		
		sql.append("SELECT ")
			.append("UR.ID,UR.CREATED_TIME,UR.STATUS,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,U.USER_NAME,ML.NAME AS MERCHANT_NAME,U.FIRST_NAME,U.LAST_NAME,UR.RESERVATION_CODE, ")
			.append("SL.NAME AS STORE_NAME, ")
//			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,C.RESERVATION_EXPIRED_TIME ")
			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,UR.RESERVATION_EXPIRED_TIME ")
			.append("FROM USER_RESERVATION UR ")
			.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.LANG_CODE = ML.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
			.append("LEFT JOIN STORE_LANG_MAP SL ON SL.STORE_ID = UR.STORE_ID AND SL.IS_DELETED = 0 AND SL.LANG_CODE = ML.LANG_CODE ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE ");
		
		
		sqlCount.append("WHERE UR.IS_DELETED = 0 AND ML.IS_DEFAULT = ? ");
		sql.append("WHERE UR.IS_DELETED = 0 AND ML.IS_DEFAULT = ? ");
		
		param.setBool(true);
		
		if(null != merchantId){
			sqlCount.append("AND UR.MERCHANT_ID = ? ");
			sql.append("AND UR.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(null != campaignId){
			sqlCount.append("AND CL.CAMPAIGN_ID = ? ");
			sql.append("AND CL.CAMPAIGN_ID = ? ");
		
			param.setLong(campaignId);
		}

		if(null != giftId){
			sqlCount.append("AND GL.GIFT_ID = ? ");
			sql.append("AND GL.GIFT_ID = ? ");
		
			param.setLong(giftId);
		}
		
		if(StringUtils.isNotBlank(userName)){
			sqlCount.append("AND U.USER_NAME REGEXP ? ");
			sql.append("AND U.USER_NAME REGEXP ? ");
		
			param.setString(userName);
		}
		
		if(StringUtils.isNotBlank(reservationCode)){
			sqlCount.append("AND UR.RESERVATION_CODE REGEXP ? ");
			sql.append("AND UR.RESERVATION_CODE REGEXP ? ");
		
			param.setString(reservationCode);
		}
		
		if(null != status){
			if(status.intValue() == UserReservation.UserReservationStatus.EXPIRED.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME <= ? ");
				sql.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME <= ? ");
				
				param.setInt(UserReservation.UserReservationStatus.AVAILABLE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			} else if(status.intValue() == UserReservation.UserReservationStatus.CANCELLED.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) != DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?) ");
				sql.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) != DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				
				param.setInt(UserReservation.UserReservationStatus.IN_COMPLETED.toValue());
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
			}else if(status.intValue() == UserReservation.UserReservationStatus.AVAILABLE.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME > ? ");
				sql.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME > ? ");
				
				param.setInt(UserReservation.UserReservationStatus.AVAILABLE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserReservation.UserReservationStatus.IN_COMPLETED.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) = DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				sql.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) = DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				
				param.setInt(UserReservation.UserReservationStatus.IN_COMPLETED.toValue());
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
			}else{
				sqlCount.append("AND UR.STATUS = ? ");
				sql.append("AND UR.STATUS = ? ");
				
				param.setInt(status);
			}
		}
		
		if(null != customerId){
			sqlCount.append("AND UR.USER_ID = ? ");
			sql.append("AND UR.USER_ID = ? ");
		
			param.setLong(customerId);
		}
		
		if(StringUtils.isNotBlank(reservationStartDate)){
			sqlCount.append("AND UR.CREATED_TIME >= ? ");
			sql.append("AND UR.CREATED_TIME >= ? ");
		
			param.setString(reservationStartDate + " 00:00:00 ");
		}

		if(StringUtils.isNotBlank(reservationEndDate)){
			sqlCount.append("AND UR.CREATED_TIME <= ?  ");
			sql.append("AND UR.CREATED_TIME <= ?  ");
			
			param.setString(reservationEndDate + " 23:59:59 ");
		}
		
		if(StringUtils.isNotBlank(storeName)){
			sqlCount.append("AND SL.NAME REGEXP ? ");
			sql.append("AND SL.NAME REGEXP ? ");
			
			param.setString(storeName);
		}
		
		if(sortField.equalsIgnoreCase("userName") || sortField.equalsIgnoreCase("firstName") || sortField.equalsIgnoreCase("lastName")){
			alias = "U";
		}
		if(sortField.equalsIgnoreCase("campaignName")){
			alias = "CL";
			sortField = "name";
		}
		if(sortField.equalsIgnoreCase("giftName")){
			alias = "GL";
			sortField = "name";
		}
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ML";
			sortField = "name";
		}
		if(sortField.equalsIgnoreCase("storeName")){
			alias = "SL";
			sortField = "name";
		}
		
		return getPagingList(UserReservationItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}
	
	@Override
	public UserReservationDetailDTO findDetailById(Long userReservationId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ")
			.append("UR.UPDATED_TIME, ")
			.append("UR.ID,UR.MERCHANT_ID,ML.NAME AS MERCHANT_NAME,U.USER_NAME,GL.NAME AS GIFT_NAME,UR.CREATED_TIME,CL.NAME AS CAMPAIGN_NAME,UR.STATUS,UR.STAMP_QTY,UR.CASH_QTY,SL.ADDRESS AS STORE_ADDRESS, ")
			.append("SL.NAME AS STORE_NAME,U.FIRST_NAME,U.LAST_NAME,UR.RESERVATION_CODE,UR.CONTACT_PHONE_AREA_CODE,UR.CONTACT_PHONE,S.PHONE AS STORE_PHONE, ")
			.append("CONCAT(IFNULL(UR.FIRST_NAME,\"\"),\" \",IFNULL(UR.LAST_NAME,\"\")) AS CONTACT_PERSON, ")
//			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,C.RESERVATION_EXPIRED_TIME ")
			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,UR.RESERVATION_EXPIRED_TIME ")
			.append("FROM USER_RESERVATION UR ")
			.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE ")
			.append("LEFT JOIN STORE_LANG_MAP SL ON SL.STORE_ID = UR.STORE_ID AND SL.LANG_CODE = U.LANG_CODE AND SL.IS_DELETED = 0 ")
			.append("LEFT JOIN STORE S ON S.ID = UR.STORE_ID AND S.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
			.append("WHERE UR.IS_DELETED = 0 AND UR.ID = ? ");
		param.setLong(userReservationId);
		
		return query(sql.toString(), UserReservationDetailDTO.class, param);
	}

//	@Override
//	public Date findReservationExpiredTime(Long userReservationId) {
//		final StringBuilder sql = new StringBuilder();
//		final StatementParameter param = new StatementParameter();
//		
//		sql.append("SELECT C.RESERVATION_EXPIRED_TIME FROM CAMPAIGN C ")
//			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CG ON CG.CAMPAIGN_ID = C.ID ")
//			.append("LEFT JOIN USER_RESERVATION UR ON UR.CAMPAIGN_GIFT_MAP_ID = CG.ID ")
//			.append("WHERE UR.ID = ? AND UR.IS_DELETED = 0 AND CG.IS_DELETED = 0 AND C.IS_DELETED = 0 ");
//	
//		param.setLong(userReservationId);
//
//		return queryForDate(sql.toString(),param);
//	}

	@Override
	public List<UserReservationReportDTO> findUserReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, final String pickupEndTime, final Integer status) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ")
			.append("U.PHONE,U.PHONE_AREA_CODE, ")
			.append("UR.ID,UR.CREATED_TIME,UR.STATUS,UR.UPDATED_TIME,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,U.USER_NAME,ML.NAME AS MERCHANT_NAME, ")
			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,UR.RESERVATION_EXPIRED_TIME,S.EXTERNAL_STORE_ID,UR.STAMP_QTY,UR.CASH_QTY,UR.EXCHANGE_TYPE_ID,ET.EXTERNAL_RULE_CODE ")
//			.append("UR.ID, ")
//			.append("UR.STATUS,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,U.USER_NAME,ML.NAME AS MERCHANT_NAME, ")
//			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,S.EXTERNAL_STORE_ID,UR.STAMP_QTY,UR.CASH_QTY,UR.EXCHANGE_TYPE_ID, ")
//			.append("CONVERT_TZ(UR.CREATED_TIME, ? ,M.TIME_ZONE) AS CREATED_TIME, ")
//			.append("CONVERT_TZ(UR.RESERVATION_EXPIRED_TIME, ? ,M.TIME_ZONE) AS RESERVATION_EXPIRED_TIME ")
			.append("FROM USER_RESERVATION UR ")
			.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_EXCHANGE_TYPE ET ON ET.ID = UR.EXCHANGE_TYPE_ID AND ET.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE ")
			.append("LEFT JOIN STORE S ON S.ID = UR.STORE_ID AND S.IS_DELETED = 0 ");
		
		sql.append("WHERE UR.IS_DELETED = 0 ");
		
//		param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
//		param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		
		if(null != merchantId){
			sql.append("AND UR.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(null != campaignId){
			sql.append("AND C.ID = ? ");
		
			param.setLong(campaignId);
		}
		
		if(null != status){
			if(status.intValue() == UserReservation.UserReservationStatus.EXPIRED.toValue()){
				sql.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME <= ? ");
				
				param.setInt(UserReservation.UserReservationStatus.AVAILABLE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			} else if(status.intValue() == UserReservation.UserReservationStatus.CANCELLED.toValue()){
				sql.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) != DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				
				param.setInt(UserReservation.UserReservationStatus.IN_COMPLETED.toValue());
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
			}else if(status.intValue() == UserReservation.UserReservationStatus.AVAILABLE.toValue()){
				sql.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME > ? ");
				
				param.setInt(UserReservation.UserReservationStatus.AVAILABLE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserReservation.UserReservationStatus.IN_COMPLETED.toValue()){
				sql.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) = DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				
				param.setInt(UserReservation.UserReservationStatus.IN_COMPLETED.toValue());
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
			}else{
				sql.append("AND UR.STATUS = ? ");
				
				param.setInt(status);
			}
		}
		
		if(StringUtils.isNotBlank(reservationStartTime)){
			sql.append("AND UR.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
		
			param.setString(reservationStartTime + " 00:00:00 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(reservationEndTime)){
			sql.append("AND UR.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
		
			param.setString(reservationEndTime + " 23:59:59 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(pickupStartTime)){
			sql.append("AND UR.UPDATED_TIME >= CONVERT_TZ(?, ?, M.TIME_ZONE) AND UR.STATUS = ? ");
		
			param.setString(pickupStartTime + " 00:00:00 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
			param.setInt(UserReservation.UserReservationStatus.SETTLED.toValue());
		}

		if(StringUtils.isNotBlank(pickupEndTime)){
			sql.append("AND UR.UPDATED_TIME <= CONVERT_TZ(?, ?, M.TIME_ZONE) AND UR.STATUS = ? ");
		
			param.setString(pickupEndTime + " 23:59:59 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
			param.setInt(UserReservation.UserReservationStatus.SETTLED.toValue());
		}
		
		sql.append(" ORDER BY CREATED_TIME ASC ");
		
		return queryForList(sql.toString(),UserReservationReportDTO.class, param);
	}

	@Override
	public List<UserReservationReportDTO> pagingFindUserReservationReport(final Map<String, Object> criteriaMap, String sortField, final String sortType, final int[] args) {
		String alias = "UR";
		
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final String reservationStartTime = (String) criteriaMap.get("reservationStartTime");
		final String reservationEndTime = (String) criteriaMap.get("reservationEndTime");
		final String pickupStartTime = (String) criteriaMap.get("pickupStartTime");
		final String pickupEndTime = (String) criteriaMap.get("pickupEndTime");
		final Long campaignId = (Long) criteriaMap.get("campaignId");
		final Integer status = (Integer) criteriaMap.get("status");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM USER_RESERVATION UR ")
				.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_GIFT_EXCHANGE_TYPE ET ON ET.ID = UR.EXCHANGE_TYPE_ID AND ET.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND CL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
				.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND ML.LANG_CODE = GL.LANG_CODE ")
				.append("LEFT JOIN STORE S ON S.ID = UR.STORE_ID AND S.IS_DELETED = 0 ");
		
		sql.append("SELECT ")
			.append("U.PHONE,U.PHONE_AREA_CODE, ")
			.append("UR.ID,UR.CREATED_TIME,UR.STATUS,UR.UPDATED_TIME,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,U.USER_NAME,ML.NAME AS MERCHANT_NAME, ")
			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,UR.RESERVATION_EXPIRED_TIME,S.EXTERNAL_STORE_ID,UR.STAMP_QTY,UR.CASH_QTY,UR.EXCHANGE_TYPE_ID,ET.EXTERNAL_RULE_CODE ")
//			.append("UR.ID, ")
//			.append("UR.STATUS,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,U.USER_NAME,ML.NAME AS MERCHANT_NAME, ")
//			.append("M.TIME_ZONE AS MERCHANT_TIME_ZONE,S.EXTERNAL_STORE_ID,UR.STAMP_QTY,UR.CASH_QTY,UR.EXCHANGE_TYPE_ID, ")
//			.append("CONVERT_TZ(UR.CREATED_TIME, \"")
//			.append(TimeZoneConvert.DEFAULT_TIMEZONE)
//			.append("\",M.TIME_ZONE) AS CREATED_TIME, ")
//			.append("CONVERT_TZ(UR.RESERVATION_EXPIRED_TIME, \"")
//			.append(TimeZoneConvert.DEFAULT_TIMEZONE)
//			.append("\",M.TIME_ZONE) AS RESERVATION_EXPIRED_TIME ")
			.append("FROM USER_RESERVATION UR ")
			.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CGM.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_EXCHANGE_TYPE ET ON ET.ID = UR.EXCHANGE_TYPE_ID AND ET.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CGM.CAMPAIGN_ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN C ON C.ID = CGM.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CGM.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE ")
			.append("LEFT JOIN STORE S ON S.ID = UR.STORE_ID AND S.IS_DELETED = 0 ");
		
		
		sqlCount.append("WHERE UR.IS_DELETED = 0 ");
		sql.append("WHERE UR.IS_DELETED = 0 ");
		
		if(null != merchantId){
			sqlCount.append("AND UR.MERCHANT_ID = ? ");
			sql.append("AND UR.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(null != campaignId){
			sqlCount.append("AND C.ID = ? ");
			sql.append("AND C.ID = ? ");
		
			param.setLong(campaignId);
		}
		
		if(null != status){
			if(status.intValue() == UserReservation.UserReservationStatus.EXPIRED.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME <= ? ");
				sql.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME <= ? ");
				
				param.setInt(UserReservation.UserReservationStatus.AVAILABLE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			} else if(status.intValue() == UserReservation.UserReservationStatus.CANCELLED.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) != DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				sql.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) != DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				
				param.setInt(UserReservation.UserReservationStatus.IN_COMPLETED.toValue());
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
			}else if(status.intValue() == UserReservation.UserReservationStatus.AVAILABLE.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME > ? ");
				sql.append("AND UR.STATUS = ? AND UR.RESERVATION_EXPIRED_TIME > ? ");
				
				param.setInt(UserReservation.UserReservationStatus.AVAILABLE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == UserReservation.UserReservationStatus.IN_COMPLETED.toValue()){
				sqlCount.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) = DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				sql.append("AND UR.STATUS = ? AND DATE_FORMAT(CONVERT_TZ(UR.CREATED_TIME,?,M.TIME_ZONE), ? ) = DATE_FORMAT(CONVERT_TZ(NOW(),?,M.TIME_ZONE),?)  ");
				
				param.setInt(UserReservation.UserReservationStatus.IN_COMPLETED.toValue());
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
				param.setString(TimeZoneConvert.getCurrentServerTimeZone());
				param.setString("%Y-%m-%d");
			}else{
				sqlCount.append("AND UR.STATUS = ? ");
				sql.append("AND UR.STATUS = ? ");
				
				param.setInt(status);
			}
		}
		
		if(StringUtils.isNotBlank(reservationStartTime)){
			sqlCount.append("AND UR.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ? ) ");
			sql.append("AND UR.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
		
			param.setString(reservationStartTime + " 00:00:00 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(reservationEndTime)){
			sqlCount.append("AND UR.CREATED_TIME <= CONVERT_TZ(?, ?, M.TIME_ZONE) ");
			sql.append("AND UR.CREATED_TIME <= CONVERT_TZ(?, ?, M.TIME_ZONE) ");
		
			param.setString(reservationEndTime + " 23:59:59 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(pickupStartTime)){
			sqlCount.append("AND UR.UPDATED_TIME >= CONVERT_TZ(?, ?, M.TIME_ZONE) AND UR.STATUS = ? ");
			sql.append("AND UR.UPDATED_TIME >= CONVERT_TZ(?, ?, M.TIME_ZONE) AND UR.STATUS = ? ");
		
			param.setString(pickupStartTime + " 00:00:00 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
			param.setInt(UserReservation.UserReservationStatus.SETTLED.toValue());
		}

		if(StringUtils.isNotBlank(pickupEndTime)){
			sqlCount.append("AND UR.UPDATED_TIME <= CONVERT_TZ(?, ?, M.TIME_ZONE) AND UR.STATUS = ? ");
			sql.append("AND UR.UPDATED_TIME <= CONVERT_TZ(?, ?, M.TIME_ZONE) AND UR.STATUS = ? ");
		
			param.setString(pickupEndTime + " 23:59:59 ");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
			param.setInt(UserReservation.UserReservationStatus.SETTLED.toValue());
		}
		
		if(sortField.equalsIgnoreCase("userName")){
			alias = "U";
		}
		if(sortField.equalsIgnoreCase("campaignName")){
			alias = "CL";
			sortField = "name";
		}
		if(sortField.equalsIgnoreCase("giftName")){
			alias = "GL";
			sortField = "name";
		}
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ML";
			sortField = "name";
		}
		
		return getPagingList(UserReservationReportDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public Long findCampaignReservationCount(final Long campaignId,final List<Integer> statusList) {
		final StringBuilder sql = new StringBuilder();
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		
		sql.append("SELECT COUNT(1) FROM USER_RESERVATION UR ")
			.append("LEFT JOIN USER_STAMP_CARD UC ON UC.ID = UR.USER_STAMP_CARD_ID AND UC.IS_DELETED = 0 ")
			.append("WHERE UR.IS_DELETED = 0 AND UC.CAMPAIGN_ID = :campaignId AND UR.STATUS IN ( :statusList ) ");
		paramMap.put("campaignId", campaignId);
		paramMap.put("statusList", statusList);
		
		return queryForLongWithNamedParam(sql.toString(), paramMap);
	}

	@Override
	public UserReservationPushNotifDTO finduserReservationPushNotifInfo(final Long id) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT UR.USER_ID,U.USER_NAME,M.TIME_ZONE,U.LANG_CODE,GL.NAME AS GIFT_NAME,M.DATE_FORMAT,UR.RESERVATION_EXPIRED_TIME FROM USER_RESERVATION UR ")
			.append("LEFT JOIN USER U ON U.ID = UR.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = UR.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UR.MERCHANT_ID AND ML.LANG_CODE = U.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CG ON CG.ID = UR.CAMPAIGN_GIFT_MAP_ID AND CG.IS_DELETED = 0 ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CG.GIFT_ID AND GL.LANG_CODE = U.LANG_CODE AND GL.IS_DELETED = 0 ")
			.append("WHERE UR.IS_DELETED = 0 AND UR.ID = ? ");
		
		param.setLong(id);
		
		return query(sql.toString(), UserReservationPushNotifDTO.class, param);
	}

}
