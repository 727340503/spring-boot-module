package com.cherrypicks.tcc.cms.merchant.dao;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.model.MerchantConfig;

public interface MerchantConfigDao extends IBaseDao<MerchantConfig> {

	MerchantConfig findByMerchantId(final Long merchantId);

}
