package com.cherrypicks.tcc.cms.merchant.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.StoreLangMapDTO;
import com.cherrypicks.tcc.model.StoreLangMap;

public interface StoreLangMapDao extends IBaseDao<StoreLangMap> {

	List<StoreLangMapDTO> findStoreLagnMap(Long id);


}
