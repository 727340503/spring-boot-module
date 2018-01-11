package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.customer.service.UserService;
import com.cherrypicks.tcc.cms.dto.UserDetailDTO;
import com.cherrypicks.tcc.cms.dto.UserReportDTO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.model.User;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class UserController extends BaseController<User>{
	
	@Autowired
	private UserService userService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<User> userService) {
		super.setBaseService(userService);
	}
	
	@RequestMapping(value="/getUserList",method=RequestMethod.POST)
	public PagingResultVo getUserList(final Long userId, final Long merchantId, final String userName,
									final String sortField,final String sortType,final Integer startRow,final Integer maxRows,final String langCode, final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("userName", userName);
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			criteriaMap.put("merchantId", SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId));
		}
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/getUserDetail",method=RequestMethod.POST)
	public ResultVO getUserDetail(final Long userId,final Long customerId,final String lang){
		
		AssertUtil.notNull(customerId, "User id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		UserDetailDTO userDetail = userService.findDetailByID(userId,customerId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(userDetail);
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value="/private/getCampaignTotalUserCount",method=RequestMethod.POST)
	public ResultVO getCampaignTotalUserCount(final Long campaignId){
		
		ResultVO result = new ResultVO();
		result.setResult(userService.findCampaignTotalUserCount(campaignId));
		return result;
	}

	@IPPermissionAuth
	@RequestMapping(value="/private/exportUserReport",method=RequestMethod.POST)
	public ResultVO exportUserReport(final Long merchantId, final String startTime, final String endTime){
		
		List<UserReportDTO> userReports = userService.findUserReport(merchantId, startTime, endTime);
		
		ResultVO result = new ResultVO();
		result.setResult(userReports);
		return result;
	}
	
	
	@RequestMapping(value = "/private/getViewUserReportData", method = RequestMethod.POST)
	public ResultVO viewUserReport(final Long userId,final Long merchantId, final String startTime, final String endTime, final String sortField, final String sortType, final Integer startRow, final Integer maxRows, final String lang) throws Exception {
		
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("startTime", startTime);
		criteriaMap.put("endTime", endTime);
		
		ResultVO result = new ResultVO();
		result.setResult(userService.pagingFindUserReport(criteriaMap, sortField, sortType, startRow, maxRows));
		return result;
	}
}