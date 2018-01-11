package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponLangMapDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.CouponLangMapDao;
import com.cherrypicks.tcc.model.CouponLangMap;

@Repository
public class CouponLangMapDaoImpl extends AbstractBaseDao<CouponLangMap> implements CouponLangMapDao {

	@Override
	public List<CouponLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<CouponLangMapDTO> findByCouponId(final Long couponId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT CL.ID,CL.NAME,CL.TERMS,CL.IMG,CL.DESCR,CL.LANG_CODE,CL.CREATED_TIME,ML.NAME AS MERCHANT_NAME,CAL.NAME AS CAMPAIGN_NAME FROM COUPON_LANG_MAP CL ")
			.append("LEFT JOIN COUPON C ON C.ID = CL.COUPON_ID AND C.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN CA ON CA.ID = C.CAMPAIGN_ID AND CA.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CAL ON CAL.CAMPAIGN_ID = CA.ID AND CAL.LANG_CODE = CL.LANG_CODE AND CAL.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = CA.MERCHANT_ID AND ML.LANG_CODE = CL.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("WHERE CL.IS_DELETED = 0 AND C.ID = ? ");
		param.setLong(couponId);
		
		
		return queryForList(sql.toString(), CouponLangMapDTO.class, param);
	}

	@Override
	public boolean delByCouponIds(List<Object> couponIds) {
		final StringBuilder sql = new StringBuilder("DELETE FROM COUPON_LANG_MAP WHERE COUPON_ID IN (:couponIds)");
		final Map<String, List<Object>> paramMap = Collections.singletonMap("couponIds", couponIds);
		return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}



}
