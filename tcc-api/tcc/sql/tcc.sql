DROP DATABASE IF EXISTS tcc;
CREATE DATABASE tcc DEFAULT CHARACTER SET UTF8;
USE tcc;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `AUDIT_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `AUDIT_LOG`;
CREATE TABLE `AUDIT_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  `IP` varchar(255) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `ACCESS_TOKEN` varchar(255) DEFAULT NULL,
  `USER_AGENT` text,
  `URL` varchar(255) DEFAULT NULL,
  `METHOD` varchar(45) DEFAULT NULL,
  `PARAMS` longtext CHARACTER SET utf8mb4,
  `STATUS` tinyint(1) DEFAULT NULL,
  `RESPONSE` longtext CHARACTER SET utf8mb4,
  `REQ_ID` varchar(45) DEFAULT NULL,
  `REQUEST_TIMESTAMP` varchar(20) DEFAULT NULL,
  `RESPONSE_TIMESTAMP` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `LANGUAGE`
-- ----------------------------
DROP TABLE IF EXISTS `LANGUAGE`;
CREATE TABLE `LANGUAGE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LANG_CODE` varchar(20) NOT NULL,
  `DESCR` varchar(200) DEFAULT NULL,
  `IMAGE` varchar(100) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `SORT_ORDER` int(11) NOT NULL DEFAULT '0',
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `CURRENCY_UNIT`
-- ----------------------------
DROP TABLE IF EXISTS `CURRENCY_UNIT`;
CREATE TABLE `CURRENCY_UNIT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COUNTRY` varchar(100) NOT NULL,
  `CURRENCY_CODE` varchar(20) NOT NULL,
  `CURRENCY_FONT_CODE` varchar(20) NOT NULL,
  `DESCR` varchar(200) DEFAULT NULL,
  `IMAGE` varchar(100) DEFAULT NULL,
  `SORT_ORDER` int(11) DEFAULT '0',
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `POS_TRANS_STAMP`
-- ----------------------------
DROP TABLE IF EXISTS `POS_TRANS_STAMP`;
CREATE TABLE `POS_TRANS_STAMP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CODE` varchar(50) NOT NULL,
  `EXTERNAL_STORE_ID` varchar(50) NOT NULL,
  `TRANS_DATE_TIME` datetime NOT NULL,
  `TRANS_NO` varchar(100) NOT NULL,
  `TRANS_AMT` decimal(15,2) NOT NULL DEFAULT 0.00,
  `CURRENCY` varchar(20) NOT NULL,
  `COLLECT_CODE` varchar(100) NOT NULL,
  `STAMPS` int(11) NOT NULL,
  `ISSUE_TYPE` varchar(50) NOT NULL COMMENT '0- Money to stamps 1-Paper to Stamps 2-Stamps directly',
  `ITEM_CODE_QTY` text DEFAULT NULL,
  `COUPONS` varchar(500) DEFAULT NULL,
  `SIGN` varchar(100) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `POS_REDEEM_GIFT`
-- ----------------------------
DROP TABLE IF EXISTS `POS_REDEEM_GIFT`;
CREATE TABLE `POS_REDEEM_GIFT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CODE` varchar(50) NOT NULL,
  `EXTERNAL_STORE_ID` varchar(50) NOT NULL,
  `TRANS_DATE_TIME` datetime NOT NULL,
  `TRANS_NO` varchar(100) NOT NULL,
  `TRANS_AMT` decimal(15,2) DEFAULT 0.00,
  `CURRENCY` varchar(20) DEFAULT NULL,
  `REDEEM_CODE` varchar(100) NOT NULL,
  `ITEM_CODE_QTY` text DEFAULT NULL,
  `SIGN` varchar(100) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `POS_RESERVATION_PICK_UP`
-- ----------------------------
DROP TABLE IF EXISTS `POS_RESERVATION_PICK_UP`;
CREATE TABLE `POS_RESERVATION_PICK_UP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CODE` varchar(50) NOT NULL,
  `EXTERNAL_STORE_ID` varchar(50) NOT NULL,
  `UPDATE_DATE_TIME` datetime NOT NULL,
  `RESERVATION_CODE` varchar(100) NOT NULL,
  `ITEM_CODE_QTY` text DEFAULT NULL,
  `SIGN` varchar(100) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `POS_GIFT_OUT_OF_STOCK`
-- ----------------------------
DROP TABLE IF EXISTS `POS_GIFT_OUT_OF_STOCK`;
CREATE TABLE `POS_GIFT_OUT_OF_STOCK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CODE` varchar(50) NOT NULL,
  `STATUS_DATE_TIME` datetime NOT NULL,
  `EXTERNAL_GIFT_ID` varchar(50) NOT NULL,
  `SIGN` varchar(100) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `POS_GIFT_LOW_STOCK`
-- ----------------------------
DROP TABLE IF EXISTS `POS_GIFT_LOW_STOCK`;
CREATE TABLE `POS_GIFT_LOW_STOCK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_CODE` varchar(50) NOT NULL,
  `STATUS_DATE_TIME` datetime NOT NULL,
  `EXTERNAL_GIFT_ID` varchar(50) NOT NULL,
  `SIGN` varchar(100) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for MERCHANT
-- ----------------------------
DROP TABLE IF EXISTS `MERCHANT`;
CREATE TABLE `MERCHANT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CURRENCY_UNIT_ID` bigint(20) NOT NULL,
  `MERCHANT_CODE` varchar(100) NOT NULL COMMENT 'Merchant唯一code',
  `SECURITY_KEY` varchar(100) NOT NULL COMMENT 'Merchant唯一的key',
  `LOGIN_METHOD` varchar(30) NOT NULL COMMENT '用户Login的方式:1 - Anonymous  2 - Email  3 - Facebook',
  `ISSUE_STAMP_METHOD` tinyint(1) NOT NULL COMMENT '用户收集印花的方式:1 - POS integration  2 - Merchant app',
  `STATUS` tinyint(1) NOT NULL COMMENT '状态:0 - InActive  1 - Active',
  `IS_COUPON_MANAGEMENT` tinyint(1) NOT NULL,
  `HOME_PAGE` varchar(500) DEFAULT NULL,
  `HOME_PAGE_DRAFT` varchar(500) DEFAULT NULL,
  `TIME_ZONE` varchar(100) NOT NULL,
  `DATE_FORMAT` varchar(20) NOT NULL,
  `HOURS_FORMAT` varchar(20) NOT NULL,
  `PHONE_DISTRICT_CODE` varchar(20) DEFAULT NULL,
  `RESERVATION_TYPE` tinyint(1) NOT NULL,
  
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`),
  KEY `MERCHANT_CODE` (`MERCHANT_CODE`),
  FOREIGN KEY(CURRENCY_UNIT_ID) REFERENCES CURRENCY_UNIT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for MERCHANT_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `MERCHANT_LANG_MAP`;
CREATE TABLE `MERCHANT_LANG_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` bigint(20) NOT NULL COMMENT 'MERCHANT表外键',
  `NAME` varchar(100) NOT NULL COMMENT '标题',
  `IMG` varchar(100) DEFAULT NULL COMMENT '图片',
  `LOGO` varchar(100) DEFAULT NULL COMMENT '标志',
  `DESCR` varchar(500) COMMENT '描述',
  `LANG_CODE` varchar(20) NOT NULL COMMENT '语言编码',
  `IS_DEFAULT` tinyint(1) NOT NULL COMMENT '默认语言',
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for merchant_area
-- ----------------------------
DROP TABLE IF EXISTS `MERCHANT_AREA`;
CREATE TABLE `MERCHANT_AREA` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` bigint(20) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for merchant_area_lang_map
-- ----------------------------
DROP TABLE IF EXISTS `MERCHANT_AREA_LANG_MAP`;
CREATE TABLE `MERCHANT_AREA_LANG_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_AREA_ID` bigint(20) NOT NULL,
  `AREA_INFO` varchar(300) NOT NULL,
  `DESCR` varchar(500) DEFAULT NULL,
  `LANG_CODE` varchar(20) NOT NULL COMMENT '语言编码',
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(MERCHANT_AREA_ID) REFERENCES MERCHANT_AREA(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for BANNER
-- ----------------------------
DROP TABLE IF EXISTS `BANNER`;
CREATE TABLE `BANNER` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`MERCHANT_ID` bigint(20) NOT NULL,
	`SORT_ORDER` int(11) NOT NULL DEFAULT 0,
	`WEB_URL` varchar(100) DEFAULT NULL,
	`START_TIME` datetime NOT NULL,
  	`END_TIME` datetime NOT NULL,
	`STATUS` tinyint(1) NOT NULL COMMENT '状态',
	`INAPP_OPEN` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 -false\n 1 - true',
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for BANNER_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `BANNER_LANG_MAP`;
CREATE TABLE `BANNER_LANG_MAP` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`BANNER_ID` bigint(20) NOT NULL,
	`NAME` varchar(100) NOT NULL,
	`IMG` varchar(100) DEFAULT NULL,
	`LANG_CODE`  varchar(20) NOT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(BANNER_ID) REFERENCES BANNER(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for CAMPAIGN
-- ----------------------------
DROP TABLE IF EXISTS `CAMPAIGN`;
CREATE TABLE `CAMPAIGN` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` bigint(20) NOT NULL,
  `START_TIME` datetime NOT NULL,
  `END_TIME` datetime NOT NULL,
  `COLL_START_TIME` datetime DEFAULT NULL COMMENT '开始收集印花的时间',
  `COLL_END_TIME` datetime DEFAULT NULL COMMENT '结束收集印花的时间',
  `REDEEM_START_TIME` datetime DEFAULT NULL COMMENT '开始兑换印花的时间',
  `REDEEM_END_TIME` datetime DEFAULT NULL COMMENT '结束兑换印花的时间',
  `STATUS` tinyint(1) NOT NULL COMMENT '状态:1 - Pending 2 - Active 3 - Expired 4 - Inactive',
  `STAMP_RATIO` int(9) NOT NULL COMMENT '兑换印花计算比率',
  `PRM_BANNER_URL` varchar(500) DEFAULT NULL,
  `INAPP_OPEN` tinyint(1) NOT NULL COMMENT '0-In-app 1-Native browser',
  `GRANT_STAMP_QTY` int(10) DEFAULT '0',
  `GRANT_TYPE` tinyint(1) DEFAULT '1',
  
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for CAMPAIGN_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `CAMPAIGN_LANG_MAP`;
CREATE TABLE `CAMPAIGN_LANG_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CAMPAIGN_ID` bigint(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `COVER_IMG` varchar(100) DEFAULT NULL,
  `BG_IMG` varchar(100) DEFAULT NULL COMMENT 'Background图片',
  `BG_COLOR` varchar(20) DEFAULT NULL COMMENT 'background颜色',
  `PRM_BANNER_IMG` varchar(100) DEFAULT NULL,
  `DESCR` varchar(500) DEFAULT NULL,
  `TERMS` varchar(3000) DEFAULT NULL COMMENT '条款说明',
  `LANG_CODE` varchar(20) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(CAMPAIGN_ID) REFERENCES CAMPAIGN(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for GAME
-- ----------------------------
DROP TABLE IF EXISTS `GAME`;
CREATE TABLE `GAME` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
    `WEB_URL` varchar(100) DEFAULT NULL,
    `START_TIME` datetime NOT NULL,
  	`END_TIME` datetime NOT NULL,
    `STATUS` tinyint(1) NOT NULL COMMENT '状态',
    `INAPP_OPEN` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 -false\n 1 - true',
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GAME_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `GAME_LANG_MAP`;
CREATE TABLE `GAME_LANG_MAP` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `GAME_ID` bigint(20) NOT NULL,
    `IMG` varchar(100) DEFAULT NULL,
    `LANG_CODE`  varchar(20) NOT NULL,
    `NAME` varchar(100) NOT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(GAME_ID) REFERENCES GAME(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for COUPON
-- ----------------------------
DROP TABLE IF EXISTS `COUPON`;
CREATE TABLE `COUPON` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `CAMPAIGN_ID` bigint(20) NOT NULL,
	`START_TIME` datetime DEFAULT NULL,
	`END_TIME` datetime DEFAULT NULL,
	`STATUS` tinyint(1) NOT NULL COMMENT '0-Inactive 1-Active 2-Terminated 3-Out of stock 4-Expired',
	`STAMPS` int(11) NOT NULL DEFAULT 0,
	`SORT_ORDER` int(11) NOT NULL DEFAULT '0',
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(CAMPAIGN_ID) REFERENCES CAMPAIGN(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for COUPON_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `COUPON_LANG_MAP`;
CREATE TABLE `COUPON_LANG_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COUPON_ID` bigint(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `TERMS` varchar(500) DEFAULT NULL,
  `IMG` varchar(100) DEFAULT NULL,
  `DESCR` varchar(500) DEFAULT NULL,
  `LANG_CODE` varchar(20) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(COUPON_ID) REFERENCES COUPON(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GIFT
-- ----------------------------
DROP TABLE IF EXISTS `GIFT`;
CREATE TABLE `GIFT` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`MERCHANT_ID` bigint(20) NOT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GIFT_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `GIFT_LANG_MAP`;
CREATE TABLE `GIFT_LANG_MAP` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`GIFT_ID` bigint(20) NOT NULL,
	`NAME` varchar(100) NOT NULL,
	`IMAGE` varchar(100) DEFAULT NULL,
	`DESCR` varchar(500) DEFAULT NULL,
	`LANG_CODE` varchar(20) NOT NULL,
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(GIFT_ID) REFERENCES GIFT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for CAMPAIGN_GIFT_MAP
-- ----------------------------
DROP TABLE IF EXISTS `CAMPAIGN_GIFT_MAP`;
CREATE TABLE `CAMPAIGN_GIFT_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CAMPAIGN_ID` bigint(20) NOT NULL,
  `GIFT_ID` bigint(20) NOT NULL,
  `EXTERNAL_GIFT_ID` varchar(100) NOT NULL,
  `STATUS` tinyint(1) NOT NULL COMMENT '1-coming soon 2-available 3-low stock 4-out of stock 5-expired 6-inactive',
  `IS_RESERVATION` tinyint(1) DEFAULT 0 COMMENT '0 - OFF 1 - ON',
  
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(CAMPAIGN_ID) REFERENCES CAMPAIGN(ID),
  FOREIGN KEY(GIFT_ID) REFERENCES GIFT(ID),
  INDEX `CGM_CI_S` (`CAMPAIGN_ID`,`STATUS`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for CAMPAIGN_GIFT_EXCHANGE_TYPE
-- ----------------------------
DROP TABLE IF EXISTS `CAMPAIGN_GIFT_EXCHANGE_TYPE`;
CREATE TABLE `CAMPAIGN_GIFT_EXCHANGE_TYPE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CAMPAIGN_GIFT_MAP_ID` bigint(20) NOT NULL,
  `TYPE` tinyint(1) NOT NULL COMMENT '1-Stamps 2-Stamps + Cash',
  `STAMP_QTY` bigint(15) NOT NULL,
  `CASH_QTY` decimal(15,2) DEFAULT 0.00,
  `STATUS` tinyint(1) NOT NULL DEFAULT 1 COMMENT '0-InActive 1-Active',
  `EXTERNAL_RULE_CODE` varchar(20) NOT NULL,
 
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(CAMPAIGN_GIFT_MAP_ID) REFERENCES CAMPAIGN_GIFT_MAP(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for STAMP_CARD
-- ----------------------------
DROP TABLE IF EXISTS `STAMP_CARD`;
CREATE TABLE `STAMP_CARD` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`CAMPAIGN_ID` bigint(20) NOT NULL,
	`TYPE` tinyint(1) NOT NULL COMMENT '1-Merchant 2-Shopping',
	`STATUS` tinyint(1) NOT NULL COMMENT '1-Pending 2-Active 3-Expired 4-Inactive',
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(CAMPAIGN_ID) REFERENCES CAMPAIGN(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for 
-- ----------------------------
DROP TABLE IF EXISTS `STAMP_CARD_LANG_MAP`;
CREATE TABLE `STAMP_CARD_LANG_MAP` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`STAMP_CARD_ID` bigint(20) NOT NULL,
	`NAME` varchar(100) NOT NULL,
	`COVER_IMG` varchar(100) DEFAULT NULL,
	`DESCR` varchar(500) DEFAULT NULL,
	`TERMS` varchar(3000) DEFAULT NULL,
	`LANG_CODE` varchar(20) NOT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(STAMP_CARD_ID) REFERENCES STAMP_CARD(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for STAMP
-- ----------------------------
DROP TABLE IF EXISTS `STAMP`;
CREATE TABLE `STAMP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STAMP_CARD_ID` bigint(20) NOT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(STAMP_CARD_ID) REFERENCES STAMP_CARD(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for STAMP_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `STAMP_LANG_MAP`;
CREATE TABLE `STAMP_LANG_MAP` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
      `STAMP_ID` bigint(20) NOT NULL,
      `STAMP_IMG` varchar(100) DEFAULT NULL,
      `LANG_CODE` varchar(20) NOT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(STAMP_ID) REFERENCES STAMP(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for STORE
-- ----------------------------
DROP TABLE IF EXISTS `STORE`;
CREATE TABLE `STORE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EXTERNAL_STORE_ID` varchar(100) NOT NULL,
  `MERCHANT_ID` bigint(20) NOT NULL,
  `MERCHANT_AREA_ID` bigint(20) NOT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `STATUS` tinyint(1) DEFAULT 0 COMMENT '0 - InActive 1 - Active',
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `MERCHANT_ID` (`MERCHANT_ID`),
  KEY `MERCHANT_AREA_ID` (`MERCHANT_AREA_ID`),
  CONSTRAINT `STORE_ibfk_1` FOREIGN KEY (`MERCHANT_ID`) REFERENCES `MERCHANT` (`ID`),
  CONSTRAINT `STORE_ibfk_2` FOREIGN KEY (`MERCHANT_AREA_ID`) REFERENCES `MERCHANT_AREA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for STORE_LANG_MAP
-- ----------------------------
DROP TABLE IF EXISTS `STORE_LANG_MAP`;
CREATE TABLE `STORE_LANG_MAP` (
   `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`STORE_ID` bigint(20) NOT NULL,
	`IMAGE` varchar(100) DEFAULT NULL,
	`NAME` varchar(100) NOT NULL,
	`ADDRESS` varchar(500) DEFAULT NULL,
	`BUSINESS_INFO` varchar(500) DEFAULT NULL,
	`LANG_CODE` varchar(20) NOT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(STORE_ID) REFERENCES STORE(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`MERCHANT_ID` bigint(20) NOT NULL,
	`USER_NAME` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
	`FIRST_NAME` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
	`LAST_NAME` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
	`BIRTHDAY` date DEFAULT NULL,
	`PASSWORD` varchar(100) DEFAULT NULL,
	`ICON` varchar(100) DEFAULT NULL,
	`SESSION` varchar(100) NOT NULL,
	`USER_TYPE` tinyint(1) NOT NULL COMMENT '0-Temporary user 1-Email user 2-Phone user 3-SNS user',
	`CONTACT_EMAIL` varchar(100) DEFAULT NULL,
	`EMAIL` varchar(100) DEFAULT NULL,
	-- `EMAIL_VALIDATION_CODE` varchar(100) NULL,
	-- `VALIDATION_CODE_EXPRIED_TIME` datetime DEFAULT NULL,
	-- `RESET_PWD_EXPRIED_TIME` datetime DEFAULT NULL,
	`IS_EMAIL_VALIDATION` tinyint(1) NULL DEFAULT 0 COMMENT '0-false 1-true',
	`PHONE_AREA_CODE` varchar(20) DEFAULT NULL,
	`PHONE` varchar(20) DEFAULT NULL,
	-- `PHONE_VALIDATION_CODE` varchar(10) DEFAULT NULL,
	`IS_PHONE_VALIDATION` tinyint(1) NULL DEFAULT 0 COMMENT '0-false 1-true',
	`GENDER` varchar(10) DEFAULT NULL,
	`LANG_CODE` varchar(20) NOT NULL COMMENT '语言编码',
	`IS_GRANT` tinyint(1) NOT NULL DEFAULT 0,
	`IS_MARKETING_INFO` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0-false(default),1-true',
	
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
  	KEY `USER_NAME` (`USER_NAME`),
  	KEY `EMAIL` (`EMAIL`),
  	KEY `USER_TYPE` (`USER_TYPE`),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER_COUPON
-- ----------------------------
DROP TABLE IF EXISTS `USER_COUPON`;
CREATE TABLE `USER_COUPON` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
	`USER_ID` bigint(20) NOT NULL,
	`COUPON_ID` bigint(20) NOT NULL,
	`STATUS` tinyint(1) NOT NULL COMMENT '1- Active 2- Redeemed',
	`QR_CODE` varchar(100)NOT NULL,
	`REDEEMED_DATE` datetime DEFAULT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID),
    FOREIGN KEY(COUPON_ID) REFERENCES COUPON(ID),
    UNIQUE KEY(QR_CODE,MERCHANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER_FRIEND
-- ----------------------------
DROP TABLE IF EXISTS `USER_FRIEND`;
CREATE TABLE `USER_FRIEND` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
    `USER_ID` bigint(20) NOT NULL,
    `FRIEND_USER_ID` bigint(20) NOT NULL,
    `STATUS` tinyint(1) NOT NULL COMMENT '"0 - Invited 1 - Accept"',
    `READ_STATUS` tinyint(1) NOT NULL COMMENT '"0 -  unread 1 - Read"',
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID),
    FOREIGN KEY(FRIEND_USER_ID) REFERENCES USER(ID),
    INDEX `UF_IDX_UI_MI_FUI` (`USER_ID`,`MERCHANT_ID`,`FRIEND_USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER_SNS
-- ----------------------------
DROP TABLE IF EXISTS `USER_SNS`;
CREATE TABLE `USER_SNS` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`USER_ID` bigint(20) NOT NULL,
	`SNS_ID` varchar(50) NOT NULL,
	`SNS_TYPE` tinyint(1) NOT NULL COMMENT '1-Facebook',
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER_STAMP_CARD
-- ----------------------------
DROP TABLE IF EXISTS `USER_STAMP_CARD`;
CREATE TABLE `USER_STAMP_CARD` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
    `CAMPAIGN_ID` bigint(20) NOT NULL,
    `USER_ID` bigint(20) NOT NULL,
    `STAMP_CARD_ID` bigint(20) NOT NULL,
    `COLLECT_STAMP_QTY` bigint(15) NOT NULL DEFAULT 0,
    `COLLECT_CODE` varchar(100) NOT NULL,
    `SCAN_STATUS` tinyint(1) DEFAULT '0' COMMENT '0-fail 1-sucess',
    `IS_SCAN` tinyint(1) DEFAULT '0' COMMENT '0-false 1-true',
    `MSG`  varchar(100) DEFAULT NULL,
    `GRANT_STAMP_QTY` bigint(15) NOT NULL DEFAULT 0,
    `IS_POP_GRANT` tinyint(1) NOT NULL DEFAULT 0,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID),
    FOREIGN KEY(STAMP_CARD_ID) REFERENCES STAMP_CARD(ID),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID),
    FOREIGN KEY(CAMPAIGN_ID) REFERENCES CAMPAIGN(ID),
    UNIQUE KEY(COLLECT_CODE,MERCHANT_ID),
    UNIQUE KEY(USER_ID,STAMP_CARD_ID,MERCHANT_ID,CAMPAIGN_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for USER_GIFT_REDEEM_CODE
-- ----------------------------
DROP TABLE IF EXISTS `USER_GIFT_REDEEM_CODE`;
CREATE TABLE `USER_GIFT_REDEEM_CODE` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `USER_ID` bigint(20) NOT NULL,
    `MERCHANT_ID` bigint(20) NOT NULL,
    `USER_STAMP_CARD_ID` bigint(20) NOT NULL,
    `CAMPAIGN_GIFT_MAP_ID` bigint(20) NOT NULL,
    `CAMPAIGN_GIFT_EXCHANGE_TYPE_ID` bigint(20) NOT NULL,
    `EXPIRY_TIME` datetime NOT NULL,
    `IS_REDEEM` tinyint(1) DEFAULT '0' COMMENT '0-false 1-true',
    `SCAN_STATUS` tinyint(1) DEFAULT '0' COMMENT '0-fail 1-sucess',
    `IS_SCAN` tinyint(1) DEFAULT '0' COMMENT '0-false 1-true',
    `MSG`  varchar(100) DEFAULT NULL,
    `REDEEM_CODE` varchar(100) NOT NULL,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID),
    FOREIGN KEY(CAMPAIGN_GIFT_MAP_ID) REFERENCES CAMPAIGN_GIFT_MAP(ID),
    FOREIGN KEY(USER_STAMP_CARD_ID) REFERENCES USER_STAMP_CARD(ID),
    FOREIGN KEY(CAMPAIGN_GIFT_EXCHANGE_TYPE_ID) REFERENCES CAMPAIGN_GIFT_EXCHANGE_TYPE(ID),
    UNIQUE KEY(REDEEM_CODE,MERCHANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER_STAMP
-- ----------------------------
DROP TABLE IF EXISTS `USER_STAMP`;
CREATE TABLE `USER_STAMP` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
	`USER_ID` bigint(20) NOT NULL,
	`USER_STAMP_CARD_ID` bigint(20) NOT NULL,
	`STAMP_ID` bigint(20) NOT NULL,
	`STATUS` tinyint(1) NOT NULL COMMENT '1- Active 2- Redeemed',
	`REDEEMED_DATE` datetime DEFAULT NULL ,
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID),
    FOREIGN KEY(USER_STAMP_CARD_ID) REFERENCES USER_STAMP_CARD(ID),
    FOREIGN KEY(STAMP_ID) REFERENCES STAMP(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER_STAMP_HISTORY
-- ----------------------------
DROP TABLE IF EXISTS `USER_STAMP_HISTORY`;
CREATE TABLE `USER_STAMP_HISTORY` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
    `USER_ID` bigint(20) NOT NULL,
	`USER_STAMP_CARD_ID` bigint(20) NOT NULL,
	`TYPE`  tinyint(1) NOT NULL COMMENT '1-Collect stamps 2-Transfer out stamps 3-Transfer in stamps 4-redeem 5-Reservation 6-System in stamps 7-System out stamps 8-Grant',
	`STAMP_QTY` bigint(15) NOT NULL,
	`TRANS_USER_ID` bigint(20) DEFAULT NULL,
	`CAMPAIGN_GIFT_MAP_ID` bigint(20) DEFAULT NULL,
	`EXCHANGE_TYPE_ID` bigint(20) DEFAULT NULL,
	`EXCHANGE_TYPE`  tinyint(1) DEFAULT NULL COMMENT '1-Stamps 2-Stamps + Cash',
    `CASH_QTY` decimal(15,2) DEFAULT 0.00,
    `TRANS_DATE_TIME` datetime DEFAULT NULL,
    `TRANS_NO` varchar(100) DEFAULT NULL,
    `EXTERNAL_STORE_ID` varchar(50) DEFAULT NULL,
    `READ_STATUS` tinyint(1) DEFAULT 0 COMMENT '0 -  unread 1 - Read',
    `REDEEM_CODE` varchar(100) DEFAULT NULL,
    `REDEEM_READ_STATUS` tinyint(1) DEFAULT NULL COMMENT '0 -  unread 1 - Read',
    `REMARKS` varchar(500) DEFAULT NULL,
 
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID),
    FOREIGN KEY(USER_STAMP_CARD_ID) REFERENCES USER_STAMP_CARD(ID),
    KEY(MERCHANT_ID,`TYPE`,REDEEM_CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for USER_RESERVATION
-- ----------------------------
DROP TABLE IF EXISTS `USER_RESERVATION`;
CREATE TABLE `USER_RESERVATION` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
    `USER_ID` bigint(20) NOT NULL,
    `USER_STAMP_CARD_ID` bigint(20) NOT NULL,
    `STATUS`  tinyint(1) NOT NULL COMMENT '1-confirmed 2-available at store 3-Settled 4-Expired 5-In_completed',
    `TYPE` tinyint(1) NOT NULL COMMENT '"1 - Stamps 2 - Stamps + Cash"',
    `READ_STATUS` tinyint(1) NOT NULL COMMENT '"0 -  unread 1 - Read"',
    `SCAN_STATUS` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-fail 1-sucess',
    `IS_SCAN` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-false 1-true',
    `MSG`  varchar(100) DEFAULT NULL,
    `EXCHANGE_TYPE_ID` bigint(20) NOT NULL,
    `STAMP_QTY` bigint(15) NOT NULL,
    `CASH_QTY` decimal(15,2) NOT NULL DEFAULT 0.00,
    `CAMPAIGN_GIFT_MAP_ID` bigint(20) NOT NULL,
    `STORE_ID` bigint(20) NOT NULL,
    `RESERVATION_CODE` varchar(100) NOT NULL,
    `RESERVATION_EXPIRED_TIME` datetime DEFAULT NULL,
    `FIRST_NAME` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
    `LAST_NAME` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
    `CONTACT_PHONE_AREA_CODE` varchar(20) DEFAULT NULL,
    `CONTACT_PHONE` varchar(100) DEFAULT NULL,
   
    
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(USER_ID) REFERENCES USER(ID),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID),
    FOREIGN KEY(USER_STAMP_CARD_ID) REFERENCES USER_STAMP_CARD(ID),
    FOREIGN KEY(CAMPAIGN_GIFT_MAP_ID) REFERENCES CAMPAIGN_GIFT_MAP(ID),
    FOREIGN KEY(STORE_ID) REFERENCES STORE(ID),
    UNIQUE KEY(RESERVATION_CODE,MERCHANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for HOME_PAGE
-- ----------------------------
DROP TABLE IF EXISTS `HOME_PAGE`;
CREATE TABLE `HOME_PAGE` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT,
    `MERCHANT_ID` bigint(20) NOT NULL,
    `REF_ID` bigint(20) NOT NULL,
    `TYPE`  tinyint(1) NOT NULL COMMENT '1-Coupon 2-Banner 3-Game',
    `STATUS` tinyint(1) NOT NULL DEFAULT '0' COMMENT '编辑状态：1 - on line 0-draft',
    `SORT_ORDER` int(11) NOT NULL,
   
    `CREATED_BY` varchar(100) DEFAULT NULL,
    `CREATED_TIME` datetime DEFAULT NULL,
    `UPDATED_BY` varchar(100) DEFAULT NULL,
    `UPDATED_TIME` datetime DEFAULT NULL,
    `IS_DELETED` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`ID`),
    FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for system_function
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_FUNCTION`;
CREATE TABLE `SYSTEM_FUNCTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FUNC_CODE` varchar(50) NOT NULL COMMENT '菜单code',
  `FUNC_NAME` varchar(100) NOT NULL COMMENT '菜单 名称',
  `FUNC_TYPE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '菜单类型:1-Menu  2-Button',
  `FUNC_DESC` varchar(100) DEFAULT NULL COMMENT '菜单描述',
  `IS_FOLDER` tinyint(1) NOT NULL COMMENT '是否为父菜单',
  `PARENT_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '父菜单的ID',
  `PAGE_INFO` varchar(500) DEFAULT NULL COMMENT '菜单所用到的页面',
  `DISPLAY_ORDER` bigint(20) DEFAULT NULL COMMENT '菜单显示顺序',
  `ICON` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  `WEB_INFO` varchar(100) DEFAULT NULL COMMENT '菜单所用到的WEB页面',
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_ROLE`;
CREATE TABLE `SYSTEM_ROLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_TYPE` tinyint(1) NOT NULL COMMENT '角色类型:1-Platform 2-Mall',
  `ROLE_NAME` varchar(50) NOT NULL COMMENT '角色名称',
  `ROLE_DESC` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for system_role_function_map
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_ROLE_FUNCTION_MAP`;
CREATE TABLE `SYSTEM_ROLE_FUNCTION_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(20) NOT NULL COMMENT 'SYSTEM_ROLE表主键',
  `FUNC_ID` bigint(20) NOT NULL COMMENT 'SYSTEM_FUN表主键',
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_USER`;
CREATE TABLE `SYSTEM_USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(20) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(255) NOT NULL COMMENT '密码',
  `MOBILE` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `EMAIL` varchar(50) NOT NULL COMMENT '联系电邮',
  `STATUS` tinyint(1) DEFAULT '1' COMMENT '用户状态：0-Inactive   1-Active',
  `SESSION` varchar(100) DEFAULT NULL,
  `SESSION_EXPIRE_TIME` datetime DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for system_user_merchant_map
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_USER_MERCHANT_MAP`;
CREATE TABLE `SYSTEM_USER_MERCHANT_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) NOT NULL COMMENT 'SYSTEM_USER表主键',
  `MERCHANT_ID` bigint(20) NOT NULL COMMENT 'MERCHANT表主键',
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for system_user_role_map
-- ----------------------------
DROP TABLE IF EXISTS `SYSTEM_USER_ROLE_MAP`;
CREATE TABLE `SYSTEM_USER_ROLE_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(20) NOT NULL COMMENT 'SYSTEM_ROLE表主健',
  `USER_ID` bigint(20) NOT NULL COMMENT 'SYSTEM_USER表主健',
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for push_notif
-- ----------------------------
DROP TABLE IF EXISTS `PUSH_NOTIF`;
CREATE TABLE `PUSH_NOTIF` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` bigint(20) NOT NULL COMMENT 'Merchant表的外键',
  `TYPE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '消息类型:1 - 到货提示消息   2 - 推送消息',
  `PUSH_TASK_ID` bigint(20) DEFAULT NULL,
  `PAGE_REDIRECT_TYPE` tinyint(1) DEFAULT NULL,
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `FREQUENCY` tinyint(1) DEFAULT NULL COMMENT '发送频率:1 - daily  2 - weekly  3 - monthly',
  `LANDING_URL` varchar(500) DEFAULT NULL,
  `STATUS` tinyint(1) DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for push_notif_lang_map
-- ----------------------------
DROP TABLE IF EXISTS `PUSH_NOTIF_LANG_MAP`;
CREATE TABLE `PUSH_NOTIF_LANG_MAP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PUSH_NOTIF_ID` bigint(20) NOT NULL COMMENT 'PUSH_NOTIF表外键',
  `MSG` varchar(200) DEFAULT '0' COMMENT '消息内容',
  `LANDING_IMG` varchar(100) DEFAULT NULL COMMENT '超链接图片',
  `LANDING_URL` varchar(500) DEFAULT NULL COMMENT '超链接URL',
  `LANG_CODE` varchar(20) NOT NULL COMMENT '语言编码',
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(PUSH_NOTIF_ID) REFERENCES PUSH_NOTIF(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `SHARE`;
CREATE TABLE `SHARE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` bigint(20) NOT NULL COMMENT 'MERCHANT表外键',
  `FB_APP_ID` varchar(100) DEFAULT NULL,
  `TYPE` varchar(50) DEFAULT NULL,
  `TITLE` varchar(500) DEFAULT NULL,
  `IMAGE` varchar(100) DEFAULT NULL,
  `DESCR` text CHARACTER SET utf8mb4 DEFAULT NULL ,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for MERCHANT_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `MERCHANT_CONFIG`;
CREATE TABLE `MERCHANT_CONFIG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MERCHANT_ID` bigint(20) NOT NULL,
  `IMG_DOMAIN` varchar(255) NOT NULL,
  `PUSH_PROJECT_ID` varchar(20) NOT NULL,
  `EMAIL_HOST` varchar(200) NOT NULL,
  `EMAIL_PORT` int(11) NOT NULL,
  `EMAIL_USER_NAME` varchar(100) DEFAULT NULL,
  `EMAIL_PASSWORD` varchar(100) DEFAULT NULL,
  `EMAIL_SMTP_AUTH` tinyint(1) NOT NULL,
  `EMAIL_SMTP_STS_ENABLE` tinyint(1) NOT NULL,
  `EMAIL_SMTP_STS_REQUIRED` tinyint(1) NOT NULL,
  `EMAIL_FROM_NICK` varchar(100) DEFAULT NULL,
  `EMAIL_FROM` varchar(100) NOT NULL,
  `SUPPORT_EMAIL_FROM` varchar(100) NOT NULL,
  `WEB_DOMAIN` varchar(200) NOT NULL,
  `API_DOMAIN` varchar(200) NOT NULL,
  `RESERVATION_PUSH_TEMPLATE` varchar(50) NOT NULL,
  `REGISTER_VERIFY_EMAIL_TEMPLATE` varchar(50) NOT NULL,
  `RESET_PASSWORD_EMAIL_TEMPLATE` varchar(50) NOT NULL,
  `IQUIRY_EMAIL_TEMPLATE` varchar(50) NOT NULL,
  `SHARE_TEMPLATE﻿` varchar(50) NOT NULL,
  `SHARE_DEFALUT_TITLE` varchar(200) NOT NULL,
  `SHARE_DEFALUT_DESCR` text NOT NULL,
  `SHARE_DEFALUT_IMAGE` varchar(100) NOT NULL,
  
  `CREATED_BY` varchar(100) DEFAULT NULL COMMENT '创建者',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(100) DEFAULT NULL COMMENT '修改者',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `IS_DELETED` tinyint(1) DEFAULT '0' COMMENT '业务删除，是否删除',
  PRIMARY KEY (`ID`),
  FOREIGN KEY(MERCHANT_ID) REFERENCES MERCHANT(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for SCHEDULE_JOB
-- ----------------------------
-- DROP TABLE IF EXISTS `SCHEDULE_JOB`;
-- CREATE TABLE `SCHEDULE_JOB` (
  -- `ID` bigint(20) NOT NULL,
  -- `UUID` varchar(50) DEFAULT NULL,
  -- `SCHEDULE_EXECUTE_DATE` datetime DEFAULT NULL,
  -- `CREATED_BY` varchar(100) DEFAULT NULL,
  -- `CREATED_TIME` datetime DEFAULT NULL,
  -- `UPDATED_BY` varchar(100) DEFAULT NULL,
  -- `UPDATED_TIME` datetime DEFAULT NULL,
  -- `IS_DELETED` tinyint(1) DEFAULT '0',
  -- PRIMARY KEY (`ID`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;