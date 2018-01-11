package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.GiftLangMapDTO;
import com.cherrypicks.tcc.model.GiftLangMap;

public interface GiftLangMapDao extends IBaseDao<GiftLangMap> {

	boolean delByGiftIds(List<Object> idList);

	List<GiftLangMapDTO> findByGiftId(Long giftId);


}
