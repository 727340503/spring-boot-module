package com.cherrypicks.tcc.cms.customer.dao;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.model.UserStampCard;

public interface UserStampCardDao extends IBaseDao<UserStampCard> {

	UserStampCard findByUserAndCampaign(final Long customerId, final Long campaignId);

	Long findCampaignTotalUserCount(final Long campaignId);

}
