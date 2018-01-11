package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponCollectQuotaDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.CouponCollectQuotaMapDao;
import com.cherrypicks.tcc.model.CouponCollectQuotaMap;

@Repository
public class CouponCollectQuotaMapDaoImpl extends AbstractBaseDao<CouponCollectQuotaMap> implements CouponCollectQuotaMapDao {

	@Override
	public List<CouponCollectQuotaMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<CouponCollectQuotaDTO> findByCouponId(Long couponId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,COUPON_ID,COLLECT_QUOTA_TYPE,COLLECT_QUOTA_QTY FROM COUPON_COLLECT_QUOTA_MAP WHERE COUPON_ID = ? AND IS_DELETED = 0 ");
		param.setLong(couponId);
		
		return queryForList(sql.toString(), CouponCollectQuotaDTO.class, param);
	}

	@Override
	public void delByCouponIds(List<Object> couponIds) {
		final StringBuilder sql = new StringBuilder();
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		
		sql.append("DELETE FROM COUPON_COLLECT_QUOTA_MAP WHERE COUPON_ID IN (:couponIds) ");
		paramMap.put("couponIds", couponIds);
		
		updateForRecordWithNamedParam(sql.toString(), paramMap);
	}



}
