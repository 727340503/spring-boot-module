package com.cherrypicks.tcc.cms.campaign.dao;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.model.StampCardLangMap;

public interface StampCardLangMapDao extends IBaseDao<StampCardLangMap> {

	StampCardLangMap findByStampCardIdAndLangCode(Long stampCardId, String langCode);


}
