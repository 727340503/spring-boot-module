package com.cherrypicks.tcc.cms.system.service;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.SystemUserMerchantMap;

public interface SystemUserMerchantMapService extends IBaseService<SystemUserMerchantMap>{

	Long findMerchantIdBySystemUserId(final Long userId);

}
