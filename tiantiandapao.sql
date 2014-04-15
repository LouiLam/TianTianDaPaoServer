/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : tiantiandapao

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-04-15 17:48:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `chargerecord`
-- ----------------------------
DROP TABLE IF EXISTS `chargerecord`;
CREATE TABLE `chargerecord` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '充值流水号',
  `id` varchar(32) NOT NULL DEFAULT 'test' COMMENT '帐号',
  `value` tinyint(3) NOT NULL DEFAULT '0' COMMENT '充值面额RMB',
  `time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '充值时间戳（单位秒）',
  `phone` varchar(20) NOT NULL DEFAULT '0' COMMENT '用户手机号',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chargerecord
-- ----------------------------
INSERT INTO `chargerecord` VALUES ('1', 'test', '0', '0', '0');
INSERT INTO `chargerecord` VALUES ('2', 'test', '0', '0', '0');
INSERT INTO `chargerecord` VALUES ('3', 'ffff', '1', '1397119080', '13975800403');
INSERT INTO `chargerecord` VALUES ('4', 'ffff', '1', '1397119146', '13975800403');
INSERT INTO `chargerecord` VALUES ('5', 'ffff', '1', '1397119385', '13975800403');
INSERT INTO `chargerecord` VALUES ('6', 'ffff', '1', '1397119498', '13975800403');
INSERT INTO `chargerecord` VALUES ('7', 'yyyy', '1', '1397299328', '13278860564');

-- ----------------------------
-- Table structure for `usergame`
-- ----------------------------
DROP TABLE IF EXISTS `usergame`;
CREATE TABLE `usergame` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `normal_max_record` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '普通游戏最高成绩',
  `ucharge` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '话费点',
  `ugold` int(10) unsigned NOT NULL DEFAULT '100000' COMMENT '游戏金币',
  `is_get_login_reward_this_day` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否已经领取当日登录奖励(0未领取，1已领取)',
  `uconsecutive` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '连续登录天数',
  `diamond` int(10) unsigned NOT NULL DEFAULT '100000' COMMENT '钻石',
  `last_tili_send_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户最近一次获取体力时间',
  `tili` int(10) unsigned NOT NULL DEFAULT '5' COMMENT '体力值',
  `cur_role` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '当前角色（1，2，3，4）',
  `cur_pet` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '当前宠物（1，2，3，4）',
  `cur_airship` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '当前飞艇（1，2，3，4）',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usergame
-- ----------------------------
INSERT INTO `usergame` VALUES ('1', '500', '0', '100510', '1', '1', '100000', '1397551701', '4', '1', '1', '1');
INSERT INTO `usergame` VALUES ('2', '0', '0', '100505', '1', '1', '100000', '1397552608', '5', '1', '1', '1');
INSERT INTO `usergame` VALUES ('3', '0', '0', '100000', '0', '1', '100000', '1397552590', '5', '1', '1', '1');
INSERT INTO `usergame` VALUES ('4', '0', '0', '100507', '1', '1', '100000', '1397552713', '3', '1', '1', '1');
INSERT INTO `usergame` VALUES ('5', '0', '0', '100500', '1', '1', '100000', '1397555060', '5', '1', '1', '1');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `id` varchar(32) NOT NULL COMMENT '用户名',
  `ultime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户最近登录时间(单位秒)',
  `urtime` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户注册时间戳（单位秒）',
  `utoken` varchar(64) DEFAULT NULL COMMENT '登录成功后使用此token与服务端通信',
  `uemail` varchar(32) DEFAULT NULL COMMENT '用户邮箱',
  `uphone` varchar(20) DEFAULT '0' COMMENT '用户手机',
  `ugender` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户性别：0=未定义 1=男性 2=女性',
  `ustatus` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户账号状态：0=正常 1=封停 2=GM账号',
  `mac` varchar(64) DEFAULT NULL COMMENT '用户mac地址',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='与平台相关的静态用户信息';

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '123456', 'Visitor--1', '1397551701', '1397551701', 'EE52F32189578A0A37B26B947872CF66', null, '0', '0', '0', '283749159');
INSERT INTO `userinfo` VALUES ('2', '123456', 'Visitor--2', '1397552640', '1397551915', '45CC015FCDC0B738EAD0FDF167FA558D', null, '0', '0', '0', '283749165159');
INSERT INTO `userinfo` VALUES ('3', '123456', 'Visitor--3', '1397552590', '1397552590', 'B7134B18E962AC4BCDAC598F1920D2B1', null, '0', '0', '0', 'D8-50-E6-5A-E9-82');
INSERT INTO `userinfo` VALUES ('4', '123456', 'Visitor--4', '1397553218', '1397552713', '18ECBACDA99D9A1BAD075206DA90829F', null, '0', '0', '0', '28165159');
INSERT INTO `userinfo` VALUES ('5', '123456', 'Visitor--5', '1397555273', '1397555060', '019B495E9B753D355A1826F75815CCA2', null, '0', '0', '0', '4846554');

-- ----------------------------
-- Table structure for `userjjc`
-- ----------------------------
DROP TABLE IF EXISTS `userjjc`;
CREATE TABLE `userjjc` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户唯一id标识符',
  `rank` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户在竞技场内的排名',
  `day_max_times` tinyint(3) unsigned NOT NULL DEFAULT '15' COMMENT 'Challenging times 玩家每日最大挑战次数(目前最大次数15，使用钻石+10)',
  `score` int(10) unsigned NOT NULL DEFAULT '100000' COMMENT '积分(每次挑战胜利获得十点积分，失败得5积分。)',
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
INSERT INTO `userjjc` VALUES ('1', '3', '15', '101200', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('2', '1', '15', '100910', '1500', '1', '0', '0', '1', '0');
INSERT INTO `userjjc` VALUES ('3', '4', '15', '100000', '0', '0', '0', '0', '0', '700');
INSERT INTO `userjjc` VALUES ('4', '2', '15', '100620', '700', '2', '0', '0', '2', '0');
INSERT INTO `userjjc` VALUES ('5', '5', '15', '100000', '0', '0', '0', '0', '0', '600');

-- ----------------------------
-- Table structure for `userprop`
-- ----------------------------
DROP TABLE IF EXISTS `userprop`;
CREATE TABLE `userprop` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `prop0` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '角色1的等级（0表示没拥有此角色）',
  `prop1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '角色2的等级（0表示没拥有此角色）',
  `prop2` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '角色3的等级（0表示没拥有此角色）',
  `prop3` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '角色4的等级（0表示没拥有此角色）',
  `prop4` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '宠物1：的数量',
  `prop5` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '宠物2：的数量',
  `prop6` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '宠物3：的数量',
  `prop7` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '宠物4：的数量',
  `prop8` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '飞艇1：的数量',
  `prop9` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '飞艇2：的数量',
  `prop10` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '飞艇3：的数量',
  `prop11` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '飞艇4：的数量',
  `prop12` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具1：超级水晶的数量',
  `prop13` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具2：能量之星的数量',
  `prop14` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具3：不屈意志的数量',
  `prop15` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具4：水晶护甲的数量',
  `prop16` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具5：永恒之力的数量',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userprop
-- ----------------------------
INSERT INTO `userprop` VALUES ('1', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('2', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('3', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('4', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('5', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `usertask`
-- ----------------------------
DROP TABLE IF EXISTS `usertask`;
CREATE TABLE `usertask` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户uid',
  `running_task_id` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '当前正在执行的任务ID(0:无任务,1:金币数量,2:竞技场挑战次数,3:游戏成绩,4:游戏场次)',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '任务状态(0:正在执行,1:冻结,2:完成)',
  `finish_task_count` int(3) unsigned NOT NULL DEFAULT '0' COMMENT '完成任务的总次数',
  `gold_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天获取的金币计数',
  `game_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天游戏场次',
  `record_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天总成绩计数',
  `cur_day_finish_task_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当天完成任务的总次数',
  `jjc_pk_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天竞技场挑战计数',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertask
-- ----------------------------
INSERT INTO `usertask` VALUES ('1', '2', '0', '0', '5', '1', '500', '0', '0');
INSERT INTO `usertask` VALUES ('2', '1', '0', '0', '5', '1', '1500', '0', '0');
INSERT INTO `usertask` VALUES ('3', '4', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('4', '2', '0', '0', '7', '2', '900', '0', '0');
INSERT INTO `usertask` VALUES ('5', '3', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- View structure for `select_gameover_task`
-- ----------------------------
DROP VIEW IF EXISTS `select_gameover_task`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `select_gameover_task` AS select `usertask`.`running_task_id` AS `running_task_id`,`usertask`.`gold_count` AS `gold_count`,`usertask`.`game_count` AS `game_count`,`usertask`.`record_count` AS `record_count`,`usertask`.`jjc_pk_count` AS `jjc_pk_count`,`usertask`.`uid` AS `uid` from `usertask` ;

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
TRUNCATE TABLE userprop;
alter table userinfo AUTO_INCREMENT=1;
alter table userjjc AUTO_INCREMENT=1;
alter table usertask AUTO_INCREMENT=1;
alter table usergame AUTO_INCREMENT=1;
alter table userprop AUTO_INCREMENT=1;
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
update userjjc,usergame,usertask  SET usertask.cur_day_finish_task_count=0,userjjc.day_cur_times = 0,userjjc.day_max_times=15,usergame.is_get_login_reward_this_day=0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `insert_score_3day`
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_score_3day`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_score_3day`(in_uid long)
BEGIN
	update userjjc SET score_3day = 1200 where rank=1 and uid=in_uid;
	update userjjc SET score_3day = 900 where rank=2 and uid=in_uid;
	update userjjc SET score_3day = 700 where rank=3 and uid=in_uid;
	update userjjc SET score_3day = 600 where rank between 4 AND 9 and uid=in_uid;
update userjjc SET score_3day = 500 where rank between  10 AND 299 and uid=in_uid;
update userjjc SET score_3day = 400 where rank between  300 AND 999 and uid=in_uid;
update userjjc SET score_3day = 300 where rank between  1000 AND 1999 and uid=in_uid;
update userjjc SET score_3day = 200 where rank between  2000 AND 2999 and uid=in_uid;
update userjjc SET score_3day = 100 where rank NOT BETWEEN 1 and 2999 and uid=in_uid;
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
-- Procedure structure for `reset_score_3day`
-- ----------------------------
DROP PROCEDURE IF EXISTS `reset_score_3day`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reset_score_3day`()
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
-- Event structure for `reset_score_3day`
-- ----------------------------
DROP EVENT IF EXISTS `reset_score_3day`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `reset_score_3day` ON SCHEDULE EVERY 1 DAY STARTS '2014-03-28 23:59:59' ON COMPLETION NOT PRESERVE ENABLE DO call reset_score_3day()
;;
DELIMITER ;
