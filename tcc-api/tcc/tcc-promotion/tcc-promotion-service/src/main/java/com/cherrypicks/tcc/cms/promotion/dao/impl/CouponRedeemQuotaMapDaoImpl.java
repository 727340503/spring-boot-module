package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.CouponRedeemQuotaDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.CouponRedeemQuotaMapDao;
import com.cherrypicks.tcc.model.CouponRedeemQuotaMap;

@Repository
public class CouponRedeemQuotaMapDaoImpl extends AbstractBaseDao<CouponRedeemQuotaMap> implements CouponRedeemQuotaMapDao {

	@Override
	public List<CouponRedeemQuotaMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<CouponRedeemQuotaDTO> findByCouponId(Long couponId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,COUPON_ID,REDEEM_QUOTA_TYPE,REDEEM_QUOTA_QTY FROM COUPON_REDEEM_QUOTA_MAP WHERE COUPON_ID = ? AND IS_DELETED = 0 ");
		param.setLong(couponId);
		
		return queryForList(sql.toString(), CouponRedeemQuotaDTO.class, param);
	}

	@Override
	public void delByCouponIds(List<Object> couponIds) {
		final StringBuilder sql = new StringBuilder();
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		
		sql.append("DELETE FROM COUPON_REDEEM_QUOTA_MAP WHERE COUPON_ID IN (:couponIds) ");
		paramMap.put("couponIds", couponIds);
		
		updateForRecordWithNamedParam(sql.toString(), paramMap);
	}



}
