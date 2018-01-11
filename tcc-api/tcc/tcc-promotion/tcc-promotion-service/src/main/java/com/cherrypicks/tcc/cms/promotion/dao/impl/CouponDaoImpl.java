package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponItemDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.CouponDao;
import com.cherrypicks.tcc.model.Banner;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.ListUtil;

@Repository
public class CouponDaoImpl extends AbstractBaseDao<Coupon> implements CouponDao {

	@Override
	public List<CouponItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		String alias = "C";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final String statusStr = (String) criteriaMap.get("status");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final Long campaignId =  (Long) criteriaMap.get("campaignId");
		final String name =  (String) criteriaMap.get("name");
		final Integer type =  (Integer) criteriaMap.get("type");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM COUPON C ")
				.append("LEFT JOIN COUPON_LANG_MAP CL ON CL.COUPON_ID = C.ID AND CL.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN CA ON CA.ID = C.CAMPAIGN_ID AND CA.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP CAL ON CAL.CAMPAIGN_ID = CA.ID AND CAL.LANG_CODE = CL.LANG_CODE AND CAL.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = CA.MERCHANT_ID AND ML.LANG_CODE = CAL.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("WHERE C.IS_DELETED = 0  ");

		
		sql.append("SELECT C.ID,ML.NAME AS MERCHANT_NAME,C.STATUS,C.SORT_ORDER,C.COLLECT_START_TIME,C.COLLECT_END_TIME,CAL.NAME AS CAMPAIGN_NAME,CL.NAME,C.UPDATED_TIME FROM COUPON C ")
			.append("LEFT JOIN COUPON_LANG_MAP CL ON CL.COUPON_ID = C.ID AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN CA ON CA.ID = C.CAMPAIGN_ID AND CA.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CAL ON CAL.CAMPAIGN_ID = CA.ID AND CAL.LANG_CODE = CL.LANG_CODE AND CAL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = CA.MERCHANT_ID AND ML.LANG_CODE = CAL.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("WHERE C.IS_DELETED = 0  ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND CL.LANG_CODE = ? ");
			sql.append("AND CL.LANG_CODE = ? ");
		
			param.setString(langCode);
		}else{
			sqlCount.append("AND ML.IS_DEFAULT = ? ");
			sql.append("AND ML.IS_DEFAULT = ? ");
		
			param.setBool(true);
		}
		
		if(StringUtils.isNotBlank(statusStr)){
			
			List<String> statusStrList = ListUtil.toList(statusStr);
			List<Integer> statusList = ListUtil.toIntList(statusStrList);
			
			sqlCount.append("AND ( ");
			sql.append("AND ( ");
			
			for(int i = 0;i < statusList.size(); i++) {
				final int status = statusList.get(i);
				
				if(i > 0) {
					sqlCount.append("OR ");
					sql.append("OR ");
				}
				
				if(status == Coupon.CouponStatus.ACTIVE.toValue()){
					sqlCount.append(" ( C.STATUS = ? AND C.COLLECT_START_TIME < ? AND C.COLLECT_END_TIME > ? ) ");
					sql.append(" ( C.STATUS = ? AND C.COLLECT_START_TIME < ? AND C.COLLECT_END_TIME > ? ) ");
					
					param.setInt(status);
					param.setDate(DateUtil.getCurrentDate());
					param.setDate(DateUtil.getCurrentDate());
				}else if(status == Coupon.CouponStatus.EXPIRED.toValue()){
					sqlCount.append(" ( C.STATUS = ? AND C.COLLECT_END_TIME < ? ) ");
					sql.append(" ( C.STATUS = ? AND C.COLLECT_END_TIME < ? ) ");
					
					param.setInt(Coupon.CouponStatus.ACTIVE.toValue());
					param.setDate(DateUtil.getCurrentDate());
				}else if(status == Coupon.CouponStatus.PENDING.toValue()){//pending
					sqlCount.append(" ( C.STATUS = ? AND C.COLLECT_START_TIME > ? ) ");
					sql.append(" ( C.STATUS = ? AND C.COLLECT_START_TIME > ? ) ");
					
					param.setInt(Coupon.CouponStatus.ACTIVE.toValue());
					param.setDate(DateUtil.getCurrentDate());
				}else{
					sqlCount.append(" ( C.STATUS = ? ) ");
					sql.append(" ( C.STATUS = ? ) ");
					
					param.setInt(Banner.BannerStatus.IN_ACTIVE.toValue());
				}
			}
			
			sqlCount.append(") ");
			sql.append(") ");
			
		}
		
		if(null != merchantId){
			sqlCount.append("AND CA.MERCHANT_ID = ? ");
			sql.append("AND CA.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(null != campaignId) {
			sqlCount.append("AND CA.ID = ? ");
			sql.append("AND CA.ID = ? ");
		
			param.setLong(campaignId);
		}
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append("AND CL.NAME REGEXP ? ");
			sql.append("AND CL.NAME REGEXP ? ");
		
			param.setString(name);
		}
		
		if(null != type) {
			sqlCount.append("AND C.TYPE = ? ");
			sql.append("AND C.TYPE = ? ");
		
			param.setInt(type);
		}
		
		if(sortField.equalsIgnoreCase("name")){
			alias = "CL";
		}
		
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ML";
			sortField = "name"; 
		}
		if(sortField.equalsIgnoreCase("campaignName")){
			alias = "CAL";
			sortField = "name"; 
		}
		
		return getPagingList(CouponItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public List<HomePageItemDTO> findHomePageCouponList(Long merchantId, Integer status) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT C.ID,C.STATUS,CL.IMG,CL.NAME,C.COLLECT_START_TIME AS START_TIME,C.COLLECT_END_TIME AS END_TIME FROM COUPON C ")
			.append("LEFT JOIN COUPON_LANG_MAP CL ON CL.COUPON_ID = C.ID AND CL.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN CA ON CA.ID = C.CAMPAIGN_ID AND CA.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = CA.MERCHANT_ID AND CL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("WHERE C.IS_DELETED = 0 AND ML.IS_DEFAULT = ? AND CA.MERCHANT_ID = ? ");
		
		param.setBool(true);
		param.setLong(merchantId);
		
		if(null != status){
			if(status.intValue() == Coupon.CouponStatus.ACTIVE.toValue()){
				sql.append("AND C.STATUS = ? AND C.COLLECT_START_TIME < ? AND C.COLLECT_END_TIME > ? ");
				
				param.setInt(status);
				param.setDate(DateUtil.getCurrentDate());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Coupon.CouponStatus.EXPIRED.toValue()){
				sql.append("AND C.STATUS = ? AND C.COLLECT_END_TIME < ? ");
				
				param.setInt(Coupon.CouponStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else if(status.intValue() == Coupon.CouponStatus.PENDING.toValue()){//pending
				sql.append("AND C.STATUS = ? AND C.COLLECT_START_TIME > ? ");
				
				param.setInt(Coupon.CouponStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else{
				sql.append("AND C.STATUS = ? ");
				
				param.setInt(Coupon.CouponStatus.IN_ACTIVE.toValue());
			}
		}else{
			sql.append("AND C.STATUS = ? ");
			
			param.setInt(Coupon.CouponStatus.ACTIVE.toValue());
		}
		
		return queryForList(sql.toString(), HomePageItemDTO.class,param);
	}

	@Override
	public Integer findSortOrderByMerchantId(Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT IFNULL(MAX(SORT_ORDER),0) FROM COUPON C ")
			.append("LEFT JOIN CAMPAIGN CA ON CA.ID = C.CAMPAIGN_ID AND CA.IS_DELETED = 0 ")
			.append("WHERE CA.MERCHANT_ID = ? AND C.IS_DELETED = 0 ");
		param.setLong(merchantId);
		
		return queryForInt(sql.toString(),param)+1;
	}

}
