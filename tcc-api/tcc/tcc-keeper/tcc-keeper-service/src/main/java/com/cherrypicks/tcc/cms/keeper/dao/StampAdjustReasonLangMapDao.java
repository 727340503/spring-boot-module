package com.cherrypicks.tcc.cms.keeper.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.StampAdjustReasonLangMapDTO;
import com.cherrypicks.tcc.model.StampAdjustReasonLangMap;

public interface StampAdjustReasonLangMapDao extends IBaseDao<StampAdjustReasonLangMap> {

	boolean delByStampAdjustReasonIds(final List<Object> stampAdjustReasonIds);

	List<StampAdjustReasonLangMapDTO> findByStampAdjustReasonId(final Long stampAdjustReasonId);


}
