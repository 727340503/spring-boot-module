package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.BannerLangMapDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.BannerLangMapDao;
import com.cherrypicks.tcc.model.BannerLangMap;

@Repository
public class BannerLangMapDaoImpl extends AbstractBaseDao<BannerLangMap> implements BannerLangMapDao {

	@Override
	public List<BannerLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BannerLangMapDTO> findByBannerId(final Long bannerId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();

		sql.append("SELECT BL.ID,BL.IMG,BL.LANG_CODE,BL.NAME,ML.NAME AS MERCHANT_NAME FROM BANNER_LANG_MAP BL ")
				.append("LEFT JOIN BANNER B ON B.ID = BL.BANNER_ID AND B.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = B.MERCHANT_ID AND BL.LANG_CODE = ML.LANG_CODE AND ML.IS_DELETED = 0 ")
				.append("WHERE BL.IS_DELETED = 0 AND BL.BANNER_ID = ? ");
		param.setLong(bannerId);

		return queryForList(sql.toString(), BannerLangMapDTO.class, param);
	}

	@Override
	public boolean delByBannerIds(final List<Object> bannerIds) {
		final StringBuilder sql = new StringBuilder("DELETE FROM BANNER_LANG_MAP WHERE BANNER_ID IN (:bannerIds)");
		final Map<String, List<Object>> paramMap = Collections.singletonMap("bannerIds", bannerIds);
		return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

}
