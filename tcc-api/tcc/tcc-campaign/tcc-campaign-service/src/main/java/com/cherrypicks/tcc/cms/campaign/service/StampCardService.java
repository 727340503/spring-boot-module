package com.cherrypicks.tcc.cms.campaign.service;

import java.util.List;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.model.StampCard;

public interface StampCardService extends IBaseService<StampCard>{

	StampCard addStampCard(final List<String> needAddStampCardLangs, final String defaultMerchantLangCode, final Campaign campaign,final String langData, final String lang);

	StampCard findbyCampaignId(Long campaignId);

	void updateStampCard(Campaign campaign, String langData, String lang);


}
