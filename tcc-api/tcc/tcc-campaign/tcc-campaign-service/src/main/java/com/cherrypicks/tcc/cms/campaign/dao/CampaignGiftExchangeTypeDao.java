package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.model.CampaignGiftExchangeType;

public interface CampaignGiftExchangeTypeDao extends IBaseDao<CampaignGiftExchangeType> {

	boolean delByCampaignGiftMapIds(List<Object> campaignGiftMapIds);

	List<CampaignGiftExchangeType> findByCampGiftMapId(Long campaignGiftMapId);

}
