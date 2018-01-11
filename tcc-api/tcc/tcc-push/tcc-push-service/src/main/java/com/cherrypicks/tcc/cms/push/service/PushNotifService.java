package com.cherrypicks.tcc.cms.push.service;

import java.util.List;

import com.cherrypicks.tcc.cms.dto.PushNotifDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.model.PushNotif;
import com.cherrypicks.tcc.model.UserReservation;

public interface PushNotifService extends IBaseService<PushNotif>{

	PushNotifDTO findMerchantReservationPushNotif(final Long merchantId,final UserReservation userReservation,final MerchantConfig merchantConfig);

	PushNotif createPushNotif(Long userId, Long merchantId, final Integer pageRedirectType, String langData, String lang);

	void updatePushNotif(Long userId, Long pushNotifId, final Integer pageRedirectType, String langData, String lang);

	PushNotifDTO findDetailById(final Long userId, final Long pushNotifId, final String lang);

	void sendPushNotif(final Long userId,final Long pushNotifId,final String lang) throws Exception;

	void sendUserReservationPushMsg(final List<UserReservation> userReservations) throws Exception;

}
