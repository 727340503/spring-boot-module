package com.cherrypicks.tcc.cms.customer.service;

import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dto.UserDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReportDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.User;

public interface UserService extends IBaseService<User>{

	UserDetailDTO findDetailByID(final Long userId, final Long id, final String lang);

	List<Long> findUserIdsByMerchantId(final Long merchantId);

	List<UserReportDTO> findUserReport(final Long merchantId, final String startTime, final String endTime);

	PagingResultVo pagingFindUserReport(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final Integer startRow, final Integer maxRows);

	Long findCampaignTotalUserCount(final Long campaignId);
}
