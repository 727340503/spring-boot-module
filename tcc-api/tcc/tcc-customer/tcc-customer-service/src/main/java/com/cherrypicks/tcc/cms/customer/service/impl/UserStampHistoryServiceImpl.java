package com.cherrypicks.tcc.cms.customer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.CampaignRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.StampRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserRequestUtil;
import com.cherrypicks.tcc.cms.customer.dao.UserStampCardDao;
import com.cherrypicks.tcc.cms.customer.dao.UserStampDao;
import com.cherrypicks.tcc.cms.customer.dao.UserStampHistoryDao;
import com.cherrypicks.tcc.cms.customer.service.UserStampHistoryService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.CampaignDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserStampHistoryDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserStampHistoryReportDTO;
import com.cherrypicks.tcc.cms.exception.CampaignStatusIsNotActiveException;
import com.cherrypicks.tcc.cms.exception.StampNotExistException;
import com.cherrypicks.tcc.cms.exception.SubUserStampsException;
import com.cherrypicks.tcc.cms.exception.UserStampCardNotExistException;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Campaign;
import com.cherrypicks.tcc.model.CampaignGiftExchangeType;
import com.cherrypicks.tcc.model.Stamp;
import com.cherrypicks.tcc.model.SystemUser;
import com.cherrypicks.tcc.model.UserStamp;
import com.cherrypicks.tcc.model.UserStamp.UserStampStatus;
import com.cherrypicks.tcc.model.UserStampCard;
import com.cherrypicks.tcc.model.UserStampHistory;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ListUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;
import com.cherrypicks.tcc.util.paging.PagingList;

@Service
public class UserStampHistoryServiceImpl extends AbstractBaseService<UserStampHistory> implements UserStampHistoryService {

	@Autowired
	private UserStampHistoryDao userStampHistoryDao;

	@Autowired
	private UserStampCardDao userStampCardDao;

	@Autowired
	private UserStampDao userStampDao;

	@Override
	@Autowired
	public void setBaseDao(final IBaseDao<UserStampHistory> userStampHistoryDao) {
		super.setBaseDao(userStampHistoryDao);
	}

	@Override
	public Long findUserTotalStampNo(final Long userId, final Long campaignId) {
		return userStampHistoryDao.findUserTotalStampNo(userId, campaignId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserStampHistoryDetailDTO> findByFilter(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final int... args) {
		final List<UserStampHistoryDetailDTO> userStampHistorys = (List<UserStampHistoryDetailDTO>) super.findByFilter(
				criteriaMap, sortField, sortType, args);

		if (null != userStampHistorys && userStampHistorys.size() > 0) {
			for (final UserStampHistoryDetailDTO userStampHistory : userStampHistorys) {
				userStampHistory.setTransDateTime(TimeZoneConvert.dateTimezoneToUI(userStampHistory.getTransDateTime(),userStampHistory.getMerchantTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
			}
		}

		return userStampHistorys;
	}

	@Override
	@Transactional
	public void createUserStampHistory(final Long userId, final Long customerId, final Long campaignId, final Integer type, final Long stampQty, final String remarks, final String lang) {

		if (type.intValue() != UserStampHistory.UserStampHistoryType.SYSTEM_IN_STAMPS.toValue() && type.intValue() != UserStampHistory.UserStampHistoryType.SYSTEM_OUT_STAMPS.toValue()) {
			throw new IllegalArgumentException( "Type " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		if(stampQty <= 0){
			throw new IllegalArgumentException("Stamp qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		final UserStampCard userStampCard = userStampCardDao.findByUserAndCampaign(customerId, campaignId);
		if (null == userStampCard) {
			throw new UserStampCardNotExistException();
		}

		final CampaignDetailDTO campaignInfo = CampaignRequestUtil.findDetailById(userId, campaignId, lang);
		if(campaignInfo.getStatus().intValue() == Campaign.CampaignStatus.EXPIRED.toValue() ||
				campaignInfo.getStatus().intValue() == Campaign.CampaignStatus.IN_ACTIVE.toValue() ||
				campaignInfo.getStatus().intValue() == Campaign.CampaignStatus.PENDING.toValue()){
			throw new CampaignStatusIsNotActiveException();
		}

		AuthorizeRequestUtil.checkUserMerchantPermission(userId, userStampCard.getMerchantId(), lang);

		final SystemUser systemUser = SystemUserRequestUtil.findById(userId);

		final UserStampHistory userStampHistory = new UserStampHistory();
		userStampHistory.setUserId(customerId);
		userStampHistory.setStampQty(stampQty);
		userStampHistory.setExchangeType(CampaignGiftExchangeType.Type.STAMPS.getCode());
		userStampHistory.setMerchantId(userStampCard.getMerchantId());
		userStampHistory.setReadStatus(UserStampHistory.UserStampHistoryReadStatus.UN_READ.toValue());
		userStampHistory.setType(type);
		userStampHistory.setUserStampCardId(userStampCard.getId());
		userStampHistory.setCreatedTime(DateUtil.getCurrentDate());
		userStampHistory.setCreatedBy(systemUser.getUserName());
		userStampHistory.setRemarks(remarks);

		userStampHistoryDao.add(userStampHistory);

		if (type.intValue() == UserStampHistory.UserStampHistoryType.SYSTEM_IN_STAMPS.toValue()) {
			// add user stamps
			addUserStamps(customerId, userStampCard, stampQty, systemUser);
		} else {// sub user stamps
			subUserStamps(customerId, userStampCard, stampQty, systemUser);
		}

	}

	private void subUserStamps(final Long customerId, final UserStampCard userStampCard, final Long stampQty, final SystemUser systemUser) {
		final List<Long> userUnRedeemedStamps = userStampDao.findUserUnRedeemedStamps(customerId, userStampCard.getId());
		if(userStampCard.getCollectStampQty().longValue() < stampQty || userUnRedeemedStamps.size() < stampQty){
			throw new SubUserStampsException();
		}
		final List<Long> delIds = userUnRedeemedStamps.subList(0, stampQty.intValue());

		final List<Object> delIdList = new ArrayList<Object>();
		delIdList.addAll(delIds);

		//sub user stamps
		userStampDao.delByIds(delIdList);

		userStampCard.setUpdatedBy(systemUser.getUserName());
		userStampCard.setCollectStampQty(userStampCard.getCollectStampQty().longValue() - stampQty.longValue());
		//sub user stamps total num
		userStampCardDao.updateForVersion(userStampCard);
	}

	private void addUserStamps(final Long customerId, final UserStampCard userStampCard, final Long stampQty, final SystemUser systemUser) {

		final List<Stamp> stampList = StampRequestUtil.findByStampCardId(userStampCard.getStampCardId());

		UserStamp userStamp = null;
		final List<UserStamp> userStampList = new ArrayList<UserStamp>();
		//add user stamp
		if (ListUtil.isNotEmpty(stampList)) {
			for (int i = 0; i < stampQty; i++) {
				userStamp = new UserStamp();
				userStamp.setMerchantId(userStampCard.getMerchantId());
				userStamp.setStampId(stampList.get(new Random().nextInt(stampList.size())).getId());
				userStamp.setUserStampCardId(userStampCard.getId());
				userStamp.setUserId(userStampCard.getUserId());
				userStamp.setStatus(UserStampStatus.ACTIVE.toValue());
				userStamp.setCreatedBy(systemUser.getUserName());
				userStamp.setCreatedTime(DateUtil.getCurrentDate());
				userStampList.add(userStamp);
			}
			if (ListUtil.isNotEmpty(userStampList)) {
				userStampDao.batchAdd(userStampList);
			}
		}else{
			throw new StampNotExistException();
		}

		userStampCard.setCollectStampQty(userStampCard.getCollectStampQty()+stampQty);
		userStampCard.setUpdatedBy(systemUser.getUserName());

		//update user stamp total
		userStampCardDao.updateForVersion(userStampCard);
	}

	@Override
	public List<UserStampHistoryReportDTO> findUserStampHistoryReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final List<Integer> types) {
		final List<UserStampHistoryReportDTO> userStampHistorys = userStampHistoryDao.findUserStampHistoryReport(merchantId, campaignId, startTime, endTime, types);

		if (null != userStampHistorys && userStampHistorys.size() > 0) {
			initStampHistoryReport(userStampHistorys);
		}

		return userStampHistorys;
	}

	private void initStampHistoryReport(final List<UserStampHistoryReportDTO> userStampHistorys) {
		for (final UserStampHistoryReportDTO userStampHistory : userStampHistorys) {
			final String merchantTimeZone = userStampHistory.getMerchantTimeZone();

			userStampHistory.setCreatedTime(TimeZoneConvert.dateTimezoneToUI(userStampHistory.getCreatedTime(), merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));

			if(null != userStampHistory.getTransDateTime()){
				userStampHistory.setTransDateTime(TimeZoneConvert.dateTimezoneToUI(userStampHistory.getTransDateTime(), merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
			}

			if(null != userStampHistory.getReservationTime()){
				userStampHistory.setReservationTime(TimeZoneConvert.dateTimezoneToUI(userStampHistory.getReservationTime(), merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
			}

			if(null != userStampHistory.getType()){
				final Integer userStampHistoryType = userStampHistory.getType().intValue();

				if(userStampHistoryType == UserStampHistory.UserStampHistoryType.COLLECT_STAMPS.toValue()){
					userStampHistory.setTypeValue(UserStampHistory.UserStampHistoryType.COLLECT_STAMPS.toName());
				}else if(userStampHistoryType == UserStampHistory.UserStampHistoryType.SYSTEM_IN_STAMPS.toValue()){
					userStampHistory.setTypeValue(UserStampHistory.UserStampHistoryType.SYSTEM_IN_STAMPS.toName());
				}else if(userStampHistoryType == UserStampHistory.UserStampHistoryType.SYSTEM_OUT_STAMPS.toValue()){
					userStampHistory.setTypeValue(UserStampHistory.UserStampHistoryType.SYSTEM_OUT_STAMPS.toName());
					userStampHistory.setStampQty(0-userStampHistory.getStampQty());
				}else if(userStampHistoryType == UserStampHistory.UserStampHistoryType.GRANT.toValue()){
					userStampHistory.setTypeValue(UserStampHistory.UserStampHistoryType.GRANT.toName());
				}
			}
		}
	}

	@Override
	public PagingResultVo pagingFindUserStampHistory(final Map<String, Object> criteriaMap, final String sortField, final String sortType, final Integer startRow, final Integer maxRows) {
		final int[] args = new int[] { startRow, maxRows };

		final List<UserStampHistoryReportDTO> resultList = userStampHistoryDao.pagingFindUserStampHistoryReport(criteriaMap, sortField, sortType, args);

		if (null != resultList && resultList.size() > 0) {
			initStampHistoryReport(resultList);
		}

		final PagingResultVo result = new PagingResultVo();
		result.setResultList(resultList);

		if (resultList instanceof PagingList) {
			final PagingList<UserStampHistoryReportDTO> pagingResultList = (PagingList<UserStampHistoryReportDTO>) resultList;
			result.setTotalRows(pagingResultList.getTotalRecords());
		}

		return result;
	}

	@Override
	public Long findTotalByTypes(Long campaignId, List<Integer> userStampHistoryTypes) {
		
		return userStampHistoryDao.findTotalItemCountByType(campaignId,userStampHistoryTypes);
	}
}
