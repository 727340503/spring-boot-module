package com.cherrypicks.tcc.cms.push.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.PushNotifLangMapDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.push.dao.PushNotifLangMapDao;
import com.cherrypicks.tcc.model.PushNotifLangMap;

@Repository
public class PushNotifLangMapDaoImpl extends AbstractBaseDao<PushNotifLangMap> implements PushNotifLangMapDao {

	@Override
	public List<PushNotifLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<PushNotifLangMapDTO> findByPushNotifId(Long pushNotifId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT PL.ID,PL.LANDING_IMG,PL.LANDING_URL,PL.MSG,PL.LANG_CODE,ML.NAME AS MERCHANT_NAME FROM PUSH_NOTIF_LANG_MAP PL ")
			.append("LEFT JOIN PUSH_NOTIF P ON P.ID = PL.PUSH_NOTIF_ID ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = P.MERCHANT_ID AND ML.LANG_CODE = PL.LANG_CODE ")
			.append("WHERE PL.IS_DELETED = 0 AND P.IS_DELETED = 0 AND ML.IS_DELETED = 0 AND PL.PUSH_NOTIF_ID = ? ");
		
		param.setLong(pushNotifId);
		
		return queryForList(sql.toString(), PushNotifLangMapDTO.class, param);
	}


}
