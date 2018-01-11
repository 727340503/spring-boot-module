package com.cherrypicks.tcc.cms.customer.dao;

import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.UserDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReportDTO;
import com.cherrypicks.tcc.model.User;

public interface UserDao extends IBaseDao<User> {

	UserDetailDTO findDetailById(final Long id);

	List<Long> findUserIdsByMerchantId(final Long merchantId);

	List<UserReportDTO> findUserReport(final Long merchantId, final String startTime, final String endTime);

	List<UserReportDTO> pagingFindUserReport(Map<String, Object> criteriaMap, String sortField, String sortType, int[] args);

}
