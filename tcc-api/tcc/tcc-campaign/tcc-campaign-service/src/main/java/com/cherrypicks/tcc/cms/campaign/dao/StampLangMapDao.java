package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.StampLangMapDetailDTO;
import com.cherrypicks.tcc.model.StampLangMap;

public interface StampLangMapDao extends IBaseDao<StampLangMap> {

	boolean delByStampIds(List<Long> stampIdList);

	List<StampLangMapDetailDTO> findByStampId(Long stampId);

	int updateImgByStampId(Long userId, String stampImg, Long stampId);


}
