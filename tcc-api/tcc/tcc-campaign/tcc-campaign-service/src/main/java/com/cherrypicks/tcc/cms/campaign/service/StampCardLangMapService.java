package com.cherrypicks.tcc.cms.campaign.service;

import java.util.List;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.StampCard;
import com.cherrypicks.tcc.model.StampCardLangMap;

public interface StampCardLangMapService extends IBaseService<StampCardLangMap>{

	void createStampLangMap(List<String> needAddStampCardLangs, String defaultMerchantLangCode, StampCard stampCard,String langData,
			String lang);

	void updateStampLangMap(StampCard stampCard, String langData, String lang);


}
