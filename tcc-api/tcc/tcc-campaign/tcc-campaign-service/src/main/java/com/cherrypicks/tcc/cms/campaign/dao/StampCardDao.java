package com.cherrypicks.tcc.cms.campaign.dao;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.model.StampCard;

public interface StampCardDao extends IBaseDao<StampCard> {

	StampCard findByCampaignId(Long campaignId);

}
