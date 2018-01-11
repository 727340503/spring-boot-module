package com.cherrypicks.tcc.cms.customer.dao;

import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.UserStampHistoryReportDTO;
import com.cherrypicks.tcc.model.UserStampHistory;

public interface UserStampHistoryDao extends IBaseDao<UserStampHistory> {

	Long findUserTotalStampNo(final Long userId, final Long campaignId);

	List<UserStampHistoryReportDTO> findUserStampHistoryReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final List<Integer> types);

	List<UserStampHistoryReportDTO> pagingFindUserStampHistoryReport(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final int[] args);

	Long findTotalByType(final Long campaignId,final List<Integer> userStampHistoryTypes);

	Long findTotalItemCountByType(final Long campaignId, final List<Integer> userStampHistoryTypes);

}
