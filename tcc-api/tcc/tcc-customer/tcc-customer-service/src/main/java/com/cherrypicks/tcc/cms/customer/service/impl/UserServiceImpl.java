package com.cherrypicks.tcc.cms.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.customer.dao.UserDao;
import com.cherrypicks.tcc.cms.customer.dao.UserStampCardDao;
import com.cherrypicks.tcc.cms.customer.service.UserService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.UserDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReportDTO;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.User;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;
import com.cherrypicks.tcc.util.paging.PagingList;

@Service
public class UserServiceImpl extends AbstractBaseService<User> implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserStampCardDao userStampCardDao;

	@Override
	@Autowired
	public void setBaseDao(final IBaseDao<User> userDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(userDao);
	}

	@Override
	public UserDetailDTO findDetailByID(final Long userId, final Long id, final String lang) {
		final UserDetailDTO userDetail = userDao.findDetailById(id);

		if(null != userDetail){

			AuthorizeRequestUtil.checkUserMerchantPermission(userId, userDetail.getMerchantId(), lang);

			return userDetail;
		}

		return null;
	}

	@Override
	public List<Long> findUserIdsByMerchantId(final Long merchantId) {
		return userDao.findUserIdsByMerchantId(merchantId);
	}
	
	@Override
	public List<UserReportDTO> findUserReport(final Long merchantId, final String startTime, final String endTime) {
		final List<UserReportDTO> users = userDao.findUserReport(merchantId,startTime,endTime);

		initUserReportValue(users);

		return users;
	}
	
	@Override
	public PagingResultVo pagingFindUserReport(final Map<String, Object> criteriaMap, final String sortField, final String sortType,final Integer startRow,final Integer maxRows) {

		final int[] args = new int[] { startRow, maxRows };

		final List<UserReportDTO> resultList = userDao.pagingFindUserReport(criteriaMap, sortField, sortType, args);

		if(null != resultList && resultList.size() > 0 ){
			initUserReportValue(resultList);
		}

		final PagingResultVo result = new PagingResultVo();
		result.setResultList(resultList);

		if (resultList instanceof PagingList) {
			final PagingList<UserReportDTO> pagingResultList = (PagingList<UserReportDTO>) resultList;
			result.setTotalRows(pagingResultList.getTotalRecords());
		}

		return result;
	}
	
	private void initUserReportValue(final List<UserReportDTO> users) {
		for(final UserReportDTO user : users){
			if(null != user.getBirthday()){
				user.setBirthdayValue(DateUtil.format(user.getBirthday(), DateUtil.DATE_PATTERN_MMDDYYYY));
			}

			final String merchantTimeZone = user.getMerchantTimeZone();
			user.setCreatedTime(TimeZoneConvert.dateTimezoneToUI(user.getCreatedTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
			
			if(null != user.getLastMobileVerifyTime()) {
				user.setLastMobileVerifyTime(TimeZoneConvert.dateTimezoneToUI(user.getLastMobileVerifyTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
			}
		}
	}

	@Override
	public Long findCampaignTotalUserCount(Long campaignId) {
		return userStampCardDao.findCampaignTotalUserCount(campaignId);
	}
}
