package com.cherrypicks.tcc.cms.customer.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.model.UserStamp;

public interface UserStampDao extends IBaseDao<UserStamp> {

	long findUserCountByStampIdList(List<Long> stampIds);

	List<Long> findUserUnRedeemedStamps(final Long customerId, final Long userStampCardId);


}
