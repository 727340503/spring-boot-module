package com.cherrypicks.tcc.cms.push.service.push;

import java.util.List;

import com.cherrypicks.tcc.cms.dto.PushNotifDTO;
import com.cherrypicks.tcc.model.UserReservation;

public interface PushExecutorService {

	void sendUserReservationPushMessage(final List<UserReservation> userReservations) throws Exception;

	Long sendPushNotif(final PushNotifDTO pushNotif,final List<Long> userIds) throws Exception;

	Long sendPushNotifToAllUser(final PushNotifDTO pushNotif) throws Exception;

}
