package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.CampaignLangMapDetailDTO;
import com.cherrypicks.tcc.model.CampaignLangMap;

public interface CampaignLangMapDao extends IBaseDao<CampaignLangMap> {

	List<CampaignLangMap> findByName(final String name);

	CampaignLangMap findbyCampaignId(final Long campaignId);

	List<CampaignLangMapDetailDTO> findDetailByCampaignId(Long campaignId);

	CampaignLangMap findCampaignDefaultLangMap(Long campaignId);

}
