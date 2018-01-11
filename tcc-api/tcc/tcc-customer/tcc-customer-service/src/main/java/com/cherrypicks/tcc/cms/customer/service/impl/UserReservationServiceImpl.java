package com.cherrypicks.tcc.cms.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.PushRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.customer.dao.UserReservationDao;
import com.cherrypicks.tcc.cms.customer.service.UserReservationService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.UserReservationDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationItemDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationPushNotifDTO;
import com.cherrypicks.tcc.cms.dto.UserReservationReportDTO;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.UserReservationNotExistException;
import com.cherrypicks.tcc.cms.exception.UserReservationStatusException;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.model.UserReservation;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;
import com.cherrypicks.tcc.util.paging.PagingList;

@Service
public class UserReservationServiceImpl extends AbstractBaseService<UserReservation> implements UserReservationService {
	
	@Autowired
	private UserReservationDao userReservationDao;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<UserReservation> userReservationDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(userReservationDao);
	}

	@Override
	public UserReservationDetailDTO findDetailById(final Long userId, final Long userReservationId, final String lang) {
		
		UserReservationDetailDTO userReservationDetai = userReservationDao.findDetailById(userReservationId);
		
		if(null != userReservationDetai){
			final SystemRole userRole = SystemRoleRequestUtil.findByUserId(userId);
			if(userRole.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
				Long merchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
				if(merchantId.intValue() != userReservationDetai.getMerchantId()){
					return null;
				}
			}
			
			final String merchantTimeZone = userReservationDetai.getMerchantTimeZone();
			
			if(null != userReservationDetai.getReservationExpiredTime()){
				userReservationDetai.setReservationExpiredTime(TimeZoneConvert.dateTimezoneToUI(userReservationDetai.getReservationExpiredTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
			}
			
			final int userReservationStatus = userReservationDetai.getStatus().intValue();
			if(userReservationStatus == UserReservation.UserReservationStatus.CONFIRMED.toValue() || userReservationStatus == UserReservation.UserReservationStatus.AVAILABLE.toValue()){
				if(null != userReservationDetai.getReservationExpiredTime()){
					if(DateUtil.getCurrentDate().getTime() >= userReservationDetai.getReservationExpiredTime().getTime()){
						userReservationDetai.setStatus(UserReservation.UserReservationStatus.EXPIRED.toValue());
					}
				}
			}else if(userReservationStatus == UserReservation.UserReservationStatus.IN_COMPLETED.toValue()){
				final Date createTime = TimeZoneConvert.dateTimezoneToUI(userReservationDetai.getCreatedTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone);
				final Date currentTime = TimeZoneConvert.dateTimezoneToUI(DateUtil.getCurrentDate(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone);
				
				if(!DateUtil.format(createTime).equals(DateUtil.format(currentTime))){
					userReservationDetai.setStatus(UserReservation.UserReservationStatus.CANCELLED.toValue());
				}
			}
			
			if(UserReservation.UserReservationStatus.SETTLED.toValue() == userReservationStatus && null != userReservationDetai.getUpdatedTime()){
				userReservationDetai.setPickupTime(TimeZoneConvert.dateTimezoneToUI(userReservationDetai.getUpdatedTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
			}
			
			return userReservationDetai;
		}
		return null;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public void updateUserReservationToAvailable(final Long userId, Long merchantId,final String ids, final Date reservationExpiredTime,final String lang) throws Exception {
		final String[] idArr = ids.split(",");
		final List<Object> idList = new ArrayList<Object>(); 
		for(String idStr : idArr){
			idList.add(Long.parseLong(idStr));
		}
		
		final List<UserReservation> userReservations = userReservationDao.findByIds(idList);
		if(null == userReservations || userReservations.size() <= 0 ){
			throw new UserReservationNotExistException(I18nUtil.getMessage(CmsCodeStatus.USER_RESERVATION_NOT_EXIST, null, lang));
		}
		
		final SystemRole userRole = SystemRoleRequestUtil.findByUserId(userId);
		boolean isPlatformUser = false;
		if(userRole.getRoleType().intValue() == SystemRole.Roletype.PLATFORM.getCode()){
			isPlatformUser = true;
		}
		if(!isPlatformUser){
			merchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		final Merchant merchant = MerchantRequestUtil.findById(merchantId);
		
		Date reservationExpiredTimeDB = TimeZoneConvert.dateTimezoneToDB(reservationExpiredTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE);
		if(reservationExpiredTimeDB.getTime() <= DateUtil.getCurrentDate().getTime()){
			throw new IllegalArgumentException("Reservation expired time"+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		final List<UserReservation> needSendEmailUserReservation = new ArrayList<UserReservation>();
		
		for(UserReservation userReservation : userReservations){
			if(!isPlatformUser){
				if(merchantId.intValue() != userReservation.getMerchantId().intValue()){
					throw new ForbiddenException(I18nUtil.getMessage(CmsCodeStatus.FORBIDDEN, null, lang));
				}
			}
			
			if(merchantId.intValue() != userReservation.getMerchantId().intValue()){
				throw new ForbiddenException();
			}
			
			if(userReservation.getStatus().intValue() != UserReservation.UserReservationStatus.CONFIRMED.toValue()){
				throw new UserReservationStatusException();
			}
			
			userReservation.setStatus(UserReservation.UserReservationStatus.AVAILABLE.toValue());
			userReservation.setReservationExpiredTime(TimeZoneConvert.dateTimezoneToDB(reservationExpiredTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
			userReservation.setUpdatedBy(String.valueOf(userId));
			
			userReservationDao.updateForVersion(userReservation);

			needSendEmailUserReservation.add(userReservation);
		}
		
		if(needSendEmailUserReservation.size() > 0){
			PushRequestUtil.sendUserReservationPushMessage(needSendEmailUserReservation);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserReservationItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		final List<UserReservationItemDTO> userReservations = (List<UserReservationItemDTO>) super.findByFilter(criteriaMap, sortField, sortType, args);
		
		for(UserReservationItemDTO userReservation : userReservations){
			final int userReservationStatus = userReservation.getStatus().intValue();
			if(userReservationStatus == UserReservation.UserReservationStatus.CONFIRMED.toValue() || userReservationStatus == UserReservation.UserReservationStatus.AVAILABLE.toValue()){
				if(null != userReservation.getReservationExpiredTime()){
					if(DateUtil.getCurrentDate().getTime() >= userReservation.getReservationExpiredTime().getTime()){
						userReservation.setStatus(UserReservation.UserReservationStatus.EXPIRED.toValue());
					}
				}

			}else if(userReservationStatus == UserReservation.UserReservationStatus.IN_COMPLETED.toValue()){
				final String merchantTimeZone = userReservation.getMerchantTimeZone();
				final Date createTime = TimeZoneConvert.dateTimezoneToUI(userReservation.getCreatedTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone);
				final Date currentTime = TimeZoneConvert.dateTimezoneToUI(DateUtil.getCurrentDate(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone);
				
				if(!DateUtil.format(createTime).equals(DateUtil.format(currentTime))){
					userReservation.setStatus(UserReservation.UserReservationStatus.CANCELLED.toValue());
				}
			}
		}
		
		return userReservations;
	}

	@Override
	public Long findCampaignReservationCount(final Long campaignId) {
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(UserReservation.UserReservationStatus.AVAILABLE.toValue());
		statusList.add(UserReservation.UserReservationStatus.CONFIRMED.toValue());
		statusList.add(UserReservation.UserReservationStatus.SETTLED.toValue());
		
		return userReservationDao.findCampaignReservationCount(campaignId,statusList);
	}

	@Override
	public UserReservationPushNotifDTO finduserReservationPushNotifInfo(final Long id) {
		return userReservationDao.finduserReservationPushNotifInfo(id);
	}
	
	@Override
	public PagingResultVo pagingFindUserReservationReport(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final Integer startRow,final Integer maxRows) {
		int[] args = new int[] { startRow, maxRows };
		
		List<UserReservationReportDTO> resultList = userReservationDao.pagingFindUserReservationReport(criteriaMap, sortField, sortType, args);
		
		if(null != resultList && resultList.size() > 0){
			//set user reservation status
			for(UserReservationReportDTO userReservationReport : resultList){
				initReservationStatusValue(userReservationReport);
//				initReservationExchangeTypeValue(userReservationReport);
				initReservationTime(userReservationReport);
			}
		}
		
		PagingResultVo result = new PagingResultVo();
		result.setResultList(resultList);
		
		if (resultList instanceof PagingList) {
			PagingList<UserReservationReportDTO> pagingResultList = (PagingList<UserReservationReportDTO>) resultList;
			result.setTotalRows(pagingResultList.getTotalRecords());
		}
		
		return result;
	}
	
	@Override
	public List<UserReservationReportDTO> findUserReservationReport(final Long merchantId, final Long campaignId, final String reservationStartTime,final String reservationEndTime, final String pickupStartTime, final String pickupEndTime, final Integer status) {
		List<UserReservationReportDTO> userReservationReports = userReservationDao.findUserReservationReport(merchantId, campaignId, reservationStartTime, reservationEndTime,pickupStartTime,pickupEndTime,status);
		
		if(null != userReservationReports && userReservationReports.size() > 0){
			//set user reservation status
			for(UserReservationReportDTO userReservationReport : userReservationReports){
				initReservationStatusValue(userReservationReport);
				initReservationTime(userReservationReport);
			}
		}
		
		return userReservationReports;
	}
	
	private void initReservationTime(UserReservationReportDTO userReservationReport) {
		final String merchantTimeZone = userReservationReport.getMerchantTimeZone();
		if(null != userReservationReport.getCreatedTime()){
			userReservationReport.setCreatedTime(TimeZoneConvert.dateTimezoneToUI(userReservationReport.getCreatedTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
		}
		if(null != userReservationReport.getReservationExpiredTime()){
			userReservationReport.setReservationExpiredTime(TimeZoneConvert.dateTimezoneToUI(userReservationReport.getReservationExpiredTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
		}
		if(UserReservation.UserReservationStatus.SETTLED.toValue() == userReservationReport.getStatus().intValue() && null != userReservationReport.getUpdatedTime()){
			userReservationReport.setPickupTime(TimeZoneConvert.dateTimezoneToUI(userReservationReport.getUpdatedTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
		}
	}
	
	private void initReservationStatusValue(final UserReservationReportDTO userReservationReport) {
		final int userReservationStatus = userReservationReport.getStatus().intValue();
		if(userReservationStatus == UserReservation.UserReservationStatus.CONFIRMED.toValue()){
			userReservationReport.setStatusValue(UserReservation.UserReservationStatus.CONFIRMED.toInfo());
		}else if(userReservationStatus == UserReservation.UserReservationStatus.AVAILABLE.toValue()){
			userReservationReport.setStatusValue(UserReservation.UserReservationStatus.AVAILABLE.toInfo());
			if(null != userReservationReport.getReservationExpiredTime()){
				if(DateUtil.getCurrentDate().getTime() >= userReservationReport.getReservationExpiredTime().getTime()){
					userReservationReport.setStatusValue(UserReservation.UserReservationStatus.EXPIRED.toInfo());
				}
			}
		}else if(userReservationStatus == UserReservation.UserReservationStatus.IN_COMPLETED.toValue()){
			userReservationReport.setStatusValue(UserReservation.UserReservationStatus.IN_COMPLETED.toInfo());
			
			final String merchantTimeZone = userReservationReport.getMerchantTimeZone();
			final Date createTime = TimeZoneConvert.dateTimezoneToUI(userReservationReport.getCreatedTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone);
			final Date currentTime = TimeZoneConvert.dateTimezoneToUI(DateUtil.getCurrentDate(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone);
			
			if(!DateUtil.format(createTime).equals(DateUtil.format(currentTime))){
				userReservationReport.setStatusValue(UserReservation.UserReservationStatus.CANCELLED.toInfo());
			}
		}else if(userReservationStatus == UserReservation.UserReservationStatus.SETTLED.toValue()){
			userReservationReport.setStatusValue(UserReservation.UserReservationStatus.SETTLED.toInfo());
		}
	}
}
