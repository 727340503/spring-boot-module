package com.cherrypicks.tcc.cms.customer.dao;

import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.UserCouponReportDTO;
import com.cherrypicks.tcc.model.UserCoupon;

public interface UserCouponDao extends IBaseDao<UserCoupon> {

	Long findByTotalByCampaignId(final Long campaignId, final Integer status);

	List<UserCouponReportDTO> findUserCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final Integer statu,final boolean isRedeemCouponReports);

	List<UserCouponReportDTO> pagingFindUserCoupon(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final int[] args);

	UserCoupon findByQrCode(final Long merchantId, final String qrCode);

}
