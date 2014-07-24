/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : tiantiandapao

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-07-24 15:54:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `chargerecord`
-- ----------------------------
DROP TABLE IF EXISTS `chargerecord`;
CREATE TABLE `chargerecord` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '充值流水号',
  `id_uid` varchar(32) NOT NULL DEFAULT 'test' COMMENT '用户uid',
  `value` tinyint(3) NOT NULL DEFAULT '0' COMMENT '充值面额RMB(元)',
  `time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '充值时间戳（单位秒）',
  `phone` varchar(20) NOT NULL DEFAULT '0' COMMENT '充值用户手机号',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话费点充值记录';

-- ----------------------------
-- Records of chargerecord
-- ----------------------------

-- ----------------------------
-- Table structure for `gogal_var`
-- ----------------------------
DROP TABLE IF EXISTS `gogal_var`;
CREATE TABLE `gogal_var` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `boss_charge_remain` int(10) unsigned NOT NULL DEFAULT '2000' COMMENT '每日BOSS被打败话费点剩余',
  `lottery_charge_remain` int(10) unsigned NOT NULL DEFAULT '1500' COMMENT '每日抽奖话费点剩余',
  `every_week_score_item0` tinyint(3) NOT NULL DEFAULT '1' COMMENT '每周积分兑换物品角色随机（id1~3）',
  `every_week_score_item1` tinyint(3) NOT NULL DEFAULT '6' COMMENT '每周积分兑换物品宠物随机（id6~8）',
  `every_week_score_item2` tinyint(3) NOT NULL DEFAULT '9' COMMENT '每周积分兑换物品道具随机（id9~10）',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='全局变量表';

-- ----------------------------
-- Records of gogal_var
-- ----------------------------
INSERT INTO `gogal_var` VALUES ('1', '2000', '1500', '2', '6', '3');

-- ----------------------------
-- Table structure for `operations`
-- ----------------------------
DROP TABLE IF EXISTS `operations`;
CREATE TABLE `operations` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'uid',
  `loginNum` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '每天登录人数',
  `oneGameNum` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '每天玩了一局的用户数',
  `payNum` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '每日付费人数',
  `payCount` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '每日付费次数',
  `payValue` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '每日付费金额',
  `regNum` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '每日新增注册数',
  `boss_charge_remain` int(11) unsigned NOT NULL DEFAULT '2000' COMMENT '每日BOSS被打败话费点剩余',
  `lottery_charge_remain` int(11) unsigned NOT NULL DEFAULT '1500' COMMENT '每日抽奖话费点剩余',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '每日统计数据刷新时间(单位秒)',
  `yunying_channelID` varchar(10) DEFAULT NULL COMMENT '运营渠道ID',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='运营表';

-- ----------------------------
-- Records of operations
-- ----------------------------
INSERT INTO `operations` VALUES ('1', '0', '0', '0', '0', '0', '0', '2000', '1500', '2014-07-21 23:59:59', null);
INSERT INTO `operations` VALUES ('2', '0', '0', '0', '0', '0', '0', '2000', '1500', '2014-07-22 23:59:59', null);
INSERT INTO `operations` VALUES ('3', '0', '0', '0', '0', '0', '0', '2000', '1500', '2014-07-23 23:59:59', null);

-- ----------------------------
-- Table structure for `rmbrecord`
-- ----------------------------
DROP TABLE IF EXISTS `rmbrecord`;
CREATE TABLE `rmbrecord` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '充值流水号',
  `id_uid` varchar(32) NOT NULL DEFAULT 'test' COMMENT '用户UID',
  `value` tinyint(3) NOT NULL DEFAULT '0' COMMENT '充值面额RMB/元',
  `generate_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '充值订单生成时间戳（单位秒）',
  `channelID` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '充值渠道ID（0：电信 1:移动MM 2:移动MDO 3:联通 4:指易付 5:新银河 6:支付宝 7:华为 8:话付宝,9:联通跑酷,10:3k,11:游戏基地）',
  `finish_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '充值订单完成时间戳（单位秒）',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态（0，未完成，1完成）',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='RMB充值记录';

-- ----------------------------
-- Records of rmbrecord
-- ----------------------------
INSERT INTO `rmbrecord` VALUES ('1', '10000001', '29', '1406015075', '10', '1406015085', '1');
INSERT INTO `rmbrecord` VALUES ('2', '10000001', '2', '1406015935', '10', '1406015942', '1');

-- ----------------------------
-- Table structure for `system_statistics`
-- ----------------------------
DROP TABLE IF EXISTS `system_statistics`;
CREATE TABLE `system_statistics` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `independent_ip_by_day` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '每日独立ip访问人数',
  `time` varchar(32) NOT NULL DEFAULT '' COMMENT '统计时间（单位秒）',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for `userfight`
-- ----------------------------
DROP TABLE IF EXISTS `userfight`;
CREATE TABLE `userfight` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '战斗uid流水号',
  `start_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户战斗开始时间(单位秒)',
  `finish_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户战斗结束时间（单位秒）',
  `user_uid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户uid',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '战斗类型（0：普通战斗，1：竞技场战斗）',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='战斗日志表';

-- ----------------------------
-- Records of userfight
-- ----------------------------
INSERT INTO `userfight` VALUES ('1', '1405937965', '0', '10000002', '0');
INSERT INTO `userfight` VALUES ('2', '1405945315', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('3', '1405992703', '1405992744', '10000002', '1');
INSERT INTO `userfight` VALUES ('4', '1405992827', '1405992883', '10000002', '0');
INSERT INTO `userfight` VALUES ('5', '1405993020', '0', '10000001', '0');
INSERT INTO `userfight` VALUES ('6', '1405997580', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('7', '1405998468', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('8', '1405999524', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('9', '1406001188', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('10', '1406001365', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('11', '1406003925', '0', '10000003', '1');
INSERT INTO `userfight` VALUES ('12', '1406012643', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('13', '1406012851', '1406012908', '10000003', '1');
INSERT INTO `userfight` VALUES ('14', '1406013470', '1406013499', '10000003', '0');
INSERT INTO `userfight` VALUES ('15', '1406015115', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('16', '1406015428', '1406015459', '10000001', '1');
INSERT INTO `userfight` VALUES ('17', '1406016588', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('18', '1406016893', '1406017038', '10000001', '1');
INSERT INTO `userfight` VALUES ('19', '1406017050', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('20', '1406017128', '1406017171', '10000001', '1');
INSERT INTO `userfight` VALUES ('21', '1406017196', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('22', '1406017395', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('23', '1406017471', '1406017502', '10000001', '1');
INSERT INTO `userfight` VALUES ('24', '1406017513', '1406017572', '10000001', '1');
INSERT INTO `userfight` VALUES ('25', '1406017584', '1406017648', '10000001', '1');
INSERT INTO `userfight` VALUES ('26', '1406017657', '1406017694', '10000001', '1');
INSERT INTO `userfight` VALUES ('27', '1406017704', '1406017742', '10000001', '1');
INSERT INTO `userfight` VALUES ('28', '1406017751', '1406017785', '10000001', '1');
INSERT INTO `userfight` VALUES ('29', '1406017793', '1406017822', '10000001', '1');
INSERT INTO `userfight` VALUES ('30', '1406017831', '1406017859', '10000001', '1');
INSERT INTO `userfight` VALUES ('31', '1406017872', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('32', '1406017909', '1406017939', '10000001', '1');
INSERT INTO `userfight` VALUES ('33', '1406017975', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('34', '1406018139', '1406018212', '10000001', '1');
INSERT INTO `userfight` VALUES ('35', '1406018227', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('36', '1406018293', '0', '10000001', '1');
INSERT INTO `userfight` VALUES ('37', '1406079263', '1406079295', '10000002', '0');
INSERT INTO `userfight` VALUES ('38', '1406079305', '1406079334', '10000002', '1');
INSERT INTO `userfight` VALUES ('39', '1406082607', '1406082810', '10000005', '1');
INSERT INTO `userfight` VALUES ('40', '1406108823', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('41', '1406108977', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('42', '1406109622', '1406109660', '10000003', '0');
INSERT INTO `userfight` VALUES ('43', '1406109665', '1406109710', '10000003', '0');
INSERT INTO `userfight` VALUES ('44', '1406109755', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('45', '1406109828', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('46', '1406109882', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('47', '1406110288', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('48', '1406110315', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('49', '1406110461', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('50', '1406110581', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('51', '1406110762', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('52', '1406110843', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('53', '1406110990', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('54', '1406111081', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('55', '1406111195', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('56', '1406111391', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('57', '1406111508', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('58', '1406111632', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('59', '1406111650', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('60', '1406111877', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('61', '1406112183', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('62', '1406112362', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('63', '1406112674', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('64', '1406113210', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('65', '1406113298', '1406113353', '10000003', '0');
INSERT INTO `userfight` VALUES ('66', '1406113359', '0', '10000003', '0');
INSERT INTO `userfight` VALUES ('67', '1406113454', '1406113493', '10000003', '0');
INSERT INTO `userfight` VALUES ('68', '1406113501', '1406113542', '10000003', '0');
INSERT INTO `userfight` VALUES ('69', '1406113602', '1406113659', '10000003', '0');
INSERT INTO `userfight` VALUES ('70', '1406113679', '1406113711', '10000003', '0');
INSERT INTO `userfight` VALUES ('71', '1406180111', '1406180296', '10000004', '0');
INSERT INTO `userfight` VALUES ('72', '1406180385', '1406180742', '10000004', '0');

-- ----------------------------
-- Table structure for `usergame`
-- ----------------------------
DROP TABLE IF EXISTS `usergame`;
CREATE TABLE `usergame` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `normal_max_record` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '普通游戏最高成绩',
  `ucharge` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '话费点',
  `ugold` int(10) unsigned NOT NULL DEFAULT '1000' COMMENT '游戏金币',
  `is_get_login_reward_this_day` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否已经领取当日登录奖励(0未领取，1已领取)',
  `uconsecutive` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '连续登录天数',
  `diamond` int(10) unsigned NOT NULL DEFAULT '10' COMMENT '钻石',
  `last_tili_send_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户最近一次获取体力时间',
  `tili` int(10) unsigned NOT NULL DEFAULT '5' COMMENT '体力值',
  `cur_role` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '当前角色（1，2，3，4）',
  `cur_pet` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '当前宠物（1，2，3，4）',
  `cur_airship` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '当前飞艇（1，2，3，4）',
  `cur_role_level` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '当前角色等级',
  `login_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户每天登录次数',
  `max_range` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '历史最远距离',
  `recharge_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '累计充值次数',
  `rmb` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '人民币充值金额字段（大于29才写入，用完清0，用于活动礼包）',
  `recharge_gift` tinyint(1) unsigned DEFAULT '0' COMMENT '是否购买过充值礼包',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000009 DEFAULT CHARSET=utf8 COMMENT='用户游戏表';

-- ----------------------------
-- Records of usergame
-- ----------------------------
INSERT INTO `usergame` VALUES ('10000001', '0', '2250', '17807', '1', '1', '100000', '1405993032', '5', '1', '1', '1', '1', '1', '5524', '2', '0', '1');
INSERT INTO `usergame` VALUES ('10000002', '200', '0', '2105', '0', '2', '100000', '1406079256', '3', '1', '1', '1', '1', '0', '1482', '0', '0', '0');
INSERT INTO `usergame` VALUES ('10000003', '8250', '0', '5139', '1', '1', '99492', '1406111078', '8', '1', '4', '1', '1', '4', '4330', '0', '0', '0');
INSERT INTO `usergame` VALUES ('10000004', '99750', '0', '650', '1', '1', '98502', '1406180310', '4', '3', '4', '1', '9', '3', '12010', '0', '0', '0');
INSERT INTO `usergame` VALUES ('10000005', '0', '0', '10407', '0', '2', '100000', '1406082816', '5', '1', '1', '1', '1', '0', '7988', '0', '0', '0');
INSERT INTO `usergame` VALUES ('10000006', '0', '0', '1500', '0', '1', '100000', '1406100593', '5', '1', '1', '1', '1', '0', '0', '0', '0', '0');
INSERT INTO `usergame` VALUES ('10000007', '0', '0', '3500', '1', '1', '70', '1406169511', '5', '1', '1', '1', '1', '3', '0', '0', '0', '0');
INSERT INTO `usergame` VALUES ('10000008', '0', '0', '1000', '0', '1', '10', '1406181819', '5', '1', '1', '1', '1', '4', '0', '0', '0', '0');

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
  `mac` varchar(64) DEFAULT NULL COMMENT '用户手机mac地址',
  `modify_user_profile_count` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '修改用户配置信息次数',
  `channelID` varchar(10) NOT NULL DEFAULT '0' COMMENT '运营渠道ID（默认908000官网，其他看文档）',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000009 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('10000001', '123456', 'ldy', '1406169995', '1405933776', '72B6CDF21E0F1DF9733F2DA422098FC5', null, '0', '0', '0', '864500028290074', '1', '0');
INSERT INTO `userinfo` VALUES ('10000002', '123456', 'hhh', '1406079251', '1405937951', '4706B24F81222A1BD8A83B7BA2220218', null, '0', '0', '0', '1s445g477x1', '1', '0');
INSERT INTO `userinfo` VALUES ('10000003', '123456', 'sd555', '1406168143', '1405945293', '09C63CEBA5D91318C03F3CC46C263756', null, '0', '0', '0', '8888888877', '1', '0');
INSERT INTO `userinfo` VALUES ('10000004', '123456', 'V10000004', '1406180085', '1405993819', '5648C8C9CE9DF041FD38EDD5F2AF151A', null, '0', '0', '0', '351888061616697', '0', '0');
INSERT INTO `userinfo` VALUES ('10000005', '123456', '傻逼', '1406082524', '1405996638', 'A274900F661A65C5272B43069883096D', null, '0', '0', '0', '860961025860737', '1', '0');
INSERT INTO `userinfo` VALUES ('10000006', '123456', 'V10000006', '1406100610', '1406100593', 'DBD26D43792B436FC391BCE6D2D1E4D0', null, '0', '0', '0', '1x2xxx3x6m7xxx', '0', '908000');
INSERT INTO `userinfo` VALUES ('10000007', '123456', '     ', '1406169802', '1406169511', 'A26692FFFFE2E7BE9A7B017408E92B51', null, '0', '0', '0', '864737010308619', '1', '0');
INSERT INTO `userinfo` VALUES ('10000008', '123456', 'V10000008', '1406182598', '1406181819', 'CD024C1F24626D975742200DE04EB0FB', null, '0', '0', '0', '355868054104261', '0', '0');

-- ----------------------------
-- Table structure for `userjjc`
-- ----------------------------
DROP TABLE IF EXISTS `userjjc`;
CREATE TABLE `userjjc` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `rank` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户在竞技场内的排名',
  `day_max_times` tinyint(3) unsigned NOT NULL DEFAULT '15' COMMENT 'Challenging times 玩家每日最大挑战次数(目前最大次数15，使用钻石+10)',
  `score` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分',
  `jjc_max_record` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户在竞技场内最高成绩',
  `pk_win_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '挑战胜利次数',
  `pk_lost_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '挑战失败次数',
  `pk_draw_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '挑战平局次数',
  `day_cur_times` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT 'Challenging times 玩家每日当前挑战次数',
  `score_3day` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '每三日可领取的积分',
  `charge_3day` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '每三日可领取的话费点',
  `diamond_3day` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '每三日可领取的钻石',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000009 DEFAULT CHARSET=utf8 COMMENT='竞技场相关表';

-- ----------------------------
-- Records of userjjc
-- ----------------------------
INSERT INTO `userjjc` VALUES ('10000001', '1', '15', '2148', '16100', '8', '2', '3', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('10000002', '2', '15', '10', '200', '0', '1', '1', '0', '900', '1575', '50');
INSERT INTO `userjjc` VALUES ('10000003', '3', '15', '5', '1800', '1', '0', '0', '0', '700', '675', '20');
INSERT INTO `userjjc` VALUES ('10000004', '4', '15', '0', '0', '0', '0', '0', '0', '600', '0', '10');
INSERT INTO `userjjc` VALUES ('10000005', '5', '15', '30', '32200', '1', '0', '0', '0', '600', '0', '10');
INSERT INTO `userjjc` VALUES ('10000006', '6', '15', '0', '0', '0', '0', '0', '0', '600', '0', '10');
INSERT INTO `userjjc` VALUES ('10000007', '7', '15', '600', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('10000008', '8', '15', '0', '0', '0', '0', '0', '0', '600', '0', '10');

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
  `prop4` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '宠物1的数量',
  `prop5` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '宠物2的数量',
  `prop6` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '宠物3的数量',
  `prop7` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '宠物4的数量',
  `prop8` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '飞艇1的数量',
  `prop9` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '飞艇2的数量',
  `prop10` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '飞艇3的数量',
  `prop11` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '飞艇4的数量',
  `prop12` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具1超级水晶的数量',
  `prop13` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具2能量之星的数量',
  `prop14` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具3不屈意志的数量',
  `prop15` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具4水晶护甲的数量',
  `prop16` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '道具5永恒之力的数量',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000009 DEFAULT CHARSET=utf8 COMMENT='用户道具表';

-- ----------------------------
-- Records of userprop
-- ----------------------------
INSERT INTO `userprop` VALUES ('10000001', '1', '0', '0', '0', '1', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('10000002', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('10000003', '1', '0', '0', '0', '1', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('10000004', '6', '0', '9', '1', '1', '0', '0', '1', '1', '0', '0', '0', '1', '0', '1', '0', '0');
INSERT INTO `userprop` VALUES ('10000005', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('10000006', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('10000007', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('10000008', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `usertask`
-- ----------------------------
DROP TABLE IF EXISTS `usertask`;
CREATE TABLE `usertask` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `running_task_id` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '当前正在执行的任务ID(0:无任务,1:金币数量,2:竞技场挑战次数,3:游戏成绩,4:游戏场次)',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '任务状态(0:正在执行,1:冻结,2:完成)',
  `finish_task_count` int(3) unsigned NOT NULL DEFAULT '0' COMMENT '完成任务的总次数',
  `gold_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天获取的金币计数',
  `game_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天游戏场次',
  `record_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天总成绩计数',
  `cur_day_finish_task_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当天完成任务的总次数',
  `jjc_pk_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '接取任务后，当天竞技场挑战计数',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000009 DEFAULT CHARSET=utf8 COMMENT='用户任务表';

-- ----------------------------
-- Records of usertask
-- ----------------------------
INSERT INTO `usertask` VALUES ('10000001', '4', '0', '1', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('10000002', '2', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('10000003', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('10000004', '4', '0', '1', '999', '1', '99750', '1', '0');
INSERT INTO `usertask` VALUES ('10000005', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('10000006', '3', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('10000007', '4', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('10000008', '1', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `user_system_reward`
-- ----------------------------
DROP TABLE IF EXISTS `user_system_reward`;
CREATE TABLE `user_system_reward` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内部用户ID',
  `reward1` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '奖励1是否领取（0没领取，1领取）',
  `reward2` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '奖励2是否领取（0没领取，1领取）',
  `reward3` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '奖励3是否领取（0没领取，1领取）',
  `reward4` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '奖励4是否领取（0没领取，1领取）',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000009 DEFAULT CHARSET=utf8 COMMENT='用户系统奖励表';

-- ----------------------------
-- Records of user_system_reward
-- ----------------------------
INSERT INTO `user_system_reward` VALUES ('10000001', '1', '0', '0', '0');
INSERT INTO `user_system_reward` VALUES ('10000002', '0', '0', '0', '0');
INSERT INTO `user_system_reward` VALUES ('10000003', '0', '0', '0', '0');
INSERT INTO `user_system_reward` VALUES ('10000004', '1', '0', '0', '0');
INSERT INTO `user_system_reward` VALUES ('10000005', '1', '0', '0', '0');
INSERT INTO `user_system_reward` VALUES ('10000006', '0', '0', '0', '0');
INSERT INTO `user_system_reward` VALUES ('10000007', '1', '0', '0', '0');
INSERT INTO `user_system_reward` VALUES ('10000008', '0', '0', '0', '0');

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
TRUNCATE TABLE user_system_reward;
TRUNCATE TABLE rmbrecord;
TRUNCATE TABLE operations;
TRUNCATE TABLE userfight;
TRUNCATE TABLE chargerecord;
TRUNCATE TABLE system_statistics;
alter table userinfo AUTO_INCREMENT=10000001;
alter table userjjc AUTO_INCREMENT=10000001;
alter table usertask AUTO_INCREMENT=10000001;
alter table usergame AUTO_INCREMENT=10000001;
alter table userprop AUTO_INCREMENT=10000001;
alter table user_system_reward AUTO_INCREMENT=10000001;
alter table rmbrecord AUTO_INCREMENT=1;
alter table operations AUTO_INCREMENT=1;
alter table userfight AUTO_INCREMENT=1;
alter table chargerecord AUTO_INCREMENT=1;
alter table system_statistics AUTO_INCREMENT=1;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `every3day_do_something`
-- ----------------------------
DROP PROCEDURE IF EXISTS `every3day_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `every3day_do_something`()
BEGIN
update userjjc SET score_3day =0,charge_3day=0,diamond_3day=0;
update userjjc SET score_3day = 100 order by userjjc.jjc_max_record DESC LIMIT 3999;/*where rank between  3000 AND 3999;*/
update userjjc SET score_3day = 200 order by userjjc.jjc_max_record DESC LIMIT 2999;/*where rank between  2000 AND 2999;*/
update userjjc SET score_3day = 300 order by userjjc.jjc_max_record DESC LIMIT 1999;/*where rank between  1000 AND 1999;*/
update userjjc SET score_3day = 400 order by userjjc.jjc_max_record DESC LIMIT 999;/*where rank between  300 AND 999;*/
update userjjc SET score_3day = 500 order by userjjc.jjc_max_record DESC LIMIT 299; /*where rank between  10 AND 299;*/
update userjjc SET score_3day = 600 ,diamond_3day=10 order by userjjc.jjc_max_record DESC LIMIT 9;/*where rank between  3 AND 9;*/
update userjjc SET score_3day = 700 ,charge_3day=675,diamond_3day=20  order by userjjc.jjc_max_record DESC LIMIT 3;
update userjjc SET score_3day = 900 ,charge_3day=1575,diamond_3day=50 order by userjjc.jjc_max_record DESC LIMIT 2;
update userjjc SET score_3day = 1200,charge_3day=2250,diamond_3day=80 order by userjjc.jjc_max_record DESC LIMIT 1;
UPDATE userjjc SET jjc_max_record=0;
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
update userjjc SET userjjc.day_cur_times = 0,userjjc.day_max_times=15;
update usergame SET usergame.is_get_login_reward_this_day=0,login_count=0;
update usertask SET usertask.cur_day_finish_task_count=0;
update usertask set gold_count= default(gold_count);
update usertask set game_count= default(game_count);
update usertask set record_count= default(record_count);
update usertask set jjc_pk_count= default(jjc_pk_count);
update gogal_var set boss_charge_remain= default(boss_charge_remain), lottery_charge_remain= default(lottery_charge_remain);
INSERT into operations()  VALUES();
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `every_week_do_something`
-- ----------------------------
DROP PROCEDURE IF EXISTS `every_week_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `every_week_do_something`()
BEGIN
update gogal_var set every_week_score_item0 = 2;
update  gogal_var set every_week_score_item1 = FLOOR(6 + (RAND() * 3));
update  gogal_var set every_week_score_item2 = 3;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `huodong_everyday_do_something`
-- ----------------------------
DROP PROCEDURE IF EXISTS `huodong_everyday_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `huodong_everyday_do_something`()
BEGIN
/*update user_system_reward set reward1= default(reward1);*/
update user_system_reward set reward2= default(reward2);
/*update user_system_reward set reward3= default(reward3);*/
/*update user_system_reward set reward4= default(reward4);*/
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
	update userjjc SET score_3day = 1200,diamond_3day=80,charge_3day=2250 where userjjc.rank=1 and  userjjc.uid=in_uid;
	update userjjc SET score_3day = 900 ,diamond_3day=50,charge_3day=1575 where userjjc.rank=2  and userjjc.uid=in_uid;
	update userjjc SET score_3day = 700 ,diamond_3day=20,charge_3day=675 where userjjc.rank=3  and userjjc.uid=in_uid;
	update userjjc SET score_3day = 600,diamond_3day=10 where rank between 4 AND 9 and uid=in_uid;
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
update userprop,usergame SET userprop.prop14 = userprop.prop14+1,usergame.is_get_login_reward_this_day=1 where uconsecutive=3  and usergame.uid=in_uid and userprop.uid=in_uid;
update usergame SET ugold = ugold+1450,is_get_login_reward_this_day=1 where uconsecutive=4  and uid=in_uid;
update usergame SET ugold = ugold+2000,usergame.is_get_login_reward_this_day=1 where uconsecutive=5  and uid=in_uid;
update usergame SET diamond = diamond+40,is_get_login_reward_this_day=1 where uconsecutive=6  and uid=in_uid;
update userjjc,usergame SET userjjc.score = userjjc.score+100,usergame.is_get_login_reward_this_day=1 where usergame.uconsecutive=7  and usergame.uid=in_uid and userjjc.uid=in_uid;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `yunying_everyday_do_something`
-- ----------------------------
DROP PROCEDURE IF EXISTS `yunying_everyday_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `yunying_everyday_do_something`()
BEGIN
declare yestoday_time  int(10);
declare regNum1 int(10);
select UNIX_TIMESTAMP(operations.time) into yestoday_time from operations  order by operations.uid desc LIMIT 1;
select  count(MY.uid) into regNum1 from (select userinfo.* from userinfo   where userinfo.urtime>yestoday_time) as MY;
select regNum1;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `every7day_do_something`
-- ----------------------------
DROP EVENT IF EXISTS `every7day_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `every7day_do_something` ON SCHEDULE EVERY 1 WEEK STARTS '2014-06-29 23:59:59' ON COMPLETION NOT PRESERVE ENABLE DO call every3day_do_something()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `everyday_do_something`
-- ----------------------------
DROP EVENT IF EXISTS `everyday_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `everyday_do_something` ON SCHEDULE EVERY 1 DAY STARTS '2014-05-30 23:59:59' ON COMPLETION NOT PRESERVE ENABLE DO call everyday_do_something()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `every_week_do_something`
-- ----------------------------
DROP EVENT IF EXISTS `every_week_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `every_week_do_something` ON SCHEDULE EVERY 1 WEEK STARTS '2014-05-30 23:59:59' ON COMPLETION NOT PRESERVE ENABLE DO call every_week_do_something()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `huodong_everyday_do_something`
-- ----------------------------
DROP EVENT IF EXISTS `huodong_everyday_do_something`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `huodong_everyday_do_something` ON SCHEDULE EVERY 1 DAY STARTS '2014-05-30 23:59:59' ON COMPLETION NOT PRESERVE ENABLE DO call huodong_everyday_do_something()
;;
DELIMITER ;
