package com.cherrypicks.tcc.cms.promotion.service;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.GameDetailDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.Game;

public interface GameService extends IBaseService<Game>{

	GameDetailDTO findDetailById(final Long userId, final Long gameId, final String lang);

	Game createGame(final Long userId, final Long merchantId, final String webUrl, final Integer inappOpen,final Date startTime,final Date endTime,final String langData, final String lang);

	Game updateGame(final Long userId, final Long gameId, final String webUrl, final Integer status,final Integer inappOpen,final Date startTime,final Date endTime, final String langData, final String lang);

	void delByIds(final Long userId, final Long userMerchantId,final String ids, final String lang);

	List<HomePageItemDTO> findHomePageGameList(final Long userId, final Long merchantId, final Integer status, final String lang);

}
