package com.cherrypicks.tcc.cms.merchant.dao;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.model.Merchant;

public interface MerchantDao extends IBaseDao<Merchant> {

	Merchant findByMerchantCode(final String merchantCode);

	Merchant findBySystemUser(final Long userId);

}
