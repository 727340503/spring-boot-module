package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.GameItemDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.GameDao;
import com.cherrypicks.tcc.model.Game;
import com.cherrypicks.tcc.util.DateUtil;

@Repository
public class GameDaoImpl extends AbstractBaseDao<Game> implements GameDao {

	@Override
	public List<GameItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		String alias = "G";
		
		final Integer status = (Integer) criteriaMap.get("status");
		final String langCode = (String) criteriaMap.get("langCode");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final String name = (String) criteriaMap.get("name");

		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM GAME G ")
				.append("LEFT JOIN GAME_LANG_MAP GL ON GL.GAME_ID = G.ID AND GL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = G.MERCHANT_ID AND GL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("WHERE G.IS_DELETED = 0 ");
		
		sql.append("SELECT G.ID,ML.NAME AS MERCHANT_NAME,G.MERCHANT_ID,G.CREATED_TIME,G.STATUS,GL.LANG_CODE,G.START_TIME,G.END_TIME,GL.NAME FROM GAME G ")
			.append("LEFT JOIN GAME_LANG_MAP GL ON GL.GAME_ID = G.ID AND GL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = G.MERCHANT_ID AND GL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("WHERE G.IS_DELETED = 0 ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND ML.LANG_CODE = ? ");
			sql.append("AND ML.LANG_CODE = ? ");
			
			param.setString(langCode);
		}else{
			sqlCount.append("AND ML.IS_DEFAULT = ? ");
			sql.append("AND ML.IS_DEFAULT = ? ");
			
			param.setBool(true);
		}
		
		if(null != status){
			if(status.intValue() == Game.Status.ACTIVE.getCode()){
				sqlCount.append("AND G.STATUS = ? AND G.START_TIME < ? AND G.END_TIME > ? ");
				sql.append("AND G.STATUS = ? AND G.START_TIME < ? AND G.END_TIME > ? ");
				
				param.setInt(status);
				param.setDate(DateUtil.getCurrentDate());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Game.Status.EXPIRED.getCode()){
				sqlCount.append("AND G.STATUS = ? AND G.END_TIME < ? ");
				sql.append("AND G.STATUS = ? AND G.END_TIME < ? ");
				
				param.setInt(Game.Status.ACTIVE.getCode());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Game.Status.PENDING.getCode()){//pending
				sqlCount.append("AND G.STATUS = ? AND G.START_TIME > ?");
				sql.append("AND G.STATUS = ? AND G.START_TIME > ? ");
				
				param.setInt(Game.Status.ACTIVE.getCode());
				param.setDate(DateUtil.getCurrentDate());
			}else{
				sqlCount.append("AND G.STATUS = ? ");
				sql.append("AND G.STATUS = ? ");
				
				param.setInt(Game.Status.IN_ACTIVE.getCode());
			}
		}
		
		if(null != merchantId){
			sqlCount.append("AND G.MERCHANT_ID = ? ");
			sql.append("AND G.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append("AND GL.name REGEXP ? ");
			sql.append("AND GL.name REGEXP ? ");
		
			param.setString(name);
		}
		
		if(sortField.equalsIgnoreCase("name")){
			alias = "GL";
		}
		
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ML";
			sortField = "name";
		}
		
		return getPagingList(GameItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public List<HomePageItemDTO> findHomePageGameList(final Long merchantId, final Integer status) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT G.ID,G.STATUS,G.WEB_URL,GL.IMG,GL.NAME,G.START_TIME,G.END_TIME FROM GAME G ")
			.append("LEFT JOIN GAME_LANG_MAP GL ON GL.GAME_ID = G.ID AND GL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = G.MERCHANT_ID AND GL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("WHERE G.IS_DELETED = 0 AND ML.IS_DEFAULT = ? AND G.MERCHANT_ID = ? ");
		
		param.setBool(true);
		param.setLong(merchantId);
		
		if(null != status){
			if(status.intValue() == Game.Status.ACTIVE.getCode()){
				sql.append("AND G.STATUS = ? AND G.START_TIME < ? AND G.END_TIME > ? ");
				
				param.setInt(status);
				param.setDate(DateUtil.getCurrentDate());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Game.Status.EXPIRED.getCode()){
				sql.append("AND G.STATUS = ? AND G.END_TIME < ? ");
				
				param.setInt(Game.Status.ACTIVE.getCode());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Game.Status.PENDING.getCode()){//pending
				sql.append("AND G.STATUS = ? AND G.START_TIME > ? ");
				
				param.setInt(Game.Status.ACTIVE.getCode());
				param.setDate(DateUtil.getCurrentDate());
			}else{
				sql.append("AND G.STATUS = ? ");
				
				param.setInt(Game.Status.IN_ACTIVE.getCode());
			}
		}else{
			sql.append("AND G.STATUS = ? ");
			
			param.setInt(Game.Status.ACTIVE.getCode());
		}
		
		return queryForList(sql.toString(), HomePageItemDTO.class,param);
	}


}
