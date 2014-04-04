/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : tiantiandapao

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-04-04 14:55:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `usergame`
-- ----------------------------
DROP TABLE IF EXISTS `usergame`;
CREATE TABLE `usergame` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `normal_max_record` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '普通游戏最高成绩',
  `ucharge` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '话费点',
  `ugold` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '游戏金币',
  `is_get_login_reward_this_day` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否已经领取当日登录奖励(0未领取，1已领取)',
  `uconsecutive` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '连续登录天数',
  `diamond` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '钻石',
  `last_tili_send_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户最近一次获取体力时间',
  `tili` tinyint(3) unsigned NOT NULL DEFAULT '5' COMMENT '体力值',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usergame
-- ----------------------------
INSERT INTO `usergame` VALUES ('1', '0', '0', '0', '0', '1', '0', '1396575405', '3');
INSERT INTO `usergame` VALUES ('2', '0', '0', '1000', '1', '1', '0', '1396575715', '5');
INSERT INTO `usergame` VALUES ('3', '0', '0', '0', '0', '1', '0', '1396579998', '5');
INSERT INTO `usergame` VALUES ('4', '0', '0', '0', '0', '1', '0', '1396580231', '5');
INSERT INTO `usergame` VALUES ('5', '0', '0', '0', '0', '1', '0', '1396581019', '5');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `password` varchar(40) DEFAULT NULL COMMENT '用户密码',
  `id` varchar(100) DEFAULT NULL COMMENT '用户名',
  `ultime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户最近登录时间(单位秒)',
  `urtime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户注册时间戳（单位秒）',
  `utoken` varchar(64) DEFAULT NULL COMMENT '登录成功后使用此token与服务端通信',
  `uemail` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `uphone` varchar(20) DEFAULT '0' COMMENT '用户手机',
  `ugender` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户性别：0=未定义 1=男性 2=女性',
  `ustatus` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户账号状态：0=正常 1=封停 2=GM账号',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='与平台相关的静态用户信息';

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '1111', 'aaaa', '1396576553', '1396529341', 'CA38ECFE5B1B236301E11D4E1C51896C', null, '13957680302', '0', '0');
INSERT INTO `userinfo` VALUES ('2', 'eeee', 'eeee', '1396594099', '1396575715', '40C0698D8DF4E4A8F2C0539740DED4AE', null, 'eeee', '0', '0');
INSERT INTO `userinfo` VALUES ('3', 'cccc', 'asdfasdf', '0', '1396579998', '371A8D690FC8F625B7FD9FD7BEACA43F', null, 'cccc', '0', '0');
INSERT INTO `userinfo` VALUES ('4', '1111', 'z12332', '0', '1396580231', '5BE026727272EA99BA2C2AFD88601B9E', null, '123', '0', '0');
INSERT INTO `userinfo` VALUES ('5', 'xxxxc', 'xxxc', '1396581032', '1396581019', '6C0A448F28DC0DB1CD989AA4904C9B15', null, 'bbbhb', '0', '0');

-- ----------------------------
-- Table structure for `userjjc`
-- ----------------------------
DROP TABLE IF EXISTS `userjjc`;
CREATE TABLE `userjjc` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户唯一id标识符',
  `rank` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户在竞技场内的排名',
  `day_max_times` tinyint(3) unsigned NOT NULL DEFAULT '15' COMMENT 'Challenging times 玩家每日最大挑战次数(目前最大次数15，使用钻石+10)',
  `score` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分(每次挑战胜利获得十点积分，失败得5积分。)',
  `jjc_max_record` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当前竞技场最高成绩',
  `pk_win_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '挑战胜利次数',
  `pk_lost_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '挑战失败次数',
  `pk_draw_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '挑战平局次数',
  `day_cur_times` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT 'Challenging times 玩家每日当前挑战次数',
  `score_3day` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '每三日领取的积分',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='竞技场相关表';

-- ----------------------------
-- Records of userjjc
-- ----------------------------
INSERT INTO `userjjc` VALUES ('1', '1', '15', '0', '0', '0', '0', '0', '0', '1200');
INSERT INTO `userjjc` VALUES ('2', '2', '15', '900', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('3', '3', '15', '0', '0', '0', '0', '0', '0', '700');
INSERT INTO `userjjc` VALUES ('4', '4', '15', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('5', '5', '15', '0', '0', '0', '0', '0', '0', '600');

-- ----------------------------
-- Table structure for `usertask`
-- ----------------------------
DROP TABLE IF EXISTS `usertask`;
CREATE TABLE `usertask` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户uid',
  `running_task_id` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '当前正在执行的任务ID(0:无任务,1:金币数量,2:竞技场挑战次数,3:游戏成绩,4:游戏场次)',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '任务状态(0:正在执行,1:冻结)',
  `finish_task_count` int(3) unsigned NOT NULL DEFAULT '0' COMMENT '完成任务的总次数',
  `gold_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '每日获取的金币计数',
  `game_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '游戏场次',
  `record_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当天总成绩计数',
  `cur_day_finish_task_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当天完成任务的总次数',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertask
-- ----------------------------
INSERT INTO `usertask` VALUES ('1', '4', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('2', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('3', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('4', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('5', '3', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Procedure structure for `clear_data`
-- ----------------------------
DROP PROCEDURE IF EXISTS `clear_data`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `clear_data`()
BEGIN
TRUNCATE TABLE userinfo;
TRUNCATE TABLE userjjc;
TRUNCATE TABLE usertask;
TRUNCATE TABLE usergame;
alter table userinfo AUTO_INCREMENT=1;
alter table userjjc AUTO_INCREMENT=1;
alter table usertask AUTO_INCREMENT=1;
alter table usergame AUTO_INCREMENT=1;
END
;;
DELIMITER ;

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
update usergame SET ugold =ugold+500,is_get_login_reward_this_day=1 where uconsecutive=1 and uid=in_uid;
update usergame SET ugold =ugold+ 1000,is_get_login_reward_this_day=1 where uconsecutive=2  and uid=in_uid;
update usergame SET ugold = ugold+2000,is_get_login_reward_this_day=1 where uconsecutive=3  and uid=in_uid;
update usergame SET ugold = ugold+4000,is_get_login_reward_this_day=1 where uconsecutive=4  and uid=in_uid;
update usergame SET ugold = ugold+8000,is_get_login_reward_this_day=1 where uconsecutive=5  and uid=in_uid;
update usergame SET ugold = ugold+16000,is_get_login_reward_this_day=1 where uconsecutive=6  and uid=in_uid;
update usergame SET ugold = ugold+32000,is_get_login_reward_this_day=1 where uconsecutive=7  and uid=in_uid;
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
