package com.cherrypicks.tcc.cms.push.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.PushNotifLangMapDTO;
import com.cherrypicks.tcc.model.PushNotifLangMap;

public interface PushNotifLangMapDao extends IBaseDao<PushNotifLangMap> {

	List<PushNotifLangMapDTO> findByPushNotifId(Long pushNotifId);


}
