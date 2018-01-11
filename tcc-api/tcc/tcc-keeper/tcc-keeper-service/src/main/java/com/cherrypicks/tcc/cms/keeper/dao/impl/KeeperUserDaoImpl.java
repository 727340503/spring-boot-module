package com.cherrypicks.tcc.cms.keeper.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.KeeperUserDetailDTO;
import com.cherrypicks.tcc.cms.dto.KeeperUserItemDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.keeper.dao.KeeperUserDao;
import com.cherrypicks.tcc.model.KeeperUser;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Repository
public class KeeperUserDaoImpl extends AbstractBaseDao<KeeperUser> implements KeeperUserDao {

	@Override
	public List<KeeperUserItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		
		String alias = "TEMP";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final String userName =  (String) criteriaMap.get("userName");
		final Long merchantId =  (Long) criteriaMap.get("merchantId");
		final Long storeId =  (Long) criteriaMap.get("storeId");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM ( ")
				.append("SELECT K.ID ")
				.append("FROM KEEPER_USER K  ")
				.append("LEFT JOIN MERCHANT M ON M.ID = K.MERCHANT_ID AND M.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = K.MERCHANT_ID AND ML.IS_DELETED = 0 ")
				.append("LEFT JOIN STORE_LANG_MAP SL ON SL.STORE_ID = K.STORE_ID AND SL.LANG_CODE = ML.LANG_CODE AND SL.IS_DELETED = 0 ")
//				.append("LEFT JOIN STORE S ON S.ID = K.STORE_ID AND S.IS_DELETED = 0 ")
				.append("WHERE K.IS_DELETED = 0 ");

		
		sql.append("SELECT * FROM ( ")
			.append("SELECT ")
//			.append("K.ID,K.USER_NAME,K.STATUS,S.EXTERNAL_STORE_ID, ")
			.append("K.ID,K.USER_NAME,K.STATUS,SL.NAME AS STORE_NAME, ")
			.append("CONVERT_TZ(K.CREATED_TIME,\"")
			.append(TimeZoneConvert.DEFAULT_TIMEZONE)
			.append("\", M.TIME_ZONE) AS CREATED_TIME ")
			.append("FROM KEEPER_USER K  ")
			.append("LEFT JOIN MERCHANT M ON M.ID = K.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = K.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN STORE_LANG_MAP SL ON SL.STORE_ID = K.STORE_ID AND SL.LANG_CODE = ML.LANG_CODE AND SL.IS_DELETED = 0 ")
//			.append("LEFT JOIN STORE S ON S.ID = K.STORE_ID AND S.IS_DELETED = 0 ")
			.append("WHERE K.IS_DELETED = 0 ");
		
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
			sqlCount.append("AND K.MERCHANT_ID = ? ");
			sql.append("AND K.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(null != storeId){
			sqlCount.append("AND K.STORE_ID = ? ");
			sql.append("AND K.STORE_ID = ? ");
		
			param.setLong(storeId);
		}
		
		if(StringUtils.isNotBlank(userName)){
			sqlCount.append("AND K.USER_NAME REGEXP ? ");
			sql.append("AND K.USER_NAME REGEXP ? ");
		
			param.setString(userName);
		}
		
		sql.append(" ) AS TEMP ");
		sqlCount.append(" ) AS TEMP ");
		
		return getPagingList(KeeperUserItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public KeeperUser findByUserNameAndMerchantId(String userName, Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM KEEPER_USER ")
			.append("WHERE MERCHANT_ID = ? AND USER_NAME = ? AND IS_DELETED = 0 ");
		
		param.setLong(merchantId);
		param.setString(userName);

		return query(sql.toString(), KeeperUser.class, param);
	}

	@Override
	public KeeperUserDetailDTO findDetailById(final Long keeperUserId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT K.USER_NAME,K.STAFF_ID,K.STATUS,K.MERCHANT_ID,K.STORE_ID,K.EMAIL,K.MOBILE, ")
			.append("S.EXTERNAL_STORE_ID,SL.NAME AS STORE_NAME,ML.NAME AS MERCHANT_NAME ")
			.append("FROM KEEPER_USER K ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = K.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN STORE S ON S.ID = K.STORE_ID AND S.IS_DELETED = 0 ")
			.append("LEFT JOIN STORE_LANG_MAP SL ON SL.STORE_ID = K.STORE_ID AND SL.LANG_CODE = ML.LANG_CODE AND SL.IS_DELETED = 0 ")
			.append("WHERE K.ID = ? AND K.IS_DELETED = 0 AND ML.IS_DEFAULT = ? ");
		
		param.setLong(keeperUserId);
		param.setBool(true);
		
		return query(sql.toString(), KeeperUserDetailDTO.class,param);
	}

	@Override
	public KeeperUser findByStaffIdAndMerchantId(final String staffId, final Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM KEEPER_USER ")
			.append("WHERE MERCHANT_ID = ? AND STAFF_ID = ? AND IS_DELETED = 0 ");
		
		param.setLong(merchantId);
		param.setString(staffId);

		return query(sql.toString(), KeeperUser.class, param);
	}

}
