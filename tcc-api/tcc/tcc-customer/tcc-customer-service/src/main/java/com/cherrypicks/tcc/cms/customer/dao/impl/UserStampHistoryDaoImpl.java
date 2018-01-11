package com.cherrypicks.tcc.cms.customer.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.customer.dao.UserStampHistoryDao;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.UserStampHistoryDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserStampHistoryReportDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.model.UserStampHistory;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Repository
public class UserStampHistoryDaoImpl extends AbstractBaseDao<UserStampHistory> implements UserStampHistoryDao {

	@Override
	public List<UserStampHistoryDetailDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		String alias = "UT";

		final Long userId = (Long) criteriaMap.get("userId");
		final Integer type = (Integer) criteriaMap.get("type");
		final Long campaignId = (Long) criteriaMap.get("campaignId");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM USER_STAMP_HISTORY UT ");
		
		sqlCount.append("LEFT JOIN USER U ON U.ID = UT.USER_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_GIFT_MAP CG ON CG.ID = UT.CAMPAIGN_GIFT_MAP_ID AND CG.IS_DELETED = 0 ")
				.append("LEFT JOIN USER_STAMP_CARD USC ON USC.ID = UT.USER_STAMP_CARD_ID AND USC.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CG.CAMPAIGN_ID AND U.LANG_CODE = CL.LANG_CODE AND CL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UT.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CG.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE AND GL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ");
		
		
		sql.append("SELECT ")
			.append("UT.ID,UT.USER_ID,UT.CREATED_TIME,UT.EXCHANGE_TYPE,UT.TRANS_NO,UT.TRANS_DATE_TIME,UT.TYPE,UT.STAMP_QTY,UT.CREATED_BY,UT.REMARKS,U.USER_NAME, ");
		
		sql.append("ML.NAME AS MERCHANT_NAME,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,M.TIME_ZONE AS MERCHANT_TIME_ZONE ")
			.append("FROM USER_STAMP_HISTORY UT ");
		
		sql.append("LEFT JOIN USER U ON U.ID = UT.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CG ON CG.ID = UT.CAMPAIGN_GIFT_MAP_ID AND CG.IS_DELETED = 0 ")
			.append("LEFT JOIN USER_STAMP_CARD USC ON USC.ID = UT.USER_STAMP_CARD_ID AND USC.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = USC.CAMPAIGN_ID AND U.LANG_CODE = CL.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UT.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CG.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE AND GL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ");
		
		sqlCount.append("WHERE UT.IS_DELETED = 0 AND UT.USER_ID = ? ");
		sql.append("WHERE UT.IS_DELETED = 0 AND UT.USER_ID = ? ");

		param.setLong(userId);
		
		if(null != type){
			sqlCount.append("AND UT.TYPE = ? ");
			sql.append("AND UT.TYPE = ? ");
			
			param.setInt(type);
		}
		
		if(null != campaignId){
			sqlCount.append("AND USC.CAMPAIGN_ID = ? ");
			sql.append("AND USC.CAMPAIGN_ID = ? ");
			
			param.setLong(campaignId);
		}
		
		if(null != merchantId){
			sqlCount.append("AND UT.MERCHANT_ID = ? ");
			sql.append("AND UT.MERCHANT_ID = ? ");
			
			param.setLong(merchantId);
		}
		
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ML";
			sortField = "name";
		}
		if(sortField.equalsIgnoreCase("campaignName")){
			alias = "CL";
			sortField = "name";
		}
		if(sortField.equalsIgnoreCase("giftName")){
			alias = "GL";
			sortField = "name";
		}
				
		return getPagingList(UserStampHistoryDetailDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public Long findUserTotalStampNo(final Long userId, final Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT IFNULL(COLLECT_STAMP_QTY,0) FROM USER_STAMP_CARD WHERE CAMPAIGN_ID = ? AND USER_ID = ? AND IS_DELETED = 0 ");
		param.setLong(campaignId);
		param.setLong(userId);
		
		return queryForLong(sql.toString(),param);
	}

	@Override
	public List<UserStampHistoryReportDTO> findUserStampHistoryReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final List<Integer> types) {
		final StringBuilder sql = new StringBuilder();
		final Map<String, Object> paramMap = new HashMap<String,Object>();
		
		
		
		sql.append("SELECT * FROM ( ")
			.append("SELECT ")
			.append("IF(UT.IS_FROM_RESERVATION = TRUE, UT.RESERVATION_PICKUP_DATE, UT.CREATED_TIME) AS CREATED_TIME, ")
			.append("IF(UT.IS_FROM_RESERVATION = TRUE, UT.CREATED_TIME, NULL) AS RESERVATION_TIME, ")
			.append("UT.TRANS_DATE_TIME,UT.STAMP_QTY,UT.CASH_QTY,UT.CREATED_BY,U.USER_NAME,UT.EXTERNAL_STORE_ID,UT.EXCHANGE_TYPE_ID,UT.TRANS_NO,UT.REDEEM_CODE,UT.TYPE,UT.TRANS_AMT, ") 
			.append("IF(UT.RELATED_USER_COUPON,1,0) AS IS_USED_COUPON, ")
			.append("ET.EXTERNAL_RULE_CODE, ");
	
		if(types.contains(UserStampHistory.UserStampHistoryType.TRANSFER_IN_STAMPS.toValue()) || types.contains(UserStampHistory.UserStampHistoryType.TRANSFER_OUT_STAMPS.toValue())){
			sql.append("TU.USER_NAME AS RECEIVER_USER_NAME, ")
				.append("TU.PHONE_AREA_CODE AS RECEIVER_USER_PHONE_AREA_CODE,TU.PHONE AS RECEIVER_USER_PHONE, ");
		}
		
		sql.append("ML.NAME AS MERCHANT_NAME,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,M.TIME_ZONE AS MERCHANT_TIME_ZONE, ")
			.append("U.PHONE_AREA_CODE AS USER_PHONE_AREA_CODE,U.PHONE AS USER_PHONE ")
			.append("FROM USER_STAMP_HISTORY UT ");
		
		if(types.contains(UserStampHistory.UserStampHistoryType.TRANSFER_IN_STAMPS.toValue()) || types.contains(UserStampHistory.UserStampHistoryType.TRANSFER_OUT_STAMPS.toValue())){
			sql.append("LEFT JOIN USER TU ON TU.ID = UT.TRANS_USER_ID AND TU.IS_DELETED = 0 ");
		}
	
		sql.append("LEFT JOIN USER U ON U.ID = UT.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CG ON CG.ID = UT.CAMPAIGN_GIFT_MAP_ID AND CG.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_EXCHANGE_TYPE ET ON ET.ID = UT.EXCHANGE_TYPE_ID AND ET.IS_DELETED = 0 ")
			.append("LEFT JOIN USER_STAMP_CARD USC ON USC.ID = UT.USER_STAMP_CARD_ID AND USC.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = USC.CAMPAIGN_ID AND U.LANG_CODE = CL.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UT.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CG.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE AND GL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ");

		sql.append("WHERE UT.IS_DELETED = 0 AND UT.TYPE IN (:types) ");
		
		paramMap.put("types", types);
		
		if(null != merchantId){
			sql.append("AND UT.MERCHANT_ID = :merchantId ");
			
			paramMap.put("merchantId", merchantId);
		}
		
		if(null != campaignId){
			sql.append("AND USC.CAMPAIGN_ID = :campaignId ");
			
			paramMap.put("campaignId", campaignId);
		}
		
		if(StringUtils.isNotBlank(startTime)){
			sql.append("AND UT.CREATED_TIME >= CONVERT_TZ(:startTime, M.TIME_ZONE, :startDateTimeZone)");
			
			paramMap.put("startTime", startTime+" 00:00:00 ");
			paramMap.put("startDateTimeZone",TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(endTime)){
			sql.append("AND UT.CREATED_TIME <= CONVERT_TZ(:endTime, M.TIME_ZONE, :endDateTimeZone)");
			
			paramMap.put("endTime", endTime+" 23:59:59");
			paramMap.put("endDateTimeZone",TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		sql.append(" ) AS TMP ")
			.append("ORDER BY TMP.CREATED_TIME ASC ");
		
		return queryForListWithNamedParam(sql.toString(),UserStampHistoryReportDTO.class, paramMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserStampHistoryReportDTO> pagingFindUserStampHistoryReport(final Map<String, Object> criteriaMap, String sortField, final String sortType, final int[] args) {
		String alias = "TMP";

		final Long campaignId = (Long) criteriaMap.get("campaignId");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final String startTime = (String) criteriaMap.get("startTime");
		final String endTime = (String) criteriaMap.get("endTime");
		final Boolean isTransfer = (Boolean) criteriaMap.get("isTransfer");
		final Integer type = (Integer) criteriaMap.get("type");
		final List<Integer> types = (List<Integer>) criteriaMap.get("types");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM USER_STAMP_HISTORY UT ");
		
		if(null != isTransfer && isTransfer){
			sqlCount.append("LEFT JOIN USER TU ON TU.ID = UT.TRANS_USER_ID AND TU.IS_DELETED = 0 ");
		}
		
		sqlCount.append("LEFT JOIN USER U ON U.ID = UT.USER_ID AND U.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_GIFT_MAP CG ON CG.ID = UT.CAMPAIGN_GIFT_MAP_ID AND CG.IS_DELETED = 0 ")
				.append("LEFT JOIN USER_STAMP_CARD USC ON USC.ID = UT.USER_STAMP_CARD_ID AND USC.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_GIFT_EXCHANGE_TYPE ET ON ET.ID = UT.EXCHANGE_TYPE_ID AND ET.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CG.CAMPAIGN_ID AND U.LANG_CODE = CL.LANG_CODE AND CL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UT.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CG.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE AND GL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ");
		
		sql.append("SELECT * FROM ( ")
			.append("SELECT ")
			.append("IF(UT.IS_FROM_RESERVATION = TRUE, UT.RESERVATION_PICKUP_DATE, UT.CREATED_TIME) AS CREATED_TIME,UT.TRANS_AMT, ")
			.append("IF(UT.RELATED_USER_COUPON,1,0) AS IS_USED_COUPON, ")
			.append("IF(UT.IS_FROM_RESERVATION = TRUE, UT.CREATED_TIME, NULL) AS RESERVATION_TIME, ")
			.append("UT.TRANS_DATE_TIME,UT.STAMP_QTY,UT.CASH_QTY,UT.CREATED_BY,U.USER_NAME,UT.EXTERNAL_STORE_ID,UT.EXCHANGE_TYPE_ID,UT.TRANS_NO,UT.REDEEM_CODE,UT.TYPE, ")
			.append("ET.EXTERNAL_RULE_CODE, ");
		
		if(null != isTransfer && isTransfer){
			sql.append("TU.USER_NAME AS RECEIVER_USER_NAME, ")
				.append("TU.PHONE_AREA_CODE AS RECEIVER_USER_PHONE_AREA_CODE,TU.PHONE AS RECEIVER_USER_PHONE, ");
		}
		
		sql.append("ML.NAME AS MERCHANT_NAME,CL.NAME AS CAMPAIGN_NAME,GL.NAME AS GIFT_NAME,M.TIME_ZONE AS MERCHANT_TIME_ZONE, ")
			.append("U.PHONE_AREA_CODE AS USER_PHONE_AREA_CODE,U.PHONE AS USER_PHONE ")
			.append("FROM USER_STAMP_HISTORY UT ");
		
		if(null != isTransfer && isTransfer){
			sql.append("LEFT JOIN USER TU ON TU.ID = UT.TRANS_USER_ID AND TU.IS_DELETED = 0 ");
		}
		
		sql.append("LEFT JOIN USER U ON U.ID = UT.USER_ID AND U.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CG ON CG.ID = UT.CAMPAIGN_GIFT_MAP_ID AND CG.IS_DELETED = 0 ")
			.append("LEFT JOIN USER_STAMP_CARD USC ON USC.ID = UT.USER_STAMP_CARD_ID AND USC.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_GIFT_EXCHANGE_TYPE ET ON ET.ID = UT.EXCHANGE_TYPE_ID AND ET.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = USC.CAMPAIGN_ID AND U.LANG_CODE = CL.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = UT.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = CG.GIFT_ID AND U.LANG_CODE = GL.LANG_CODE AND GL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ");
		
		sqlCount.append("WHERE UT.IS_DELETED = 0 ");
		sql.append("WHERE UT.IS_DELETED = 0 ");
		
		if(null != type){
			sqlCount.append("AND UT.TYPE = ? ");
			sql.append("AND UT.TYPE = ? ");
			
			param.setInt(type);
		}
		
		if(null != types && types.size() > 0){
			final StringBuilder userStampHistoryTypeStr = new StringBuilder();
			for(Integer userStampHistoryType : types){
				userStampHistoryTypeStr.append(userStampHistoryType);
				userStampHistoryTypeStr.append(",");
			}
			userStampHistoryTypeStr.delete(userStampHistoryTypeStr.length()-1, userStampHistoryTypeStr.length());
			
			sqlCount.append("AND UT.TYPE IN ( ")
					.append(userStampHistoryTypeStr.toString())
					.append(" ) ");
					
			sql.append("AND UT.TYPE IN ( ")
				.append(userStampHistoryTypeStr.toString())
				.append(" ) ");
		}

		if(null != campaignId){
			sqlCount.append("AND USC.CAMPAIGN_ID = ? ");
			sql.append("AND USC.CAMPAIGN_ID = ? ");
			
			param.setLong(campaignId);
		}
		
		if(null != merchantId){
			sqlCount.append("AND UT.MERCHANT_ID = ? ");
			sql.append("AND UT.MERCHANT_ID = ? ");
			
			param.setLong(merchantId);
		}
		
		if(StringUtils.isNotBlank(startTime)){
			sqlCount.append("AND UT.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
			sql.append("AND UT.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
			
			param.setString(startTime + " 00:00:00");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(StringUtils.isNotBlank(endTime)){
			sqlCount.append("AND UT.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
			sql.append("AND UT.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
			
			param.setString(endTime + " 23:59:59");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		
		if(null != isTransfer && isTransfer){
			sqlCount.append("AND UT.TYPE = ? ");
			sql.append("AND UT.TYPE = ? ");
			
			param.setInt(UserStampHistory.UserStampHistoryType.TRANSFER_OUT_STAMPS.toValue());
		}
		
		sql.append(" ) AS TMP ");
				
		return getPagingList(UserStampHistoryReportDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public Long findTotalByType(final Long campaignId,final List<Integer> userStampHistoryTypes) {
		final StringBuilder sql = new StringBuilder();
		final Map<String,Object> param = new HashMap<String,Object>();
		
		sql.append("SELECT SUM(UH.STAMP_QTY) FROM USER_STAMP_HISTORY UH ")
			.append("LEFT JOIN USER_STAMP_CARD UC ON UC.ID = UH.USER_STAMP_CARD_ID AND UC.IS_DELETED = 0 ")
			.append("WHERE UH.IS_DELETED = 0 AND UC.CAMPAIGN_ID = :campaignId ")
			.append("AND TYPE IN ( :userStampHistoryTypes ) ");
		
		param.put("campaignId", campaignId);
		param.put("userStampHistoryTypes", userStampHistoryTypes);
		
		return queryForLongWithNamedParam(sql.toString(), param);
	}

	@Override
	public Long findTotalItemCountByType(Long campaignId, List<Integer> userStampHistoryTypes) {
		final StringBuilder sql = new StringBuilder();
		final Map<String,Object> param = new HashMap<String,Object>();
		
		sql.append("SELECT COUNT(UH.STAMP_QTY) FROM USER_STAMP_HISTORY UH ")
			.append("LEFT JOIN USER_STAMP_CARD UC ON UC.ID = UH.USER_STAMP_CARD_ID AND UC.IS_DELETED = 0 ")
			.append("WHERE UH.IS_DELETED = 0 AND UC.CAMPAIGN_ID = :campaignId ")
			.append("AND TYPE IN ( :userStampHistoryTypes ) ");
		
		param.put("campaignId", campaignId);
		param.put("userStampHistoryTypes", userStampHistoryTypes);
		
		return queryForLongWithNamedParam(sql.toString(), param);
	}

}
