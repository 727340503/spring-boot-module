package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponCollectMethodDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.CouponCollectMethodMapDao;
import com.cherrypicks.tcc.model.CouponCollectMethodMap;

@Repository
public class CouponCollectMethodMapDaoImpl extends AbstractBaseDao<CouponCollectMethodMap> implements CouponCollectMethodMapDao {

	@Override
	public List<CouponCollectMethodMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<CouponCollectMethodDTO> findByCouponId(final Long couponId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,COUPON_ID,COLLECT_METHOD,COLLECT_METHOD_QTY,COLLECT_METHOD_AMOUNT,COLLECT_PRODUCT_CODE FROM COUPON_COLLECT_METHOD_MAP WHERE COUPON_ID = ? AND IS_DELETED = 0 ");
		param.setLong(couponId);
		
		return queryForList(sql.toString(), CouponCollectMethodDTO.class, param);
	}

	@Override
	public void delByCouponIds(List<Object> couponIds) {
		final StringBuilder sql = new StringBuilder();
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		
		sql.append("DELETE FROM COUPON_COLLECT_METHOD_MAP WHERE COUPON_ID IN (:couponIds) ");
		paramMap.put("couponIds", couponIds);
		
		updateForRecordWithNamedParam(sql.toString(), paramMap);
	}



}
