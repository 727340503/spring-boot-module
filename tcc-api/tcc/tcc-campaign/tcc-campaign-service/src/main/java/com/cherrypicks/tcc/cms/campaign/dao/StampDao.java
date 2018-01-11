package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.StampDetailDTO;
import com.cherrypicks.tcc.model.Stamp;

public interface StampDao extends IBaseDao<Stamp> {

	boolean delByStampIds(List<Long> stampIdList);

	List<StampDetailDTO> findDetailListByStampCardId(Long stampCardId);

	List<Stamp> findByStampCardId(final Long stampCardId);

	List<Stamp> findByStampIds(final List<Long> ids);

	int findCampaignStampTotalQty(final Long campaignId);


}
