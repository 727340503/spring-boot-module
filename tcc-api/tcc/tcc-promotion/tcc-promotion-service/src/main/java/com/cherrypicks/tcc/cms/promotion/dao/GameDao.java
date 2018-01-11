package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.model.Game;

public interface GameDao extends IBaseDao<Game> {

	List<HomePageItemDTO> findHomePageGameList(final Long merchantId, final Integer status);

}
