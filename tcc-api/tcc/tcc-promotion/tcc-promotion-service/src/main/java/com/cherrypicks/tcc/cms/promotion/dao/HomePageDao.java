package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.HomePageDetailDTO;
import com.cherrypicks.tcc.model.HomePage;

public interface HomePageDao extends IBaseDao<HomePage> {

	boolean delByMerchantIdAndStatus(Long merchantId,Integer status);

	List<HomePageDetailDTO> findMerchantHomePageList(Long merchantId, Integer status,String langCode);

	long findCountByMerchantIdAndRefId(Long merchantId,Long refId, Integer type);

	long findCountByReftId(Long refId, Integer type);

//	boolean updateDraftToOnLine(Long merchantId);

	List<HomePage> findByMerchantIdAndStatus(final Long merchantId, final Integer status);


}
