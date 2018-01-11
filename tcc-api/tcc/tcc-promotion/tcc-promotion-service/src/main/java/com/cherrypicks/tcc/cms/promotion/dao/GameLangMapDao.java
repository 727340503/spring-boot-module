package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.GameLangMapDTO;
import com.cherrypicks.tcc.model.GameLangMap;

public interface GameLangMapDao extends IBaseDao<GameLangMap> {

	List<GameLangMapDTO> findByGameId(Long gameId);

	boolean delByGameIds(List<Object> gameIds);


}
