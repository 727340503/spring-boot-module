package com.cherrypicks.tcc.cms.customer.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.customer.dao.UserStampCardDao;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.model.UserStampCard;

@Repository
public class UserStampCardDaoImpl extends AbstractBaseDao<UserStampCard> implements UserStampCardDao {

	@Override
	public List<UserStampCard> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		return null;
	}

	@Override
	public UserStampCard findByUserAndCampaign(final Long customerId, final Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM USER_STAMP_CARD WHERE IS_DELETED = 0 AND CAMPAIGN_ID = ? AND USER_ID = ? ");
		param.setLong(campaignId);
		param.setLong(customerId);
		
		return query(sql.toString(), UserStampCard.class, param);
	}

	@Override
	public Long findCampaignTotalUserCount(final Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT COUNT(1) FROM USER_STAMP_CARD UC ")
			.append("INNER JOIN ( ")
			.append("SELECT US.USER_ID FROM USER_STAMP_HISTORY US WHERE US.IS_DELETED = 0 GROUP BY US.USER_ID ")
			.append(" ) USH ON USH.USER_ID = UC.USER_ID ")
			.append("WHERE UC.IS_DELETED = 0 AND UC.CAMPAIGN_ID = ? ");
		param.setLong(campaignId);
		
		return queryForLong(sql.toString(), param);
	}

}
