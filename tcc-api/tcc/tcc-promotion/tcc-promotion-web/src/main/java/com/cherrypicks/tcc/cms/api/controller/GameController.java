package com.cherrypicks.tcc.cms.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cherrypicks.tcc.cms.api.http.util.SystemRoleRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.SystemUserMerchantMapRequestUtil;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.dto.GameDetailDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.promotion.service.GameService;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.model.Game;
import com.cherrypicks.tcc.model.SystemRole;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class GameController extends BaseController<Game>{
	
	@Autowired
	private GameService gameService;
	
	@Override
	@Autowired
	public void setBaseService(IBaseService<Game> gameService) {
		// TODO Auto-generated method stub
		super.setBaseService(gameService);
	}
	
	
	@RequestMapping(value="/getGameList",method=RequestMethod.POST)
	public PagingResultVo getGameList(final Long userId,Long merchantId,final Integer status, final String name,final String sortField,final String sortType,final Integer startRow,final Integer maxRows, final String langCode,final String lang){
		
		AssertUtil.notBlank(sortField, "Sort field "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(sortType, "Sort type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startRow, "Start row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(maxRows, "Max row "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		Map<String,Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("langCode", langCode);
		criteriaMap.put("status", status);
		criteriaMap.put("merchantId", merchantId);
		criteriaMap.put("name", name);
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			merchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
			criteriaMap.put("merchantId", merchantId);
		}
		
		return super.doFetch(startRow, maxRows, sortField, sortType, criteriaMap);
	}
	
	@RequestMapping(value="/getHomePageGameList",method=RequestMethod.POST)
	public ResultVO getHomePageGameList(final Long userId,Long merchantId,final Integer status,final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));

		List<HomePageItemDTO> games = gameService.findHomePageGameList(userId,merchantId,status,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(games);
		return result;
	}
	
	@RequestMapping(value="/getGameDetail",method=RequestMethod.POST)
	public ResultVO getGameDetail(final Long userId,final Long gameId,final String lang){
		
		AssertUtil.notNull(gameId, "Game id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		GameDetailDTO gameDetail = gameService.findDetailById(userId,gameId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(gameDetail);
		return result;
	}
	
	@RequestMapping(value="/createGame",method=RequestMethod.POST)
	public ResultVO addGame(final Long userId,final Long merchantId,final String webUrl,final Integer inappOpen,final Date startTime,final Date endTime,final String langData, final String lang){
		
		AssertUtil.notNull(merchantId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(langData, "Lang data "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(startTime, "Start time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(endTime, "End time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(inappOpen, "In app "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		if(startTime.getTime() > endTime.getTime()){
			throw new IllegalArgumentException("End time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		Game game = gameService.createGame(userId,merchantId,webUrl,inappOpen,startTime,endTime,langData,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(game);
		return result;
	}
	
	@RequestMapping(value="/updateGame",method=RequestMethod.POST)
	public SuccessVO updateGame(final Long userId,final Long gameId,final String webUrl,final Integer status,final Integer inappOpen,final Date startTime,final Date endTime,final String langData, final String lang){
		
		AssertUtil.notNull(gameId, "Merchant id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
	
		if(null != startTime && null != endTime){
			if(startTime.getTime() > endTime.getTime()){
				throw new IllegalArgumentException("End time  "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}
		
		gameService.updateGame(userId,gameId,webUrl,status,inappOpen,startTime,endTime,langData,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/deleteGame",method=RequestMethod.POST)
	public SuccessVO deleteGame(final Long userId,final String ids,final String lang){
		
		AssertUtil.notBlank(ids, "Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		//判断用户属于Platform admin
		final SystemRole role = SystemRoleRequestUtil.findByUserId(userId);
		Long userMerchantId = null;
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			userMerchantId = SystemUserMerchantMapRequestUtil.findMerchantIdBySystemUserId(userId);
		}
		
		gameService.delByIds(userId,userMerchantId,ids,lang);
		
		return new SuccessVO();
	}
}
