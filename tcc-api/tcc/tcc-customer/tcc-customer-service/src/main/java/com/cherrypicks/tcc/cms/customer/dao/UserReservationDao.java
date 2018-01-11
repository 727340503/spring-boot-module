package com.cherrypicks.tcc.cms.customer.dao;

import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.UserReservationDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationPushNotifDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationReportDTO;
import com.cherrypicks.tcc.model.UserReservation;

public interface UserReservationDao extends IBaseDao<UserReservation> {

//	Date findReservationExpiredTime(Long userReservationId);

	UserReservationDetailDTO findDetailById(Long userReservationId);

	List<UserReservationReportDTO> findUserReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, final String pickupEndTime, final Integer status);

	List<UserReservationReportDTO> pagingFindUserReservationReport(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final int[] args);

	Long findCampaignReservationCount(final Long campaignId,final List<Integer> statusList);

	UserReservationPushNotifDTO finduserReservationPushNotifInfo(final Long id);

}
