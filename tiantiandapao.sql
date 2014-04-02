/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : tiantiandapao

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-04-02 11:06:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `password` varchar(40) DEFAULT NULL COMMENT '用户密码',
  `id` varchar(100) DEFAULT NULL COMMENT '用户名',
  `ultime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户最近登录时间',
  `urtime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户注册日期',
  `utoken` varchar(64) DEFAULT NULL COMMENT '登录成功后使用此token与服务端通信',
  `uemail` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `uphone` varchar(20) DEFAULT '0' COMMENT '用户手机',
  `ucharge` int(11) unsigned DEFAULT '0' COMMENT '话费点',
  `ugender` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户性别：0=未定义 1=男性 2=女性',
  `ustatus` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户账号状态：0=正常 1=封停 2=GM账号',
  `uconsecutive` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '连续登录天数',
  `ugold` int(10) unsigned DEFAULT '0' COMMENT '游戏金币',
  `is_get_this_day` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否已经领取当日登录奖励(0未领取，1已领取)',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='与平台相关的静态用户信息';

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '1111', 'j1231231', '0', '1395644253', null, '78117253@qq.com', '13975800504', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('2', '1111', 's1231231', '0', '1395644333', null, '78117253@qq.com', '13975800504', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('3', '1111', 'z1231231', '1395819526', '1395644717', 'DA8A12573B0607A0545BDA326A48A9FF', '78117253@qq.com', '13975800504', '0', '0', '0', '1', '500', '1');
INSERT INTO `userinfo` VALUES ('4', 'xxxx', 'xxxxx', '1396407522', '1395813336', 'BA1F663EFCC99DC53899DB206E4FEB09', null, '123456', '0', '0', '0', '3', '500', '0');
INSERT INTO `userinfo` VALUES ('5', 'aaaa', 'aaaaa', '0', '1395819344', '28E4F0C2238BFD277EE9D4EC0B448574', null, '123124', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('6', 'bbbbb', 'bbbbb', '1396250121', '1395819438', 'D6BB1361F85624F5E92078AAB5C6B7B0', null, '123456', '0', '0', '0', '3', '2000', '0');
INSERT INTO `userinfo` VALUES ('7', 'xxxxx', 'xxxxxxxx', '0', '1395883666', '1E2E5609B3685A3006C02F5257AAB80B', null, 'xxxxxx', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('8', 'yyyy', 'yyyy', '0', '1395884764', '98EE515AC898B2D0C49C12A3010F1081', null, '123344', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('9', 'wwww', 'wwwww', '0', '1395886587', '6FE56D1AC4DF2593F896EE297422E439', null, '1243324', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('10', 'aaaa', 'asdfsdfsadf', '0', '1395886900', '29EC0A4FF1406706DC81F2C19D5C6840', null, 'asdfasdf', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('11', 'eeeee', 'asdfasfasdfa', '0', '1395887183', '64568625E4E2201253DAC5306C19B48E', null, 'asdfasdf', '0', '0', '0', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('12', 'ddddd', 'sdfasdfasdfa', '0', '1395887362', '61825C1F7C20D0D415B67949A6C7808F', null, 'sdfsdgsfd', '0', '0', '0', '1', '0', '0');

-- ----------------------------
-- Table structure for `userjjc`
-- ----------------------------
DROP TABLE IF EXISTS `userjjc`;
CREATE TABLE `userjjc` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户唯一id标识符',
  `rank` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户在竞技场内的排名',
  `day_max_times` tinyint(3) unsigned NOT NULL DEFAULT '15' COMMENT 'Challenging times 玩家每日最大挑战次数(目前最大次数15，使用钻石+10)',
  `score` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分(每次挑战胜利获得十点积分，失败得5积分。)',
  `diamond` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '钻石',
  `record` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当前成绩',
  `pk_win_count` int(10) unsigned NOT NULL COMMENT '挑战胜利次数',
  `pk_lost_count` int(10) unsigned NOT NULL COMMENT '挑战失败次数',
  `pk_draw_count` int(10) unsigned NOT NULL COMMENT '挑战平局次数',
  `day_cur_times` tinyint(3) unsigned NOT NULL COMMENT 'Challenging times 玩家每日当前挑战次数',
  `score_3day` int(10) unsigned NOT NULL COMMENT '每三日领取的积分',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='竞技场相关表';

-- ----------------------------
-- Records of userjjc
-- ----------------------------
INSERT INTO `userjjc` VALUES ('1', '2', '15', '0', '0', '200', '0', '0', '0', '0', '900');
INSERT INTO `userjjc` VALUES ('2', '4', '15', '0', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('3', '3', '15', '700', '0', '100', '3', '1', '0', '0', '700');
INSERT INTO `userjjc` VALUES ('4', '1', '15', '90', '0', '0', '8', '1', '1', '10', '1200');
INSERT INTO `userjjc` VALUES ('5', '5', '15', '0', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('6', '6', '15', '0', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('7', '7', '15', '0', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('8', '8', '15', '0', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('9', '9', '15', '0', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('10', '10', '15', '0', '0', '0', '0', '0', '0', '0', '500');
INSERT INTO `userjjc` VALUES ('11', '11', '15', '0', '0', '0', '0', '0', '0', '0', '500');
INSERT INTO `userjjc` VALUES ('12', '12', '15', '0', '0', '0', '0', '0', '0', '0', '500');

-- ----------------------------
-- Table structure for `usertask`
-- ----------------------------
DROP TABLE IF EXISTS `usertask`;
CREATE TABLE `usertask` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户uid',
  `taskid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '任务ID',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '任务完成状态',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertask
-- ----------------------------

-- ----------------------------
-- Procedure structure for `everyday_do_something`
-- ----------------------------
DROP PROCEDURE IF EXISTS `everyday_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `everyday_do_something`()
BEGIN
update userjjc SET day_cur_times = 0,day_max_times=15;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `login_reward`
-- ----------------------------
DROP PROCEDURE IF EXISTS `login_reward`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login_reward`(in_uid long)
BEGIN
update userinfo SET ugold = 500,is_get_this_day=1 where uconsecutive=1 and uid=in_uid;
update userinfo SET ugold = 1000,is_get_this_day=1 where uconsecutive=2  and uid=in_uid;
update userinfo SET ugold = 2000,is_get_this_day=1 where uconsecutive=3  and uid=in_uid;
update userinfo SET ugold = 4000,is_get_this_day=1 where uconsecutive=4  and uid=in_uid;
update userinfo SET ugold = 8000,is_get_this_day=1 where uconsecutive=5  and uid=in_uid;
update userinfo SET ugold = 16000,is_get_this_day=1 where uconsecutive=6  and uid=in_uid;
update userinfo SET ugold = 32000,is_get_this_day=1 where uconsecutive=7  and uid=in_uid;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `reset_score`
-- ----------------------------
DROP PROCEDURE IF EXISTS `reset_score`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reset_score`()
BEGIN
	update userjjc SET score_3day = 1200 where rank=1;
	update userjjc SET score_3day = 900 where rank=2;
	update userjjc SET score_3day = 700 where rank=3;
	update userjjc SET score_3day = 600 where rank between 4 AND 9;
update userjjc SET score_3day = 500 where rank between  10 AND 299;
update userjjc SET score_3day = 400 where rank between  300 AND 999;
update userjjc SET score_3day = 300 where rank between  1000 AND 1999;
update userjjc SET score_3day = 200 where rank between  2000 AND 2999;
update userjjc SET score_3day = 100 where rank NOT BETWEEN 1 and 2999;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `everyday_do_something`
-- ----------------------------
DROP EVENT IF EXISTS `everyday_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `everyday_do_something` ON SCHEDULE EVERY 1 DAY STARTS '2014-03-31 23:59:59' ON COMPLETION NOT PRESERVE ENABLE DO call everyday_do_something()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `reset_score`
-- ----------------------------
DROP EVENT IF EXISTS `reset_score`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `reset_score` ON SCHEDULE EVERY 1 MINUTE STARTS '2014-03-28 23:59:59' ON COMPLETION NOT PRESERVE ENABLE DO call reset_score()
;;
DELIMITER ;
