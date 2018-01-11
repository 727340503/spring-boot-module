package com.cherrypicks.tcc.cms.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.annotation.IPPermissionAuth;
import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.api.vo.UserStampHistoryVO;
import com.cherrypicks.tcc.cms.customer.service.UserService;
import com.cherrypicks.tcc.cms.customer.service.UserStampHistoryService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.User;
import com.cherrypicks.tcc.model.UserStampHistory;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.Json;

@RestController
public class UserStampHistoryController extends BaseController<UserStampHistory>{

	@Autowired
	private UserService userService;

	@Autowired
	private UserStampHistoryService userStampHistoryService;

	@Override
	@Autowired
	public void setBaseService(final IBaseService<UserStampHistory> userStampHistoryService) {
		// TODO Auto-generated method stub
		super.setBaseService(userStampHistoryService);
	}

	@RequestMapping(value="/getUserStampHistoryList",method=RequestMethod.POST)
	public UserStampHistoryVO getUserStampHistoryList(final Long userId,final Long customerId,final Long campaignId, final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String lang){

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(customerId, "User id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("userId", customerId);
		criteriaMap.put("campaignId", campaignId);

		final User user = userService.findById(customerId);

		AuthorizeRequestUtil.checkUserMerchantPermission(userId, user.getMerchantId(), lang);

		final UserStampHistoryVO result = new UserStampHistoryVO();

		final PagingResultVo recordResult = super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);

		if(null != campaignId && startRow == 0 && null != recordResult.getTotalRows() && recordResult.getTotalRows() > 0 ){
			final Long userTotalStampNo = userStampHistoryService.findUserTotalStampNo(customerId,campaignId);
			result.setTotalStampNo(userTotalStampNo);
		}

		result.setResult(recordResult);
		return result;
	}

	@RequestMapping(value="/getUserRedeemHistoryList",method=RequestMethod.POST)
	public PagingResultVo getUserRedeemHistoryList(final Long userId,final Long customerId,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String lang){

		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(customerId, "User id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		final Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("userId", customerId);
		criteriaMap.put("type", UserStampHistory.UserStampHistoryType.REDEEM.toValue());

		final User user = userService.findById(customerId);

		AuthorizeRequestUtil.checkUserMerchantPermission(userId, user.getMerchantId(), lang);

		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}

	@RequestMapping(value="/updateUserStampCount",method=RequestMethod.POST)
	public SuccessVO createUserStampHistory(final Long userId,final Long customerId,final Long campaignId,final Integer type,final Long stampQty,final String remarks,final String lang){

		AssertUtil.notNull(customerId, "Customer id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(type, "Type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(stampQty, "Stamp qty "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(remarks, "Remarks "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		userStampHistoryService.createUserStampHistory(userId, customerId, campaignId, type, stampQty,remarks,lang);

		return new SuccessVO();
	}

	@IPPermissionAuth
	@RequestMapping(value = "/private/exportUserStampHistoryReport", method = RequestMethod.POST)
	public ResultVO viewTransferStampsReport(final Long merchantId, final Long campaignId, final String startTime, final String endTime, 
			final String userStampHistoryTypes) throws Exception {

		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(campaignId, "Campaign id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(startTime, "Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(endTime, "End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(userStampHistoryTypes, "Types "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
	
		ResultVO result = new ResultVO();
		result.setResult(Json.toJson(userStampHistoryService.findUserStampHistoryReport(merchantId, campaignId, startTime, endTime, Json.toListObject(userStampHistoryTypes, Integer.class))));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value = "/private/getStampHistoryCountByType", method = RequestMethod.POST)
	public ResultVO getStampHistryCountByTypes(final Long campaignId, final String types) throws Exception {

		List<Integer> lists = Json.toListObject(types, Integer.class);
		
		ResultVO result = new ResultVO();
		result.setResult(userStampHistoryService.findTotalByTypes(campaignId,lists));
		return result;
	}
	
	@IPPermissionAuth
	@RequestMapping(value = "/private/viewUserStampHistoryReport", method = RequestMethod.POST)
	public ResultVO getStampHistryCountByTypes(final Long userId, final Long merchantId,final Long campaignId,final String startTime,final String endTime,final String sortField,final String sortType,
			final Integer startRow,final Integer maxRows, final Boolean isTransfer, final String types,final Integer type) throws Exception {

		final Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("campaignId", campaignId);
		criteriaMap.put("startTime", startTime);
		criteriaMap.put("endTime", endTime);
		
		if(null != isTransfer) {
			criteriaMap.put("isTransfer", isTransfer);
		}
		
		if(StringUtils.isNotBlank(types)) {
			criteriaMap.put("types", Json.toListObject(types, Integer.class));
		}
		
		if(null != type) {
			criteriaMap.put("type", type);
		}
	
		ResultVO result = new ResultVO();
		result.setResult(userStampHistoryService.pagingFindUserStampHistory(criteriaMap, sortField, sortType, startRow, maxRows));
		return result;
	}
}
