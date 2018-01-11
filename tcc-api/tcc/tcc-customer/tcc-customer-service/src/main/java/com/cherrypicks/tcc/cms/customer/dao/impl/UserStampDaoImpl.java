package com.cherrypicks.tcc.cms.customer.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.customer.dao.UserStampDao;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.model.UserStamp;

@Repository
public class UserStampDaoImpl extends AbstractBaseDao<UserStamp> implements UserStampDao {

	@Override
	public List<UserStamp> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		return null;
	}

	@Override
	public long findUserCountByStampIdList(List<Long> stampIds) {
		final String sql = "SELECT COUNT(ID) FROM USER_STAMP WHERE STAMP_ID IN (:stampIds) AND IS_DELETED = 0";
		Map<String, List<Long>> paramMap = Collections.singletonMap("stampIds", stampIds);
		return super.queryForLongWithNamedParam(sql, paramMap); 
	}

	@Override
	public List<Long> findUserUnRedeemedStamps(final Long customerId, final Long userStampCardId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID FROM USER_STAMP WHERE IS_DELETED = 0 AND STATUS = ? AND USER_ID = ? AND USER_STAMP_CARD_ID = ? ");
		
		param.setInt(UserStamp.UserStampStatus.ACTIVE.toValue());
		param.setLong(customerId);
		param.setLong(userStampCardId);
		
		return queryForLongs(sql.toString(), param);
	}

}
