package com.cherrypicks.tcc.cms.customer.service;

import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dto.UserStampHistoryReportDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.UserStampHistory;

public interface UserStampHistoryService extends IBaseService<UserStampHistory>{

	Long findUserTotalStampNo(final Long userId, final Long campaignId);

	void createUserStampHistory(final Long userId,final Long customerId,final Long campaignId,final Integer type,final Long stampQty,final String remarks,final String lang);

	List<UserStampHistoryReportDTO> findUserStampHistoryReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final List<Integer> types);

	PagingResultVo pagingFindUserStampHistory(final Map<String, Object> criteriaMap, final String sortField, final String sortType,final Integer startRow,final Integer maxRows);

	Long findTotalByTypes(final Long campaignId, final List<Integer> userStampHistoryTypes);

}
