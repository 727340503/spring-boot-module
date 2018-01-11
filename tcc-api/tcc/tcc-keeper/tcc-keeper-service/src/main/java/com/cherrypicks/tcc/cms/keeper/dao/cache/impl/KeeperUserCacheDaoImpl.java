package com.cherrypicks.tcc.cms.keeper.dao.cache.impl;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.cache.impl.AbstractBaseCacheDao;
import com.cherrypicks.tcc.cms.keeper.dao.cache.KeeperUserCacheDao;
import com.cherrypicks.tcc.model.KeeperUser;

@Repository
public class KeeperUserCacheDaoImpl extends AbstractBaseCacheDao<KeeperUser> implements KeeperUserCacheDao {
}
