package com.cherrypicks.tcc.cms.customer.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.customer.dao.UserDao;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.UserDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserItemDTO;
import com.cherrypicks.tcc.cms.dto.UserReportDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.model.User;
import com.cherrypicks.tcc.model.UserSns;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Repository
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {

	@Override
	public List<UserItemDTO> findByFilter(final Map<String, Object> criteriaMap, String sortField, final String sortType, final int... args) {
		String alias = "U";

		final String langCode =  (String) criteriaMap.get("langCode");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final String userName =  (String) criteriaMap.get("userName");

		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(U.ID) FROM USER U ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND ML.IS_DELETED = 0 ");

		sql.append("SELECT U.ID,U.FIRST_NAME,U.LAST_NAME,U.PHONE,U.PHONE_AREA_CODE,U.USER_NAME,ML.NAME AS MERCHANT_NAME ")
			.append("FROM USER U ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND ML.IS_DELETED = 0 ");
		
		sqlCount.append("WHERE U.IS_DELETED = 0 ");
		sql.append("WHERE U.IS_DELETED = 0 ");
		
//		sqlCount.append("SELECT COUNT(*) FROM ( ")
//				.append("SELECT ")
//				.append("IFNULL( (SELECT CASE U.USER_TYPE WHEN ")
//				.append(User.UserType.EMAIL.toValue())
//				.append(" THEN U.IS_EMAIL_VALIDATION WHEN ")
//				.append(User.UserType.PHONE.toValue())
//				.append(" THEN U.IS_PHONE_VALIDATION END), 1) AS STATUS ")
//				.append("FROM USER U  ")
//				.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ")
//				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND ML.IS_DELETED = 0 ")
//				.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
//				.append("WHERE U.IS_DELETED = 0 ");
//
//
//		sql.append("SELECT * FROM ( ")
//			.append("SELECT ")
//			.append("U.ID,U.USER_NAME,U.FIRST_NAME,U.LAST_NAME,ML.NAME AS MERCHANT_NAME,U.USER_TYPE,US.SNS_TYPE, ")
//			.append("IFNULL( (SELECT CASE U.USER_TYPE WHEN ")
//			.append(User.UserType.EMAIL.toValue())
//			.append(" THEN U.IS_EMAIL_VALIDATION WHEN ")
//			.append(User.UserType.PHONE.toValue())
//			.append(" THEN U.IS_PHONE_VALIDATION END), 1) AS STATUS ")
//			.append("FROM USER U  ")
//			.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ")
//			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND ML.IS_DELETED = 0 ")
//			.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
//			.append("WHERE U.IS_DELETED = 0 ");

		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND ML.LANG_CODE = ? ");
			sql.append("AND ML.LANG_CODE = ? ");

			param.setString(langCode);
		}else{
			sqlCount.append("AND ML.IS_DEFAULT = ? ");
			sql.append("AND ML.IS_DEFAULT = ? ");

			param.setBool(true);
		}

		if(null != merchantId){
			sqlCount.append("AND U.MERCHANT_ID = ? ");
			sql.append("AND U.MERCHANT_ID = ? ");

			param.setLong(merchantId);
		}

		if(StringUtils.isNotBlank(userName)){
			sqlCount.append("AND U.USER_NAME REGEXP ? ");
			sql.append("AND U.USER_NAME REGEXP ? ");

			param.setString(userName);
		}
		
		if("merchantName".equalsIgnoreCase(sortField)) {
			alias = "ML";
			sortField = "name";
		}

//		if(null != status){
//			sql.append("WHERE TEMP.STATUS = ")
//				.append(status.intValue() == User.UserStatus.ACTIVE.toValue()?status:User.UserStatus.IN_ACTIVE.toValue());
//
//			sqlCount.append("WHERE TEMP.STATUS = ")
//				.append(status.intValue() == User.UserStatus.ACTIVE.toValue()?status:User.UserStatus.IN_ACTIVE.toValue());
//		}

		return getPagingList(UserItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public UserDetailDTO findDetailById(final Long id) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT U.ID,U.FIRST_NAME,U.LAST_NAME,U.USER_NAME,U.EMAIL,U.PHONE,U.PHONE_AREA_CODE,U.BIRTHDAY,U.GENDER,U.IS_EMAIL_VALIDATION,U.IS_MARKETING_INFO, ")
			.append("ML.MERCHANT_ID,US.SNS_ID AS FACEBOOK_ACCOUNT,ML.NAME AS MERCHANT_NAME ")
			.append(" FROM USER U ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 AND US.SNS_TYPE = ? ")
			.append("WHERE U.IS_DELETED = 0 AND U.ID = ? ");

//		sql.append("SELECT U.*,ML.NAME AS MERCHANT_NAME,US.SNS_TYPE, ")
//			.append("(SELECT CASE U.USER_TYPE WHEN ")
//			.append(UserType.EMAIL.toValue())
//			.append(" THEN U.IS_EMAIL_VALIDATION WHEN ")
//			.append(UserType.PHONE.toValue())
//			.append(" THEN U.IS_PHONE_VALIDATION END) AS status ")
//			.append("FROM USER U ")
//			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND U.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
//			.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
//			.append("WHERE U.ID = ? ");

		param.setInt(UserSns.UserSnsType.FACE_BOOK.toValue());
		param.setLong(id);
		return query(sql.toString(), UserDetailDTO.class,param);
	}

	@Override
	public List<Long> findUserIdsByMerchantId(final Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();

		sql.append("SELECT ID FROM USER WHERE MERCHANT_ID = ? AND IS_DELETED = 0 AND USER_TYPE != ? ");
		param.setLong(merchantId);
		param.setInt(User.UserType.TEMP_USER.toValue());

		return queryForLongs(sql.toString(), param);
	}

	@Override
	public List<UserReportDTO> findUserReport(final Long merchantId, final String startTime, final String endTime) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();

		sql.append("SELECT * FROM ( ")
			.append("SELECT U.USER_NAME,U.EMAIL,U.PHONE,U.PHONE_AREA_CODE,U.GENDER,U.BIRTHDAY,U.CREATED_TIME,U.IS_EMAIL_VALIDATION,U.IS_MARKETING_INFO,U.LAST_MOBILE_VERIFY_TIME, ")
			.append("US.SNS_ID,M.TIME_ZONE AS MERCHANT_TIME_ZONE,ML.NAME AS MERCHANT_NAME, ")
//			.append("IFNULL(SV.UPDATED_TIME,SV.CREATED_TIME) AS LAST_PHONE_VERIF_TIME, ")
			.append("SL.TOTAL_SEND_SMS_QTY ")
			.append("FROM USER U ")
			.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = M.ID AND ML.IS_DELETED = 0 AND ML.LANG_CODE = U.LANG_CODE ")
			.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
//			.append("LEFT JOIN SMS_VERIFY SV ON SV.PHONE = U.PHONE AND SV.MERCHANT_ID = U.MERCHANT_ID AND SV.IS_DELETED = 0 ")
			.append("LEFT JOIN ( ")
			.append("SELECT COUNT(S.ID) AS TOTAL_SEND_SMS_QTY,MERCHANT_ID,PHONE FROM SMS_LOG S GROUP BY MERCHANT_ID,PHONE,IS_DELETED HAVING S.IS_DELETED = 0 ") 
			.append(") AS SL ON SL.MERCHANT_ID = U.MERCHANT_ID AND SL.PHONE = U.PHONE ")
			.append("WHERE U.IS_DELETED = 0 ");

		if(null != merchantId){
			sql.append("AND U.MERCHANT_ID = ? ");

			param.setLong(merchantId);
		}

		if(StringUtils.isNotBlank(startTime)){
			sql.append("AND U.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");

			param.setString(startTime + " 00:00:00");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append("AND U.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");

			param.setString(endTime + " 23:59:59");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}

		sql.append(" ) AS TEMP ");

		sql.append(" ORDER BY CREATED_TIME ASC ");

		return queryForList(sql.toString(), UserReportDTO.class, param);
	}

	@Override
	public List<UserReportDTO> pagingFindUserReport(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final int[] args) {
		final String alias = "TEMP";

		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final String startTime =  (String) criteriaMap.get("startTime");
		final String endTime =  (String) criteriaMap.get("endTime");

		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(U.ID) FROM USER U ")
				.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = M.ID AND ML.IS_DELETED = 0 AND ML.LANG_CODE = U.LANG_CODE ")
				.append("WHERE U.IS_DELETED = 0 ");
		
		sql.append("SELECT * FROM ( ")
			.append("SELECT U.USER_NAME,U.EMAIL,U.PHONE,U.PHONE_AREA_CODE,U.GENDER,U.BIRTHDAY,U.CREATED_TIME,U.IS_EMAIL_VALIDATION,U.IS_MARKETING_INFO,U.LAST_MOBILE_VERIFY_TIME, ")
			.append("US.SNS_ID,M.TIME_ZONE AS MERCHANT_TIME_ZONE,ML.NAME AS MERCHANT_NAME, ")
//			.append("IFNULL(SV.UPDATED_TIME,SV.CREATED_TIME) AS LAST_PHONE_VERIF_TIME, ")
			.append("SL.TOTAL_SEND_SMS_QTY ")
			.append("FROM USER U ")
			.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = M.ID AND ML.IS_DELETED = 0 AND ML.LANG_CODE = U.LANG_CODE ")
			.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
//			.append("LEFT JOIN SMS_VERIFY SV ON SV.PHONE = U.PHONE AND SV.MERCHANT_ID = U.MERCHANT_ID AND SV.IS_DELETED = 0 ")
			.append("LEFT JOIN ( ")
			.append("SELECT COUNT(S.ID) AS TOTAL_SEND_SMS_QTY,MERCHANT_ID,PHONE FROM SMS_LOG S GROUP BY MERCHANT_ID,PHONE,IS_DELETED HAVING S.IS_DELETED = 0 ") 
			.append(") AS SL ON SL.MERCHANT_ID = U.MERCHANT_ID AND SL.PHONE = U.PHONE ")
			.append("WHERE U.IS_DELETED = 0 ");

//		sqlCount.append("SELECT COUNT(*) FROM ( ")
//				.append("SELECT ")
//				.append("IFNULL( (SELECT CASE U.USER_TYPE WHEN ")
//				.append(User.UserType.EMAIL.toValue())
//				.append(" THEN U.IS_EMAIL_VALIDATION WHEN ")
//				.append(User.UserType.PHONE.toValue())
//				.append(" THEN U.IS_PHONE_VALIDATION END), 1) AS status ")
//				.append("FROM USER U  ")
//				.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ")
//				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND ML.IS_DELETED = 0 AND U.LANG_CODE = ML.LANG_CODE ")
//				.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
//				.append("WHERE U.IS_DELETED = 0 ");

//		sql.append("SELECT * FROM ( ")
//			.append("SELECT ")
//			.append("U.ID,U.USER_NAME,U.FIRST_NAME,U.LAST_NAME,ML.NAME AS MERCHANT_NAME,U.USER_TYPE,US.SNS_TYPE,U.EMAIL,U.CONTACT_EMAIL, ")
//			.append("U.PHONE, U.GENDER, U.BIRTHDAY,M.TIME_ZONE AS MERCHANT_TIME_ZONE,U.IS_MARKETING_INFO, ")
////			.append("U.CREATED_TIME, ")
//			.append("CONVERT_TZ(U.CREATED_TIME,\"")
//			.append(TimeZoneConvert.DEFAULT_TIMEZONE)
//			.append("\", M.TIME_ZONE) AS CREATED_TIME, ")
//			.append("IFNULL( (SELECT CASE U.USER_TYPE WHEN ")
//			.append(User.UserType.EMAIL.toValue())
//			.append(" THEN U.IS_EMAIL_VALIDATION WHEN ")
//			.append(User.UserType.PHONE.toValue())
//			.append(" THEN U.IS_PHONE_VALIDATION END), 1) AS status ")
//			.append("FROM USER U  ")
//			.append("LEFT JOIN MERCHANT M ON M.ID = U.MERCHANT_ID AND M.IS_DELETED = 0 ")
//			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = U.MERCHANT_ID AND ML.IS_DELETED = 0 AND U.LANG_CODE = ML.LANG_CODE ")
//			.append("LEFT JOIN USER_SNS US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
//			.append("WHERE U.IS_DELETED = 0 ");

		if(null != merchantId){
			sqlCount.append("AND U.MERCHANT_ID = ? ");
			sql.append("AND U.MERCHANT_ID = ? ");

			param.setLong(merchantId);
		}

		if(StringUtils.isNotBlank(startTime)){
			sqlCount.append("AND U.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
			sql.append("AND U.CREATED_TIME >= CONVERT_TZ(?, M.TIME_ZONE, ?) ");

			param.setString(startTime + " 00:00:00");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}
		if(StringUtils.isNotBlank(endTime)){
			sqlCount.append("AND U.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");
			sql.append("AND U.CREATED_TIME <= CONVERT_TZ(?, M.TIME_ZONE, ?) ");

			param.setString(endTime + " 23:59:59");
			param.setString(TimeZoneConvert.DEFAULT_TIMEZONE);
		}

		sql.append(" ) AS TEMP ");

		return getPagingList(UserReportDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}


}
