package com.cherrypicks.tcc.cms.customer.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cherrypicks.tcc.cms.dto.UserReservationDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationPushNotifDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationReportDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.UserReservation;

public interface UserReservationService extends IBaseService<UserReservation>{

	UserReservationDetailDTO findDetailById(final Long userId, final Long userReservationId, final String lang);

	void updateUserReservationToAvailable(final Long userId,final Long merchantId, final String ids, final Date reservationExpiredTime,final String lang) throws Exception;

	Long findCampaignReservationCount(final Long campaignId);

	UserReservationPushNotifDTO finduserReservationPushNotifInfo(final Long id);

	List<UserReservationReportDTO> findUserReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, final String pickupEndTime, final Integer status);

	PagingResultVo pagingFindUserReservationReport(final Map<String, Object> criteriaMap, final String sortField, final String sortType,
			final Integer startRow, final Integer maxRows);
}
