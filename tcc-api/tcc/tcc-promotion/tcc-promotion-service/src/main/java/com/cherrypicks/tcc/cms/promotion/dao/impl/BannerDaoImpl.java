package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.BannerItemDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.BannerDao;
import com.cherrypicks.tcc.model.Banner;
import com.cherrypicks.tcc.util.DateUtil;

@Repository
public class BannerDaoImpl extends AbstractBaseDao<Banner> implements BannerDao {

	@Override
	public List<BannerItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		String alias = "B";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final Integer status = (Integer) criteriaMap.get("status");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final String name =  (String) criteriaMap.get("name");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM BANNER B ")
				.append("LEFT JOIN BANNER_LANG_MAP BL ON BL.BANNER_ID = B.ID AND BL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = B.MERCHANT_ID AND ML.LANG_CODE = BL.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("WHERE B.IS_DELETED = 0  ");
		
		sql.append("SELECT B.ID,B.MERCHANT_ID,ML.NAME AS MERCHANT_NAME,B.STATUS,B.SORT_ORDER,B.START_TIME,B.END_TIME,BL.NAME,B.UPDATED_TIME FROM BANNER B ")
				.append("LEFT JOIN BANNER_LANG_MAP BL ON BL.BANNER_ID = B.ID AND BL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = B.MERCHANT_ID AND ML.LANG_CODE = BL.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("WHERE B.IS_DELETED = 0  ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND BL.LANG_CODE = ? ");
			sql.append("AND BL.LANG_CODE = ? ");
		
			param.setString(langCode);
		}else{
			sqlCount.append("AND ML.IS_DEFAULT = ? ");
			sql.append("AND ML.IS_DEFAULT = ? ");
		
			param.setBool(true);
		}
		
		if(null != status){
			if(status.intValue() == Banner.BannerStatus.ACTIVE.toValue()){
				sqlCount.append("AND B.STATUS = ? AND B.START_TIME < ? AND B.END_TIME > ? ");
				sql.append("AND B.STATUS = ? AND B.START_TIME < ? AND B.END_TIME > ? ");
				
				param.setInt(status);
				param.setDate(DateUtil.getCurrentDate());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Banner.BannerStatus.EXPIRED.toValue()){
				sqlCount.append("AND B.STATUS = ? AND B.END_TIME < ? ");
				sql.append("AND B.STATUS = ? AND B.END_TIME < ? ");
				
				param.setInt(Banner.BannerStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Banner.BannerStatus.PENDING.toValue()){//pending
				sqlCount.append("AND B.STATUS = ? AND B.START_TIME > ?");
				sql.append("AND B.STATUS = ? AND B.START_TIME > ? ");
				
				param.setInt(Banner.BannerStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else{
				sqlCount.append("AND B.STATUS = ? ");
				sql.append("AND B.STATUS = ? ");
				
				param.setInt(Banner.BannerStatus.IN_ACTIVE.toValue());
			}
		}
		
		if(null != merchantId){
			sqlCount.append("AND B.MERCHANT_ID = ? ");
			sql.append("AND B.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append("AND BL.name REGEXP ? ");
			sql.append("AND BL.name REGEXP ? ");
		
			param.setString(name);
		}
		
		if(sortField.equalsIgnoreCase("name")){
			alias = "BL";
		}
		
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ML";
			sortField = "name"; 
		}
		
		return getPagingList(BannerItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public Integer findSortOrderByMerchantId(final Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT IFNULL(MAX(SORT_ORDER),0) FROM BANNER WHERE MERCHANT_ID = ? AND IS_DELETED = 0 ");
		param.setLong(merchantId);
		
		return queryForInt(sql.toString(),param) + 1;
	}

	@Override
	public List<HomePageItemDTO> findHomePageBannerList(Long merchantId, Integer status) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT B.ID,B.STATUS,B.WEB_URL, BL.IMG,BL.NAME,B.START_TIME,B.END_TIME FROM BANNER B ")
			.append("LEFT JOIN BANNER_LANG_MAP BL ON BL.BANNER_ID = B.ID AND BL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = B.MERCHANT_ID AND BL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("WHERE B.IS_DELETED = 0 AND ML.IS_DEFAULT = ? AND B.MERCHANT_ID = ? ");
		
		param.setBool(true);
		param.setLong(merchantId);
		
		if(null != status){
			if(status.intValue() == Banner.BannerStatus.ACTIVE.toValue()){
				sql.append("AND B.STATUS = ? AND B.START_TIME < ? AND B.END_TIME > ? ");
				
				param.setInt(status);
				param.setDate(DateUtil.getCurrentDate());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Banner.BannerStatus.EXPIRED.toValue()){
				sql.append("AND B.STATUS = ? AND B.END_TIME < ? ");
				
				param.setInt(Banner.BannerStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Banner.BannerStatus.PENDING.toValue()){//pending
				sql.append("AND B.STATUS = ? AND B.START_TIME > ? ");
				
				param.setInt(Banner.BannerStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else{
				sql.append("AND B.STATUS = ? ");
				
				param.setInt(Banner.BannerStatus.IN_ACTIVE.toValue());
			}
		}else{
			sql.append("AND B.STATUS = ? ");
			
			param.setInt(Banner.BannerStatus.ACTIVE.toValue());
		}
		
		return queryForList(sql.toString(), HomePageItemDTO.class,param);
	}


}
