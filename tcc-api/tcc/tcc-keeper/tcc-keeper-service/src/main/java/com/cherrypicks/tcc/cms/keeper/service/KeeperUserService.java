package com.cherrypicks.tcc.cms.keeper.service;

import com.cherrypicks.tcc.cms.dto.KeeperUserDetailDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.KeeperUser;

public interface KeeperUserService extends IBaseService<KeeperUser>{

	KeeperUser createKeeperUser(final Long userId, final Long merchantId, final Long storeId, final String userName,final String password,final String email, final String mobile,
				final String staffId,final Integer status,final String lang);

	void updateKeeperUser(final Long userId, final Long id, final Long storeId, final String userName,final String email, final String mobile,
			final String staffId,final String password,final Integer status,final String lang);

	KeeperUserDetailDTO findKeeperUserDetail(final Long userId, final Long keeperUserId, final String lang);


}
