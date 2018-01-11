package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.GameLangMapDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.GameLangMapDao;
import com.cherrypicks.tcc.model.GameLangMap;

@Repository
public class GameLangMapDaoImpl extends AbstractBaseDao<GameLangMap> implements GameLangMapDao {

	@Override
	public List<GameLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameLangMapDTO> findByGameId(Long gameId) {

		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();

		sql.append("SELECT GL.ID,GL.img,GL.LANG_CODE,ML.NAME AS MERCHANT_NAME,GL.NAME FROM GAME_LANG_MAP GL ")
			.append("LEFT JOIN GAME G ON G.ID = GL.GAME_ID AND G.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.LANG_CODE = GL.LANG_CODE AND ML.MERCHANT_ID = G.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("WHERE GL.GAME_ID = ? ");

		param.setLong(gameId);

		return queryForList(sql.toString(), GameLangMapDTO.class, param);
	}

	@Override
	public boolean delByGameIds(List<Object> gameIds) {
		final StringBuilder sql = new StringBuilder("DELETE FROM GAME_LANG_MAP WHERE GAME_ID IN (:gameIds)");
		final Map<String, List<Object>> paramMap = Collections.singletonMap("gameIds", gameIds);
		return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

}
