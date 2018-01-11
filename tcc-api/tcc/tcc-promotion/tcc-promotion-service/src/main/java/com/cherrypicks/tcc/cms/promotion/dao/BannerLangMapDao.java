package com.cherrypicks.tcc.cms.promotion.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.BannerLangMapDTO;
import com.cherrypicks.tcc.model.BannerLangMap;

public interface BannerLangMapDao extends IBaseDao<BannerLangMap> {

	List<BannerLangMapDTO> findByBannerId(final Long bannerId);

	boolean delByBannerIds(final List<Object> bannerIds);
}
