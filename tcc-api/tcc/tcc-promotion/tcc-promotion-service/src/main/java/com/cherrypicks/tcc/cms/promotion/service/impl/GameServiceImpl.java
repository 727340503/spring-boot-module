package com.cherrypicks.tcc.cms.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cherrypicks.tcc.cms.api.http.util.AuthorizeRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantConfigRequestUtil;
import com.cherrypicks.tcc.cms.api.http.util.MerchantRequestUtil;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.dto.GameDetailDTO;
import com.cherrypicks.tcc.cms.dto.GameItemDTO;
import com.cherrypicks.tcc.cms.dto.GameLangMapDTO;
import com.cherrypicks.tcc.cms.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.GameNotExistException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.exception.RecordIsReferencedException;
import com.cherrypicks.tcc.cms.exception.UpdateRecordStatusException;
import com.cherrypicks.tcc.cms.promotion.dao.GameDao;
import com.cherrypicks.tcc.cms.promotion.dao.GameLangMapDao;
import com.cherrypicks.tcc.cms.promotion.service.GameService;
import com.cherrypicks.tcc.cms.promotion.service.HomePageService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.model.Banner;
import com.cherrypicks.tcc.model.Game;
import com.cherrypicks.tcc.model.GameLangMap;
import com.cherrypicks.tcc.model.HomePage;
import com.cherrypicks.tcc.model.Merchant;
import com.cherrypicks.tcc.model.MerchantConfig;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.JsonUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Service
public class GameServiceImpl extends AbstractBaseService<Game> implements GameService {
	
	@Autowired
	private GameDao gameDao;
	
	@Autowired
	private GameLangMapDao gameLangMapDao;
	
	@Autowired
	private HomePageService homePageService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Game> gameDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(gameDao);
	}

	@Override
	public GameDetailDTO findDetailById(final Long userId, final Long gameId, final String lang) {
		Game game = gameDao.get(gameId);
		
		if(null != game){
			
			AuthorizeRequestUtil.checkUserMerchantPermission(userId, game.getMerchantId(), lang);
			
			Merchant merchant = MerchantRequestUtil.findById(game.getMerchantId());
			
			GameDetailDTO gameDetail = new GameDetailDTO();
			BeanUtils.copyProperties(game, gameDetail);
			if(gameDetail.getStatus().intValue() == Game.Status.ACTIVE.getCode()){
				long nowTime = DateUtil.getCurrentDate().getTime();
				if(gameDetail.getStartTime().getTime() > nowTime){
					gameDetail.setStatus(Game.Status.PENDING.getCode());
				}else if(gameDetail.getEndTime().getTime() < nowTime){
					gameDetail.setStatus(Game.Status.EXPIRED.getCode());
				}
			}
			
			gameDetail.setStartTime(TimeZoneConvert.dateTimezoneToUI(gameDetail.getStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			gameDetail.setEndTime(TimeZoneConvert.dateTimezoneToUI(gameDetail.getEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchant.getTimeZone()));
			
			List<GameLangMapDTO> gameLangMaps = gameLangMapDao.findByGameId(gameId);
			
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(game.getMerchantId());
			
			for(GameLangMapDTO gameLangMap : gameLangMaps){
				gameLangMap.setFullImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), gameLangMap.getImg()));
			}
			
			gameDetail.setGameLangMaps(gameLangMaps);
			
			return gameDetail;
		}
		
		return null;
	}

	@Override
	@Transactional
	public Game createGame(final Long userId, final Long merchantId, final String webUrl, final Integer inappOpen,final Date startTime,final Date endTime,final String langData, final String lang) {
		
		List<GameLangMap> gameLangMaps = JsonUtil.toListObject(langData, GameLangMap.class);
		if(null == gameLangMaps){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, merchantId, lang);
		
		Merchant merchant = MerchantRequestUtil.findById(merchantId);
		
		Game game = new Game();
		game.setCreatedBy(String.valueOf(userId));
		game.setCreatedTime(DateUtil.getCurrentDate());
		game.setMerchantId(merchantId);
		if(StringUtils.isNotBlank(webUrl)){
			game.setWebUrl(webUrl);
		}
		game.setStatus(Game.Status.ACTIVE.getCode());
		game.setStartTime(TimeZoneConvert.dateTimezoneToDB(startTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		game.setEndTime(TimeZoneConvert.dateTimezoneToDB(endTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		game.setInappOpen(inappOpen);
		
		game = gameDao.add(game);
		
		createGameLangMap(game,gameLangMaps,lang);
		
		return null;
	}

	private void createGameLangMap(Game game, List<GameLangMap> gameLangMaps, String lang) {
		String merchantDefaultLangCode = MerchantRequestUtil.findMerchantDefaultLang(game.getMerchantId());
		List<String> langCodes = MerchantRequestUtil.findMerchantLangCodes(game.getMerchantId());
		
		GameLangMap gameDefaultLangMap = null;
		for(GameLangMap gameLangMap : gameLangMaps){
			if(StringUtils.isBlank(gameLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(gameLangMap.getImg())){
				throw new IllegalArgumentException("Image " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(gameLangMap.getName())){
				throw new IllegalArgumentException("Name " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(!langCodes.contains(gameLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			gameLangMap.setCreatedBy(game.getCreatedBy());
			gameLangMap.setCreatedTime(game.getCreatedTime());
			gameLangMap.setGameId(game.getId());
			gameLangMapDao.add(gameLangMap);
			
			if(gameLangMap.getLangCode().equalsIgnoreCase(merchantDefaultLangCode)){
				gameDefaultLangMap = gameLangMap;
			}
			langCodes.remove(gameLangMap.getLangCode());
		}
		
		if(null == gameDefaultLangMap){
			throw new IllegalArgumentException("Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		for(String langCode : langCodes){
			gameDefaultLangMap.setLangCode(langCode);
			gameDefaultLangMap.setId(null);
			
			gameLangMapDao.add(gameDefaultLangMap);
		}
	}

	@Override
	@Transactional
	public Game updateGame(final Long userId, final Long gameId, final String webUrl, final Integer status,final Integer inappOpen,final Date startTime,final Date endTime, final String langData, final String lang) {
		Game game = gameDao.get(gameId);
		if(null == game){
			throw new GameNotExistException(I18nUtil.getMessage(CmsCodeStatus.GAME_NOT_EXIST, null, lang));
		}
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, game.getMerchantId(), lang);
		
		final Merchant merchant = MerchantRequestUtil.findById(game.getMerchantId());
		
		game.setWebUrl(webUrl);
		
		if(inappOpen.intValue() == Banner.BannerInappOpenStatus.IN_APP.toValue() || inappOpen.intValue() == Banner.BannerInappOpenStatus.NATIVE_BROWSER.toValue()){
			game.setInappOpen(inappOpen);
		}
		if(null != status){
			if(status.intValue() == Game.Status.IN_ACTIVE.getCode() || status.intValue() == Game.Status.ACTIVE.getCode()){
				if(status.intValue() == Game.Status.IN_ACTIVE.getCode()){
					long count = homePageService.findCountByReftId(game.getId(),HomePage.HomePageType.GAME.toValue());
					if(count > 0 ){
						throw new UpdateRecordStatusException();
					}
				}
				game.setStatus(status);
			}
		}
		if(null != startTime){
			if(null == endTime){
				if(TimeZoneConvert.dateTimezoneToDB(startTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE).getTime() > game.getEndTime().getTime()){
					throw new IllegalArgumentException("Start time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
			}
			game.setStartTime(TimeZoneConvert.dateTimezoneToDB(startTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		}
		if(null != endTime){
			if(null == startTime){
				if(TimeZoneConvert.dateTimezoneToDB(endTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE).getTime() < game.getStartTime().getTime()){
					throw new IllegalArgumentException("End time "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
			}
			game.setEndTime(TimeZoneConvert.dateTimezoneToDB(endTime, merchant.getTimeZone(), TimeZoneConvert.DEFAULT_TIMEZONE));
		}
		
		game.setUpdatedBy(String.valueOf(userId));
		
		game = gameDao.updateForVersion(game);
		
		if(StringUtils.isNotBlank(langData)){
			List<GameLangMap> gameLangMaps = JsonUtil.toListObject(langData, GameLangMap.class);
			
			if(null == gameLangMaps){
				throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION,null, lang));
			}
			
			updateGameLangMap(game,gameLangMaps,lang);
		}
		
		homePageService.updateMerchantHomePageCacheByRefId(userId, game.getMerchantId(), game.getId(), HomePage.HomePageType.GAME.toValue(), lang);
		return game;
	}

	private void updateGameLangMap(Game game, List<GameLangMap> gameLangMaps, String lang) {
		for(GameLangMap gameLangMap : gameLangMaps){
			if(null == gameLangMap.getId()){
				throw new IllegalArgumentException("Game lang map id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			GameLangMap updGameLangMap = gameLangMapDao.get(gameLangMap.getId());
			if(null == updGameLangMap){
				throw new GameNotExistException(I18nUtil.getMessage(CmsCodeStatus.GAME_NOT_EXIST, null, lang));
			}
			
			if(updGameLangMap.getGameId().intValue() != game.getId().intValue()){
				throw new ForbiddenException(I18nUtil.getMessage(CmsCodeStatus.FORBIDDEN, null, lang));
			}
			
			if(null != gameLangMap.getImg()){
				updGameLangMap.setImg(gameLangMap.getImg());
			}
			
			if(StringUtils.isNotBlank(gameLangMap.getName())) {
				updGameLangMap.setName(gameLangMap.getName());
			}
			
			updGameLangMap.setUpdatedBy(game.getUpdatedBy());
			
			gameLangMapDao.updateForVersion(updGameLangMap);
			
		}
	}

	@Override
	@Transactional
	public void delByIds(Long userId, Long userMerchantId, String ids, String lang) {
		String[] idArr = ids.split(",");
		List<Object> idList = new ArrayList<Object>();
		for(String idStr : idArr){
			idList.add(Long.parseLong(idStr));
		}
		
		List<Game> games = gameDao.findByIds(idList);
		for(Game game : games){
			if(null != userMerchantId && userMerchantId.intValue() > 0 ){
				if(game.getMerchantId().intValue() != userMerchantId.intValue()){
					throw new ForbiddenException(I18nUtil.getMessage(CmsCodeStatus.FORBIDDEN, null, lang));
				}
			}
			long count = homePageService.findCountByReftId(game.getId(),HomePage.HomePageType.GAME.toValue());
			if(count > 0 ){
				throw new RecordIsReferencedException();
			}
		}
		
		gameLangMapDao.delByGameIds(idList);
		
		gameDao.delByIds(idList);
	}

	@Override
	public List<HomePageItemDTO> findHomePageGameList(final Long userId, final Long merchantId, final Integer status, final String lang) {
		
		AuthorizeRequestUtil.checkUserMerchantPermission(userId, merchantId, lang);
		
		List<HomePageItemDTO> homePageGames = gameDao.findHomePageGameList(merchantId,status);
		
		if(null != homePageGames && homePageGames.size() > 0){
			
			MerchantConfig merchantConfig = MerchantConfigRequestUtil.findByMerchantId(merchantId);
			
			for(HomePageItemDTO homePageGame : homePageGames){
				homePageGame.setImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), homePageGame.getImg()));
				if(homePageGame.getStatus().intValue() == Game.Status.ACTIVE.getCode()){
					//update status
					Long currentTime = DateUtil.getCurrentDate().getTime();
					if(homePageGame.getStartTime().getTime() > currentTime){
						homePageGame.setStatus(Game.Status.PENDING.getCode());
					}else if(homePageGame.getEndTime().getTime() < currentTime){
						homePageGame.setStatus(Game.Status.EXPIRED.getCode());
					}
				}
			}
		}
		
		return homePageGames;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<GameItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		List<GameItemDTO> games = (List<GameItemDTO>) super.findByFilter(criteriaMap, sortField, sortType, args);
		
		if(null != games && games.size() > 0 ){
			for(GameItemDTO game : games){
				if(game.getStatus().intValue() == Game.Status.ACTIVE.getCode()){
					long nowTime = DateUtil.getCurrentDate().getTime();
					if(game.getStartTime().getTime() > nowTime){
						game.setStatus(Game.Status.PENDING.getCode());
					}else if(game.getEndTime().getTime() < nowTime){
						game.setStatus(Game.Status.EXPIRED.getCode());
					}
				}
			}
		}
		
		return games;
	}
}
