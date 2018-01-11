package com.cherrypicks.tcc.cms.keeper.dao;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.KeeperUserDetailDTO;
import com.cherrypicks.tcc.model.KeeperUser;

public interface KeeperUserDao extends IBaseDao<KeeperUser> {

	KeeperUser findByUserNameAndMerchantId(final String userName, final Long merchantId);

	KeeperUserDetailDTO findDetailById(final Long keeperUserId);

	KeeperUser findByStaffIdAndMerchantId(final String staffId, final Long merchantId);

}
