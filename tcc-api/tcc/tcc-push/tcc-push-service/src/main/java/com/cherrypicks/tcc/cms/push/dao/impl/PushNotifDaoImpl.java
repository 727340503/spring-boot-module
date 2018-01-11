package com.cherrypicks.tcc.cms.push.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.PushNotifItemDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.push.dao.PushNotifDao;
import com.cherrypicks.tcc.model.PushNotif;

@Repository
public class PushNotifDaoImpl extends AbstractBaseDao<PushNotif> implements PushNotifDao {

	@Override
	public List<PushNotifItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		String alias = "P";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final Integer type = (Integer) criteriaMap.get("type");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM PUSH_NOTIF P ")
				.append("LEFT JOIN PUSH_NOTIF_LANG_MAP PN ON PN.PUSH_NOTIF_ID = P.ID ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = P.MERCHANT_ID AND PN.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ");
		
//		sql.append("SELECT P.ID,ML.NAME AS MERCHANT_NAME,P.STATUS,P.CREATED_TIME,PN.MSG FROM PUSH_NOTIF P ")
		sql.append("SELECT P.ID,ML.NAME AS MERCHANT_NAME,P.STATUS,P.UPDATED_TIME,PN.MSG FROM PUSH_NOTIF P ")
			.append("LEFT JOIN PUSH_NOTIF_LANG_MAP PN ON PN.PUSH_NOTIF_ID = P.ID ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = P.MERCHANT_ID AND PN.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ");
		
		sqlCount.append("WHERE P.IS_DELETED = 0 AND PN.IS_DELETED = 0 ");
		sql.append("WHERE P.IS_DELETED = 0 AND PN.IS_DELETED = 0 ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND PN.LANG_CODE = ? ");
			sql.append("AND PN.LANG_CODE = ? ");
		
			param.setString(langCode);
		}else{
			sqlCount.append("AND ML.IS_DEFAULT = ? ");
			sql.append("AND ML.IS_DEFAULT = ? ");
		
			param.setBool(true);
		}
		
		if(null != merchantId){
			sqlCount.append("AND P.MERCHANT_ID = ? ");
			sql.append("AND P.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(null != type){
			sqlCount.append("AND P.TYPE = ? ");
			sql.append("AND P.TYPE = ? ");
		
			param.setInt(type);
		}else{
			sqlCount.append("AND P.TYPE = ? ");
			sql.append("AND P.TYPE = ? ");
		
			param.setInt(PushNotif.Type.CMS.getCode());
		}
		if(sortField.equalsIgnoreCase("langCode")){
			alias = "PN";
		}
		
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ML";
			sortField = "name";
		}
		
		return getPagingList(PushNotifItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}


}
