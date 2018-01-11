package com.cherrypicks.tcc.cms.promotion.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dto.HomePageDetailDTO;
import com.cherrypicks.tcc.cms.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.promotion.dao.HomePageDao;
import com.cherrypicks.tcc.model.HomePage;

@Repository
public class HomePageDaoImpl extends AbstractBaseDao<HomePage> implements HomePageDao {

	@Override
	public List<HomePage> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public boolean delByMerchantIdAndStatus(Long merchantId,Integer status) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("DELETE FROM HOME_PAGE WHERE MERCHANT_ID = ? AND IS_DELETED = 0 AND STATUS = ? ");
		param.setLong(merchantId);
		param.setInt(status);
		
		return updateForRecord(sql.toString(), param) > 0 ;
	}

	@Override
	public List<HomePageDetailDTO> findMerchantHomePageList(Long merchantId, Integer status, String langCode) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
//		sql.append("SELECT H.ID, H.REF_ID,H.INAPP_OPEN,H.TYPE,H.SORT_ORDER,H.MERCHANT_ID, ");
		sql.append("SELECT H.ID, H.REF_ID,H.TYPE,H.SORT_ORDER,H.MERCHANT_ID, ");
		
//		sql.append("( SELECT CASE H.TYPE ")
//			.append("WHEN ? THEN ")
//			.append("( SELECT C.IMG FROM COUPON_LANG_MAP C WHERE C.COUPON_ID = H.REF_ID AND C.LANG_CODE = ? ) ")
//			.append(" WHEN ? THEN ")
//			.append("( SELECT B.IMG FROM BANNER_LANG_MAP B WHERE B.BANNER_ID = H.REF_ID AND B.LANG_CODE = ? )  ")
//			.append("WHEN ? THEN ")
//			.append("( SELECT G.IMG FROM GAME_LANG_MAP G WHERE G.GAME_ID = H.REF_ID AND G.LANG_CODE = ? ) ")
//			.append("WHEN ? THEN ")
//			.append("( SELECT CA.COVER_IMG FROM CAMPAIGN_LANG_MAP CA WHERE CA.CAMPAIGN_ID = H.REF_ID AND CA.LANG_CODE = ? ) ")
//			.append("END ) AS IMG, ");
//		param.setInt(HomePage.HomePageType.COUPON.toValue());
//		param.setString(langCode);
//		param.setInt(HomePage.HomePageType.BANNER.toValue());
//		param.setString(langCode);
//		param.setInt(HomePage.HomePageType.GAME.toValue());
//		param.setString(langCode);
//		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
//		param.setString(langCode);
		
//			.append("( SELECT CASE H.TYPE ")
//			.append("WHEN ? THEN ")
//			.append("( SELECT B.WEB_URL FROM BANNER B WHERE B.ID = H.REF_ID ) ")
//			.append("WHEN ? THEN ")
//			.append("( SELECT G.WEB_URL FROM GAME G WHERE G.ID = H.REF_ID ) ")
//			.append("WHEN ? THEN ")
//			.append("( SELECT CA.PRM_BANNER_URL FROM CAMPAIGN CA WHERE CA.ID = H.REF_ID ) ")
//			.append("END ) AS WEB_URL ")
		sql.append("(SELECT CASE H.TYPE WHEN ? THEN B.INAPP_OPEN WHEN ? THEN G.INAPP_OPEN WHEN ? THEN CA.INAPP_OPEN END) AS INAPP_OPEN, ");
		param.setInt(HomePage.HomePageType.BANNER.toValue());
		param.setInt(HomePage.HomePageType.GAME.toValue());
		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
		
		sql.append("(SELECT CASE H.TYPE WHEN ? THEN B.WEB_URL WHEN ? THEN G.WEB_URL WHEN ? THEN CA.PRM_BANNER_URL END) AS WEB_URL, ");
		param.setInt(HomePage.HomePageType.BANNER.toValue());
		param.setInt(HomePage.HomePageType.GAME.toValue());
		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
		
		sql.append("(SELECT CASE H.TYPE WHEN ? THEN CP.STATUS WHEN ? THEN B.STATUS WHEN ? THEN G.STATUS WHEN ? THEN CA.STATUS END) AS STATUS, ");
		param.setInt(HomePage.HomePageType.COUPON.toValue());
		param.setInt(HomePage.HomePageType.BANNER.toValue());
		param.setInt(HomePage.HomePageType.GAME.toValue());
		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
		
		sql.append("(SELECT CASE H.TYPE WHEN ? THEN CP.COLLECT_START_TIME WHEN ? THEN B.START_TIME WHEN ? THEN G.START_TIME WHEN ? THEN CA.START_TIME END) AS START_TIME, ");
		param.setInt(HomePage.HomePageType.COUPON.toValue());
		param.setInt(HomePage.HomePageType.BANNER.toValue());
		param.setInt(HomePage.HomePageType.GAME.toValue());
		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
		
		sql.append("(SELECT CASE H.TYPE WHEN ? THEN CP.COLLECT_END_TIME WHEN ? THEN B.END_TIME WHEN ? THEN G.END_TIME WHEN ? THEN CA.END_TIME END) AS END_TIME, ");
		param.setInt(HomePage.HomePageType.COUPON.toValue());
		param.setInt(HomePage.HomePageType.BANNER.toValue());
		param.setInt(HomePage.HomePageType.GAME.toValue());
		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
		
		sql.append("(SELECT CASE H.TYPE WHEN ? THEN CM.NAME WHEN ? THEN BL.NAME WHEN ? THEN GL.NAME WHEN ? THEN CL.NAME END) AS NAME, ");
		param.setInt(HomePage.HomePageType.COUPON.toValue());
		param.setInt(HomePage.HomePageType.BANNER.toValue());
		param.setInt(HomePage.HomePageType.GAME.toValue());
		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
		
		
		sql.append("(SELECT CASE H.TYPE WHEN ? THEN CM.IMG WHEN ? THEN BL.IMG WHEN ? THEN GL.IMG WHEN ? THEN CL.COVER_IMG END) AS IMG ");
		param.setInt(HomePage.HomePageType.COUPON.toValue());
		param.setInt(HomePage.HomePageType.BANNER.toValue());
		param.setInt(HomePage.HomePageType.GAME.toValue());
		param.setInt(HomePage.HomePageType.CAMPAIGN.toValue());
		
		sql.append("FROM HOME_PAGE H ")
			.append("LEFT JOIN CAMPAIGN CA ON CA.ID = H.REF_ID AND CA.IS_DELETED = 0 ")
			.append("LEFT JOIN GAME G ON G.ID = H.REF_ID AND G.IS_DELETED = 0 ")
			.append("LEFT JOIN BANNER B ON B.ID = H.REF_ID AND B.IS_DELETED = 0 ")
			.append("LEFT JOIN COUPON CP ON CP.ID = H.REF_ID AND CP.IS_DELETED = 0 ");
		
		sql.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = H.REF_ID AND CL.IS_DELETED = 0 AND CL.LANG_CODE = ? ")
			.append("LEFT JOIN GAME_LANG_MAP GL ON GL.GAME_ID = H.REF_ID AND GL.IS_DELETED = 0  AND GL.LANG_CODE = ? ")
			.append("LEFT JOIN BANNER_LANG_MAP BL ON BL.BANNER_ID = H.REF_ID AND BL.IS_DELETED = 0  AND BL.LANG_CODE = ? ")
			.append("LEFT JOIN COUPON_LANG_MAP CM ON CM.COUPON_ID = H.REF_ID AND CM.IS_DELETED = 0  AND CM.LANG_CODE = ? ");
		
		param.setString(langCode);
		param.setString(langCode);
		param.setString(langCode);
		param.setString(langCode);
		
		sql.append("WHERE H.MERCHANT_ID = ? AND H.STATUS = ? AND H.IS_DELETED = 0 ");
		param.setLong(merchantId);
		param.setInt(status);
		
		return queryForList(sql.toString(), HomePageDetailDTO.class, param);
	}

	@Override
	public long findCountByMerchantIdAndRefId(final Long merchantId, final Long refId, final Integer type) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT COUNT(ID) FROM HOME_PAGE WHERE MERCHANT_ID = ? AND REF_ID = ? AND IS_DELETED = 0 ");
		
		param.setLong(merchantId);
		param.setLong(refId);
		if(type.intValue() == HomePage.HomePageType.CAMPAIGN.toValue() || type.intValue() == HomePage.HomePageType.BANNER.toValue()
					|| type.intValue() == HomePage.HomePageType.COUPON.toValue() || type.intValue() == HomePage.HomePageType.GAME.toValue()){
			sql.append("AND TYPE = ? ");
			param.setInt(type);
		}
		
		return queryForLong(sql.toString(), param);
	}

	@Override
	public long findCountByReftId(final Long refId, final Integer type) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
//		sql.append("SELECT COUNT(ID) FROM HOME_PAGE WHERE REF_ID = ? AND IS_DELETED = 0 AND STATUS = ? ");
		sql.append("SELECT COUNT(ID) FROM HOME_PAGE WHERE REF_ID = ? AND IS_DELETED = 0 ");
		
		param.setLong(refId);
		if(type.intValue() == HomePage.HomePageType.CAMPAIGN.toValue() || type.intValue() == HomePage.HomePageType.BANNER.toValue()
					|| type.intValue() == HomePage.HomePageType.COUPON.toValue() || type.intValue() == HomePage.HomePageType.GAME.toValue()){
			sql.append("AND TYPE = ? ");
			param.setInt(type);
		}
//		param.setInt(HomePage.EditStatus.ON_LINE.getCode());
		
		return queryForLong(sql.toString(), param);
	}

//	@Override
//	public boolean updateDraftToOnLine(Long merchantId) {
//		final StringBuilder sql = new StringBuilder();
//		final StatementParameter param = new StatementParameter();
//		
//		sql.append("UPDATE HOME_PAGE SET STATUS = ? WHERE MERCHANT_ID = ? AND IS_DELETED = 0 AND STATUS = ? ");
//		param.setInt(HomePage.EditStatus.ON_LINE.getCode());
//		param.setLong(merchantId);
//		param.setInt(HomePage.EditStatus.DRAFT.getCode());
//		
//		return updateForRecord(sql.toString(), param) > 0 ;
//	}

	@Override
	public List<HomePage> findByMerchantIdAndStatus(final Long merchantId, final Integer status){
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM HOME_PAGE WHERE IS_DELETED = 0 AND MERCHANT_ID = ? AND STATUS = ? ");
		param.setLong(merchantId);
		param.setInt(status);
		
		return queryForList(sql.toString(), HomePage.class,param);
	}



}
