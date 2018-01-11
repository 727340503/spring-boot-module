package com.cherrypicks.tcc.cms.push.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantConfigRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.UserReservationRequestUtil;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.PushNotifDTO;
import com.cherrypicks.tcc.cms.dto.PushNotifLangMapDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationPushNotifDTO;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.exception.PushNotifNotExistException;
import com.cherrypicks.tcc.cms.exception.PushNotifStatusException;
import com.cherrypicks.tcc.cms.push.dao.PushNotifDao;
import com.cherrypicks.tcc.cms.push.dao.PushNotifLangMapDao;
import com.cherrypicks.tcc.cms.push.service.PushNotifService;
import com.cherrypicks.tcc.cms.push.service.push.PushExecutorService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.model.PushNotif;
import com.cherrypicks.tcc.model.PushNotifLangMap;
import com.cherrypicks.tcc.model.UserReservation;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.Constants;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.JsonUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Service
public class PushNotifServiceImpl extends AbstractBaseService<PushNotif> implements PushNotifService {

	@Autowired
	private PushNotifDao pushNotifDao;

	@Autowired
	private PushNotifLangMapDao pushNotifLangMapDao;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private PushExecutorService pushExecutorService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<PushNotif> pushNotifDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(pushNotifDao);
	}

	@Override
	public PushNotifDTO findMerchantReservationPushNotif(final Long merchantId,final UserReservation userReservation,final MerchantConfig merchantConfig) {

		PushNotifDTO pushNotif = new PushNotifDTO();
		List<PushNotifLangMapDTO> pushNotifLangMaps = new ArrayList<PushNotifLangMapDTO>();
		
		UserReservationPushNotifDTO userReservationPush = UserReservationRequestUtil.finduserReservationPushNotifInfo(userReservation.getId());
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("user", userReservationPush.getUserName());
		model.put("giftName", userReservationPush.getGiftName());
		model.put("endTime", DateUtil.format(TimeZoneConvert.dateTimezoneToUI(userReservationPush.getReservationExpiredTime(), TimeZoneConvert.DEFAULT_TIMEZONE, userReservationPush.getTimeZone()),userReservationPush.getDateFormat()));
		
		String templaetName = merchantConfig.getReservationPushTemplate();
		
		if(StringUtils.isBlank(templaetName)){
			throw new PushNotifNotExistException();
		}
		
		String message = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templaetName, Constants.UTF8, model);
		
		PushNotifLangMapDTO pushNotifLangMap = new PushNotifLangMapDTO();
		pushNotifLangMap.setMsg(message);
		pushNotifLangMap.setLangCode(userReservationPush.getLangCode());

		pushNotifLangMaps.add(pushNotifLangMap);
		
		pushNotif.setPushNotifLangMaps(pushNotifLangMaps);
		pushNotif.setType(PushNotif.Type.RESERVATION.getCode());

		return pushNotif;
	}

	@Override
	@Transactional
	public PushNotif createPushNotif(Long userId, Long merchantId, final Integer pageRedirectType, String langData, String lang) {

		List<PushNotifLangMap> pushNotifLangMaps = JsonUtil.toListObject(langData, PushNotifLangMap.class);
		if (null == pushNotifLangMaps) {
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}

		PushNotif pushNotif = new PushNotif();
		pushNotif.setType(PushNotif.Type.CMS.getCode());
		pushNotif.setPageRedirectType(pageRedirectType);

		pushNotif.setCreatedBy(String.valueOf(userId));
		pushNotif.setCreatedTime(DateUtil.getCurrentDate());
		pushNotif.setMerchantId(merchantId);
		pushNotif.setStatus(PushNotif.Status.DRAFT.toValue());
		pushNotif = pushNotifDao.add(pushNotif);

		createPushNotifLangMap(pushNotif, pushNotifLangMaps, lang);

		return pushNotif;
	}

	private void createPushNotifLangMap(PushNotif pushNotif, List<PushNotifLangMap> pushNotifLangMaps, String lang) {
		
		List<String> langCodes = MerchantRequestUtil.findMerchantLangCodes(pushNotif.getMerchantId());

		for (PushNotifLangMap pushNotifyLangMap : pushNotifLangMaps) {
			if (StringUtils.isBlank(pushNotifyLangMap.getLangCode())) {
				throw new IllegalArgumentException("Lang code " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}

			if (StringUtils.isBlank(pushNotifyLangMap.getMsg())) {
				throw new IllegalArgumentException("Push msg " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}

			if(!langCodes.contains(pushNotifyLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}

			pushNotifyLangMap.setCreatedBy(pushNotif.getCreatedBy());
			pushNotifyLangMap.setCreatedTime(pushNotif.getCreatedTime());
			pushNotifyLangMap.setPushNotifId(pushNotif.getId());

			pushNotifLangMapDao.add(pushNotifyLangMap);

			langCodes.remove(pushNotifyLangMap.getLangCode());
		}

		if(langCodes.size() > 0){
			throw new IllegalArgumentException("Lang code " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

	}

	@Override
	@Transactional
	public void updatePushNotif(Long userId, Long pushNotifId,  final Integer pageRedirectType, String langData, String lang) {

		PushNotif pushNotif = pushNotifDao.get(pushNotifId);
		if (null == pushNotif) {
			throw new PushNotifNotExistException(I18nUtil.getMessage(CmsCodeStatus.PUSH_NOTIF_NOT_EXIST, null, lang));
		}
		
		if(pushNotif.getStatus().intValue() == PushNotif.Status.SENT.toValue()){
			return;
		}

		AuthorizeRequestUtil.checkUserMerchantPermission(userId, pushNotif.getMerchantId(), lang);
		
		if(null != pageRedirectType){
			pushNotif.setPageRedirectType(pageRedirectType);
			pushNotif.setUpdatedBy(String.valueOf(userId));
			
			pushNotifDao.updateForVersion(pushNotif);
		}

		if (StringUtils.isNotBlank(langData)) {
			List<PushNotifLangMap> pushNotifLangMaps = JsonUtil.toListObject(langData, PushNotifLangMap.class);
			if (null == pushNotifLangMaps) {
				throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
			}

			updatePushNotifLangMap(pushNotif, pushNotifLangMaps, lang);
		}
	}

	private void updatePushNotifLangMap(PushNotif pushNotif, List<PushNotifLangMap> pushNotifLangMaps, String lang) {
		for (PushNotifLangMap pushNotifLangMap : pushNotifLangMaps) {
			if (null == pushNotifLangMap.getId()) {
				throw new IllegalArgumentException("Push notif lang map id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}

			PushNotifLangMap updPushNotifLangMap = pushNotifLangMapDao.get(pushNotifLangMap.getId());
			if (null != updPushNotifLangMap) {
				if (updPushNotifLangMap.getPushNotifId().intValue() != pushNotif.getId().intValue()) {
					throw new ForbiddenException(I18nUtil.getMessage(CmsCodeStatus.FORBIDDEN, null, lang));
				}

				if (null != pushNotifLangMap.getLandingImg()) {
					updPushNotifLangMap.setLandingImg(pushNotifLangMap.getLandingImg());
				}
				if (null != pushNotifLangMap.getMsg()) {
					updPushNotifLangMap.setMsg(pushNotifLangMap.getMsg());
				}
				if (null != pushNotifLangMap.getLandingUrl()) {
					updPushNotifLangMap.setLandingUrl(pushNotifLangMap.getLandingUrl());
				}
				updPushNotifLangMap.setUpdatedBy(pushNotif.getUpdatedBy());

				pushNotifLangMapDao.updateForVersion(updPushNotifLangMap);
			}
		}
	}

	@Override
	public PushNotifDTO findDetailById(final Long userId, final Long pushNotifId, final String lang) {
		PushNotif pushNotif = pushNotifDao.get(pushNotifId);

		if (null != pushNotif) {

			AuthorizeRequestUtil.checkUserMerchantPermission(userId, pushNotif.getMerchantId(), lang);

			PushNotifDTO pushNotifDetail = new PushNotifDTO();
			BeanUtils.copyProperties(pushNotif, pushNotifDetail);

			List<PushNotifLangMapDTO> pushNotifLangMaps = pushNotifLangMapDao.findByPushNotifId(pushNotif.getId());
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(pushNotif.getMerchantId());
			
			for (PushNotifLangMapDTO pushNotifLangMap : pushNotifLangMaps) {
				pushNotifLangMap.setFullLandingImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), pushNotifLangMap.getLandingImg()));
			}

			pushNotifDetail.setPushNotifLangMaps(pushNotifLangMaps);

			return pushNotifDetail;
		}

		return null;
	}

	@Override
	@Transactional
	public void sendPushNotif(Long userId, Long pushNotifId, String lang) throws Exception {
		PushNotif pushNotif = pushNotifDao.get(pushNotifId);
		if(null == pushNotif){
			throw new PushNotifNotExistException();
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, pushNotif.getMerchantId(), lang);
		
		if(pushNotif.getStatus().intValue() != PushNotif.Status.DRAFT.toValue()){
			throw new PushNotifStatusException();
		}

		
		final Long pushTaskId = pushExecutorService.sendPushNotifToAllUser(this.findDetailById(userId, pushNotifId, lang));
		pushNotif.setPushTaskId(pushTaskId);
		pushNotif.setStatus(PushNotif.Status.SENT.toValue());
		
		pushNotif.setUpdatedBy(String.valueOf(userId));
		pushNotif.setUpdatedTime(DateUtil.getCurrentDate());
		
		pushNotifDao.update(pushNotif);
	}

	@Override
	public void sendUserReservationPushMsg(final List<UserReservation> userReservations) throws Exception {
		pushExecutorService.sendUserReservationPushMessage(userReservations);
	}

}
