package com.cherrypicks.tcc.cms.customer.service;

import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dto.UserCouponReportDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.UserCoupon;

public interface UserCouponService extends IBaseService<UserCoupon>{

	Long findTotalCountByCampaignId(final Long campaignId, final Integer status);

	void addUserCoupon(final Long userId,final Long customerId,final Long couponId, final Integer qty,final String remark, final String lang);

	void revokeUserCoupon(final Long userId, final Long userCouponId, final String remark, final String lang);

	List<UserCouponReportDTO> findUserCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime,
			final Integer status, final boolean isRedeemCouponReport);

	PagingResultVo pagingFindUserCoupon(final Map<String, Object> criteriaMap, final String sortField, final String sortType,
			final Integer startRow, final Integer maxRows);

}
