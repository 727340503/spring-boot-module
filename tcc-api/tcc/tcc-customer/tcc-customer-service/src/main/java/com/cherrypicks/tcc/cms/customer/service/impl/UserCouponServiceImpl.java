package com.cherrypicks.tcc.cms.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.CouponRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserRequestUtil;
import com.cherrypicks.tcc.cms.customer.dao.UserCouponDao;
import com.cherrypicks.tcc.cms.customer.service.UserCouponService;
import com.cherrypicks.tcc.cms.customer.service.UserService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.UserCouponItemDTO;
import com.cherrypicks.tcc.cms.dto.UserCouponReportDTO;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Coupon;
import com.cherrypicks.tcc.model.SystemUser;
import com.cherrypicks.tcc.model.User;
import com.cherrypicks.tcc.model.UserCoupon;
import com.cherrypicks.tcc.util.Constants;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.ListUtil;
import com.cherrypicks.tcc.util.RandomUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;
import com.cherrypicks.tcc.util.paging.PagingList;

@Service
public class UserCouponServiceImpl extends AbstractBaseService<UserCoupon> implements UserCouponService {
	
	@Autowired
	private UserCouponDao userCouponDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<UserCoupon> couponDao) {
		super.setBaseDao(couponDao);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserCouponItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		final List<UserCouponItemDTO> userCoupons = (List<UserCouponItemDTO>) super.findByFilter(criteriaMap, sortField, sortType, args);
		
		if(!ListUtil.isEmpty(userCoupons)) {
			initUserCoupon(userCoupons);
		}
		
		return userCoupons;
	}

	@Override
	public Long findTotalCountByCampaignId(final Long campaignId, final Integer status) {
		
		return userCouponDao.findByTotalByCampaignId(campaignId,status);
	}

	private void initUserCoupon(List<UserCouponItemDTO> userCoupons) {
		for (final UserCouponItemDTO userCoupon : userCoupons) {
			final String merchantTimeZone = userCoupon.getMerchantTimeZone();
			
			if(userCoupon.getStatus().intValue() == UserCoupon.UserCouponStatus.REDEEMED.toValue()) {
				userCoupon.setRedeemedDate(TimeZoneConvert.dateTimezoneToUI(userCoupon.getRedeemedDate(), merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
			}else if(userCoupon.getStatus().intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue()) {
				
				Date expiredTime = userCoupon.getRedeemEndTime();
				if(userCoupon.getIsAfterCollect()) {
					final int days = userCoupon.getEndDaysAfterCollect();
					expiredTime = DateUtil.addDays(userCoupon.getCreatedTime(), days);
				}
				
				if(DateUtil.getCurrentDate().getTime() >= expiredTime.getTime()) {
					userCoupon.setStatus(UserCoupon.UserCouponStatus.EXPIRED.toValue());
				}
				
				userCoupon.setExpiredTime(TimeZoneConvert.dateTimezoneToUI(expiredTime, merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
			}
			
			userCoupon.setCreatedTime(TimeZoneConvert.dateTimezoneToUI(userCoupon.getCreatedTime(), merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		}
	}

	@Override
	@Transactional
	public void addUserCoupon(final Long userId,final Long customerId,final Long couponId, final Integer qty,final String remark, final String lang) {
		
		final User user = userService.findById(customerId);
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, user.getMerchantId(), lang);
		
		final Coupon coupon = CouponRequestUtil.findById(couponId);
	
	
		if(coupon.getMerchantId().intValue() != user.getMerchantId().intValue()) {
			throw new ForbiddenException();
		}
		
		final SystemUser cmsUser = SystemUserRequestUtil.findById(userId);
		final List<UserCoupon> addUserCoupons = new ArrayList<UserCoupon>();
		
		for(int i=0; i<qty; i++) {
			final UserCoupon userCoupon = new UserCoupon();
			userCoupon.setCreatedTime(DateUtil.getCurrentDate());
			userCoupon.setCreatedBy(getCMSHandleBy(cmsUser));
			userCoupon.setIsDeleted(false);
			
			userCoupon.setUserId(customerId);
			userCoupon.setMerchantId(user.getMerchantId());
			userCoupon.setCouponId(couponId);
			userCoupon.setRemark(remark);
			userCoupon.setStatus(UserCoupon.UserCouponStatus.ACTIVE.toValue());
			
			String qrCode = Constants.COUPON_BARCODE_PREFIX + RandomUtil.RandomCode(10, RandomUtil.TYPE_NUM);
            while (userCouponDao.findByQrCode(user.getMerchantId(), qrCode) != null) {
                qrCode = Constants.COUPON_BARCODE_PREFIX + RandomUtil.RandomCode(10, RandomUtil.TYPE_NUM);
            }
			userCoupon.setQrCode(qrCode);
			
			addUserCoupons.add(userCoupon);
		}
		
		userCouponDao.batchAdd(addUserCoupons);
	}

	@Override
	@Transactional
	public void revokeUserCoupon(final Long userId, final Long userCouponId, final String remark, final String lang) {
		final UserCoupon userCoupon = userCouponDao.get(userCouponId);
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, userCoupon.getMerchantId(), lang);
		
		final SystemUser cmsUser = SystemUserRequestUtil.findById(userId);
		
		if(!userCoupon.getIsDeleted() && userCoupon.getStatus().intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue()) {
			userCoupon.setStatus(UserCoupon.UserCouponStatus.INACTIVE.toValue());
			userCoupon.setRemark(remark);
			userCoupon.setUpdatedBy(getCMSHandleBy(cmsUser));
			
			userCouponDao.updateForVersion(userCoupon);
		}
	}
	
	@Override
	public PagingResultVo pagingFindUserCoupon(final Map<String, Object> criteriaMap, final String sortField, final String sortType,final Integer startRow,final Integer maxRows) {
		final int[] args = new int[] { startRow, maxRows };

		final List<UserCouponReportDTO> resultList = userCouponDao.pagingFindUserCoupon(criteriaMap, sortField, sortType, args);

		if (!ListUtil.isEmpty(resultList)) {
			initUserCouponReport(resultList);
		}

		final PagingResultVo result = new PagingResultVo();
		result.setResultList(resultList);

		if (resultList instanceof PagingList) {
			final PagingList<UserCouponReportDTO> pagingResultList = (PagingList<UserCouponReportDTO>) resultList;
			result.setTotalRows(pagingResultList.getTotalRecords());
		}

		return result;
	}
	
	@Override
	public List<UserCouponReportDTO> findUserCouponReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, final Integer status,final boolean isRedeemCouponReport) {
		
		final List<UserCouponReportDTO> userCoupons = userCouponDao.findUserCouponReport(merchantId, campaignId, startTime, endTime, status,isRedeemCouponReport);

		if (!ListUtil.isEmpty(userCoupons)) {
			initUserCouponReport(userCoupons);
		}

		return userCoupons;
	}
	
	private void initUserCouponReport(List<UserCouponReportDTO> userCoupons) {
		for (final UserCouponReportDTO userCoupon : userCoupons) {
			final String merchantTimeZone = userCoupon.getMerchantTimeZone();

			if(userCoupon.getStatus().intValue() == UserCoupon.UserCouponStatus.REDEEMED.toValue()) {
				userCoupon.setRedeemedDate(TimeZoneConvert.dateTimezoneToUI(userCoupon.getRedeemedDate(), merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
				userCoupon.setStatusValue(UserCoupon.UserCouponStatus.REDEEMED.toInfo());
			}else if(userCoupon.getStatus().intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue() && userCoupon.getCouponStatus().intValue() != Coupon.CouponStatus.IN_ACTIVE.toValue()) {
				userCoupon.setStatusValue(UserCoupon.UserCouponStatus.ACTIVE.toInfo());
				
				Date expiredTime = userCoupon.getRedeemEndTime();
				if(userCoupon.getIsAfterCollect()) {
					final int days = userCoupon.getEndDaysAfterCollect();
					expiredTime = DateUtil.addDays(userCoupon.getCreatedTime(), days);
				}
				
				if(DateUtil.getCurrentDate().getTime() >= expiredTime.getTime()) {
					userCoupon.setStatus(UserCoupon.UserCouponStatus.EXPIRED.toValue());
					userCoupon.setStatusValue(UserCoupon.UserCouponStatus.EXPIRED.toInfo());
				}
			}else if(userCoupon.getStatus().intValue() == UserCoupon.UserCouponStatus.ACTIVE.toValue() && userCoupon.getCouponStatus().intValue() == Coupon.CouponStatus.IN_ACTIVE.toValue()){
				userCoupon.setStatusValue(UserCoupon.UserCouponStatus.INACTIVE.toInfo());
				userCoupon.setStatus(UserCoupon.UserCouponStatus.INACTIVE.toValue());
			}else {
				userCoupon.setStatusValue(UserCoupon.UserCouponStatus.INACTIVE.toInfo());
			}

			userCoupon.setCreatedTime(TimeZoneConvert.dateTimezoneToUI(userCoupon.getCreatedTime(), merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
			
			final int type = userCoupon.getType().intValue();
			if(type == Coupon.CouponType.STAMPS.toValue()) {
				userCoupon.setTypeValue(Coupon.CouponType.STAMPS.toInfo());
			}else if(type == Coupon.CouponType.POINT.toValue()) {
				userCoupon.setTypeValue(Coupon.CouponType.POINT.toInfo());
			}else if(type == Coupon.CouponType.CASH.toValue()) {
				userCoupon.setTypeValue(Coupon.CouponType.CASH.toInfo());
			}else if(type == Coupon.CouponType.DISCOUNT.toValue()) {
				userCoupon.setTypeValue(Coupon.CouponType.DISCOUNT.toInfo());
			}else if(type == Coupon.CouponType.STAMPS_MULTIPLY.toValue()) {
				userCoupon.setTypeValue(Coupon.CouponType.STAMPS_MULTIPLY.toInfo());
			}else if(type == Coupon.CouponType.POINT_MULTIPLY.toValue()) {
				userCoupon.setTypeValue(Coupon.CouponType.POINT_MULTIPLY.toInfo());
			}
		}
	}
	
	private String getCMSHandleBy(final SystemUser cmsUser) {
		return Constants.CMS+":"+cmsUser.getUserName();
	}
}
