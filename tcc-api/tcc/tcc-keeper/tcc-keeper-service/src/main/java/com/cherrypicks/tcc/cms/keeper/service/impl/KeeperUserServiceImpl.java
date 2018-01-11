package com.cherrypicks.tcc.cms.keeper.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.StoreRequestUtil;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.KeeperUserDetailDTO;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.KeeperUserIsExistException;
import com.cherrypicks.tcc.cms.exception.KeeperUserNotExistException;
import com.cherrypicks.tcc.cms.exception.KeeperUserStaffIdIsExistException;
import com.cherrypicks.tcc.cms.exception.MerchantNotExistException;
import com.cherrypicks.tcc.cms.exception.StoreNotExistException;
import com.cherrypicks.tcc.cms.jedis.RedisKey;
import com.cherrypicks.tcc.cms.keeper.dao.KeeperUserDao;
import com.cherrypicks.tcc.cms.keeper.dao.cache.KeeperUserCacheDao;
import com.cherrypicks.tcc.cms.keeper.service.KeeperUserService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.KeeperUser;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.Store;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.EncryptionUtil;
import com.cherrypicks.tcc.util.Json;

@Service
public class KeeperUserServiceImpl extends AbstractBaseService<KeeperUser> implements KeeperUserService {
	
	@Autowired
	private KeeperUserDao keeperUserDao;
	
	@Autowired
	private KeeperUserCacheDao keeperUserCacheDao;
	
	@Value("${user.expiry.seconds}")
    private Integer userExpirySeconds;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<KeeperUser> keeperUserDao) {
		super.setBaseDao(keeperUserDao);
	}

	@Override
	@Transactional
	public KeeperUser createKeeperUser(final Long userId, final Long merchantId, final Long storeId, final String userName,final String password,final String email, final String mobile,
			final String staffId,final Integer status,final String lang) {
		Merchant merchant = MerchantRequestUtil.findById(merchantId);
		if(null == merchant){
			throw new MerchantNotExistException();
		}
		
		Store store = StoreRequestUtil.findById(storeId);
		if(null == store){
			throw new StoreNotExistException();
		}
		
		if(store.getMerchantId().intValue() != merchantId.intValue()){
			throw new ForbiddenException();
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, merchantId, lang);
		
		KeeperUser checkUserNameKeeperUser = keeperUserDao.findByUserNameAndMerchantId(userName,merchantId);
		if(null != checkUserNameKeeperUser){
			throw new KeeperUserIsExistException();
		}
		
		checkUserNameKeeperUser = keeperUserDao.findByStaffIdAndMerchantId(staffId,merchantId);
		if(null != checkUserNameKeeperUser){
			throw new KeeperUserStaffIdIsExistException();
		}
		
		KeeperUser addKeeperUser = new KeeperUser();
		
		addKeeperUser.setUserName(userName);
		addKeeperUser.setPassword(EncryptionUtil.getMD5(password));
		addKeeperUser.setMerchantId(merchantId);
		addKeeperUser.setStoreId(storeId);
		addKeeperUser.setStaffId(staffId);
		addKeeperUser.setCreatedBy(String.valueOf(userId));
		addKeeperUser.setCreatedTime(DateUtil.getCurrentDate());
		addKeeperUser.setIsDeleted(false);
		addKeeperUser.setSession(StringUtils.EMPTY);
		
		
		addKeeperUser.setStatus(KeeperUser.KeeperUserStatus.ACTIVE.toValue());
		if(null != status && status.intValue() == KeeperUser.KeeperUserStatus.IN_ACTIVE.toValue()){
			addKeeperUser.setStatus(status);
		}
		
		if(StringUtils.isNotBlank(email)){
			addKeeperUser.setEmail(email);
		}
		
		if(StringUtils.isNotBlank(mobile)){
			addKeeperUser.setMobile(mobile);
		}
		
		addKeeperUser = keeperUserDao.add(addKeeperUser);
		
		return addKeeperUser;
	}

	@Override
	@Transactional
	public void updateKeeperUser(final Long userId, final Long id, final Long storeId, final String userName,final String email, final String mobile,
			final String staffId,final String password,final Integer status,final String lang) {
		
		KeeperUser keeperUser = keeperUserDao.get(id);
		
		if(null == keeperUser){
			throw new KeeperUserNotExistException();
		}
		
		if(StringUtils.isNotBlank(staffId) && !staffId.equals(keeperUser.getStaffId())){
			final KeeperUser checkUserNameKeeperUser = keeperUserDao.findByStaffIdAndMerchantId(staffId,keeperUser.getMerchantId());
			if(null != checkUserNameKeeperUser){
				throw new KeeperUserStaffIdIsExistException();
			}
			keeperUser.setStaffId(staffId);
		}
		
		if(null != storeId && keeperUser.getStoreId().intValue() != storeId.intValue()){
			Store store = StoreRequestUtil.findById(storeId);
			if(null == store){
				throw new StoreNotExistException();
			}
			
			if(store.getMerchantId().intValue() != keeperUser.getMerchantId().intValue()){
				throw new ForbiddenException();
			}
			
			keeperUser.setStoreId(storeId);
			keeperUser.setSession(StringUtils.EMPTY);
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, keeperUser.getMerchantId(), lang);
		
		keeperUser.setEmail(email);
		keeperUser.setMobile(mobile);
		keeperUser.setUpdatedBy(String.valueOf(userId));
		
		if(StringUtils.isNotBlank(userName) && !userName.equals(keeperUser.getUserName())){
			KeeperUser checkUserNameKeeperUser = keeperUserDao.findByUserNameAndMerchantId(userName,keeperUser.getMerchantId());
			if(null != checkUserNameKeeperUser){
				throw new KeeperUserIsExistException();
			}
			keeperUser.setUserName(userName);
		}
		
		if(StringUtils.isNotBlank(password)){
			keeperUser.setPassword(EncryptionUtil.getMD5(password));
			keeperUser.setSession(StringUtils.EMPTY);
		}
		
		if(null != status){
			keeperUser.setStatus(KeeperUser.KeeperUserStatus.ACTIVE.toValue());
			if(status.intValue() == KeeperUser.KeeperUserStatus.IN_ACTIVE.toValue()){
				keeperUser.setStatus(status);
			}
		}
		
		keeperUser = keeperUserDao.updateForVersion(keeperUser);
		
		updateKeeperUserCache(keeperUser);
	}

	private void updateKeeperUserCache(KeeperUser keeperUser) {
		final String redisKey = RedisKey.getKeeperUserKey(keeperUser.getMerchantId(), keeperUser.getId());
		keeperUserCacheDao.addString(redisKey, Json.toJson(keeperUser));
        keeperUserCacheDao.expire(redisKey, userExpirySeconds);
	}

	@Override
	public KeeperUserDetailDTO findKeeperUserDetail(final Long userId, final Long keeperUserId, final String lang) {
		
		KeeperUserDetailDTO keeperUserDetail = keeperUserDao.findDetailById(keeperUserId);

		if(null == keeperUserDetail){
			return null;
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, keeperUserDetail.getMerchantId(),lang);
		
		return keeperUserDetail;
	} 
	
	
	
	
}
