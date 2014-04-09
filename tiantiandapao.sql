/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : tiantiandapao

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-04-09 16:23:51
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usergame
-- ----------------------------
INSERT INTO `usergame` VALUES ('1', '100', '0', '500', '1', '1', '9680', '1397028561', '3');
INSERT INTO `usergame` VALUES ('2', '0', '0', '3000', '1', '2', '0', '1396575715', '5');
INSERT INTO `usergame` VALUES ('3', '0', '0', '0', '0', '1', '0', '1396579998', '5');
INSERT INTO `usergame` VALUES ('4', '0', '0', '0', '0', '1', '0', '1396580231', '5');
INSERT INTO `usergame` VALUES ('5', '0', '0', '0', '0', '1', '0', '1396581019', '5');
INSERT INTO `usergame` VALUES ('6', '0', '0', '0', '0', '1', '0', '1396595442', '5');
INSERT INTO `usergame` VALUES ('7', '0', '0', '0', '0', '1', '0', '1396600350', '5');
INSERT INTO `usergame` VALUES ('8', '0', '0', '0', '0', '1', '0', '1396600454', '5');
INSERT INTO `usergame` VALUES ('9', '0', '0', '0', '0', '1', '0', '1396600544', '5');
INSERT INTO `usergame` VALUES ('10', '0', '0', '0', '0', '1', '0', '1396601705', '5');
INSERT INTO `usergame` VALUES ('11', '0', '0', '0', '0', '1', '0', '1396603949', '5');
INSERT INTO `usergame` VALUES ('12', '0', '0', '0', '0', '1', '0', '1396707946', '5');
INSERT INTO `usergame` VALUES ('13', '0', '0', '0', '0', '2', '0', '1396708101', '5');
INSERT INTO `usergame` VALUES ('14', '0', '0', '0', '0', '1', '0', '1396925732', '5');
INSERT INTO `usergame` VALUES ('15', '0', '0', '0', '0', '1', '0', '1396929724', '5');
INSERT INTO `usergame` VALUES ('16', '0', '0', '0', '0', '1', '0', '1396930504', '5');
INSERT INTO `usergame` VALUES ('17', '0', '0', '0', '0', '1', '0', '1396945143', '5');
INSERT INTO `usergame` VALUES ('18', '0', '0', '0', '0', '1', '0', '1396945734', '5');
INSERT INTO `usergame` VALUES ('19', '0', '0', '0', '0', '1', '0', '1396945764', '5');
INSERT INTO `usergame` VALUES ('20', '0', '0', '0', '0', '1', '0', '1396947022', '5');
INSERT INTO `usergame` VALUES ('21', '0', '0', '0', '0', '1', '0', '1396964089', '5');
INSERT INTO `usergame` VALUES ('22', '0', '0', '0', '0', '1', '0', '1397014447', '5');
INSERT INTO `usergame` VALUES ('23', '0', '0', '500', '1', '1', '0', '1397014583', '5');
INSERT INTO `usergame` VALUES ('24', '0', '0', '0', '0', '1', '0', '1397021381', '5');
INSERT INTO `usergame` VALUES ('25', '0', '0', '0', '0', '1', '0', '1397022108', '5');
INSERT INTO `usergame` VALUES ('26', '0', '0', '0', '0', '1', '0', '1397022393', '5');
INSERT INTO `usergame` VALUES ('27', '0', '0', '500', '1', '1', '0', '1397023920', '5');
INSERT INTO `usergame` VALUES ('28', '0', '0', '500', '1', '1', '0', '1397023996', '5');
INSERT INTO `usergame` VALUES ('29', '0', '0', '0', '0', '1', '0', '1397025249', '5');
INSERT INTO `usergame` VALUES ('30', '0', '0', '500', '1', '1', '0', '1397025316', '5');
INSERT INTO `usergame` VALUES ('31', '0', '0', '0', '0', '1', '0', '1397027216', '5');
INSERT INTO `usergame` VALUES ('32', '0', '0', '0', '0', '1', '0', '1397027686', '5');
INSERT INTO `usergame` VALUES ('33', '0', '0', '500', '1', '1', '0', '1397029139', '5');
INSERT INTO `usergame` VALUES ('34', '0', '0', '500', '1', '1', '0', '1397030084', '5');
INSERT INTO `usergame` VALUES ('35', '0', '0', '500', '1', '1', '0', '1397030246', '5');

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='与平台相关的静态用户信息';

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '1111', 'aaaa', '1396576553', '1396529341', 'CA38ECFE5B1B236301E11D4E1C51896C', null, '13957680302', '0', '0');
INSERT INTO `userinfo` VALUES ('2', 'eeee', 'eeee', '1397029111', '1396575715', 'C143B153F529C771552BAE859EB2FB17', null, 'eeee', '0', '0');
INSERT INTO `userinfo` VALUES ('3', 'cccc', 'asdfasdf', '0', '1396579998', '371A8D690FC8F625B7FD9FD7BEACA43F', null, 'cccc', '0', '0');
INSERT INTO `userinfo` VALUES ('4', '1111', 'z12332', '0', '1396580231', '5BE026727272EA99BA2C2AFD88601B9E', null, '123', '0', '0');
INSERT INTO `userinfo` VALUES ('5', 'xxxxc', 'xxxc', '1396581032', '1396581019', '6C0A448F28DC0DB1CD989AA4904C9B15', null, 'bbbhb', '0', '0');
INSERT INTO `userinfo` VALUES ('6', 'gggg', 'gggg', '0', '1396595442', '3977A82C2DDB6EF71B239560F155F4E4', null, 'gggg', '0', '0');
INSERT INTO `userinfo` VALUES ('7', 'tttt', 'tttt', '0', '1396600350', '786BE1DB404BF294460BA6B4DBCDA3FC', null, 'fcvgg', '0', '0');
INSERT INTO `userinfo` VALUES ('8', 'z123456', 'z112233', '0', '1396600454', 'D1A7DC4CCB1F9CD5F77DA9D4C9C623F5', null, '18570617450', '0', '0');
INSERT INTO `userinfo` VALUES ('9', 'z11111', 'lllsss11', '0', '1396600544', '25C708DEFA31DD374C5A77B2E5F0188B', null, '18570617450', '0', '0');
INSERT INTO `userinfo` VALUES ('10', 'xxxx', 'yyyy', '1396602018', '1396601705', '7CCBC1ED6E52B51D8C3319350895FDF0', null, 'cccc', '0', '0');
INSERT INTO `userinfo` VALUES ('11', 'uuuu', 'uuuu', '1396603950', '1396603949', '66A2F62EA3CDA89B8ED4AF33A33F2AD3', null, 'hhhh', '0', '0');
INSERT INTO `userinfo` VALUES ('12', 'fffff', 'sdfsdfsaf', '1396797791', '1396707946', '9F36F2BE5410947CCEC8CCB4C3D51BEE', null, 'sadf', '0', '0');
INSERT INTO `userinfo` VALUES ('13', 'gggg', 'hvvhhh', '1396851023', '1396708101', 'D79A439F37B753B1E2DE32D6EFDBFB97', null, 'uuuhjn', '0', '0');
INSERT INTO `userinfo` VALUES ('14', '3333', 'xxxxxxx', '1396927830', '1396925732', 'A33330B5F6E7E5F25D07AAAD6FAD6EC7', null, '3333', '0', '0');
INSERT INTO `userinfo` VALUES ('15', 'kkkk', 'kkkkkkkkfghj', '1396929804', '1396929724', 'A09031E8FDEC05CF2F67EEAE9988D921', null, 'kkkk', '0', '0');
INSERT INTO `userinfo` VALUES ('16', 'dddd', 'asdfasdfa', '1396963968', '1396930504', 'AA5CA07FD1E5F7CE59096E7C3587EB02', null, 'dddd', '0', '0');
INSERT INTO `userinfo` VALUES ('17', '3333', 'vghhvvvvh', '1396945144', '1396945143', 'C734553B058FB112A8E520F1F9A6BA87', null, '4444', '0', '0');
INSERT INTO `userinfo` VALUES ('18', '1111', 'z112332', '0', '1396945734', '27258FDBD7BC8E00BA8C8835CE0AE0AF', null, '13957680302', '0', '0');
INSERT INTO `userinfo` VALUES ('19', '1111', 'z1123321', '0', '1396945764', '97BDA6A5645A04C27C38D867B6BA7C9F', null, '13957680302', '0', '0');
INSERT INTO `userinfo` VALUES ('20', 'xxxxx', 'yxff', '1396961670', '1396947022', '4920F95E0591BF05BAF71B8B0DD42518', null, 'ggggg', '0', '0');
INSERT INTO `userinfo` VALUES ('21', '4444', 'ygbhbklj', '1397014424', '1396964089', '521414D0A189BE7B4C5609275B028DFF', null, '4141', '0', '0');
INSERT INTO `userinfo` VALUES ('22', 'dddd', 'dasfgdfs', '1397014448', '1397014447', '26068CA0090B44137FEE2805AF371E03', null, 'dddd', '0', '0');
INSERT INTO `userinfo` VALUES ('23', 'ffff', 'd98j', '1397023651', '1397014583', '633E41E4AA11DC63E8B958D57E696C56', null, 'fff', '0', '0');
INSERT INTO `userinfo` VALUES ('24', 'wwww', 'jjsjbb', '1397024981', '1397021381', '8F44ACD3B27A004DBFFB552B005A1276', null, 'ss', '0', '0');
INSERT INTO `userinfo` VALUES ('25', '7363684', 'mqs1234', '1397023033', '1397022108', 'B63361E3DE8C572AA19966D3259766B2', null, '18682068', '0', '0');
INSERT INTO `userinfo` VALUES ('26', 'xxxx', 'yyyyx', '1397025161', '1397022393', '3813658AC3856C6A3097CB17A32F836C', null, 'hgggggg', '0', '0');
INSERT INTO `userinfo` VALUES ('27', 'fffff', 'ffffffasdf', '1397023923', '1397023920', 'C7392FAF896D8F03C6D3187EDD7A08F7', null, 'ffff', '0', '0');
INSERT INTO `userinfo` VALUES ('28', 'dddd', 'asdf34', '1397024613', '1397023996', '73BBC1A6FE8D42D55861B4222920E3EA', null, 'ddd', '0', '0');
INSERT INTO `userinfo` VALUES ('29', '111111', 'ttddpp', '1397025410', '1397025249', 'D17AD138A9F219202E01809AB45A2A6F', null, '13574840167', '0', '0');
INSERT INTO `userinfo` VALUES ('30', 'eeee', 'eeeet', '1397025317', '1397025316', '15855597FE9602A104A711FB69761296', null, 'dd', '0', '0');
INSERT INTO `userinfo` VALUES ('31', 'xxxxx', 'c3214654354', '1397027217', '1397027216', '3BE12EEC8FC17BFBB32D589FBAB3F4BE', null, 'fasdf', '0', '0');
INSERT INTO `userinfo` VALUES ('32', 'ffff', 'ffffasdf', '1397029087', '1397027686', '8A549F188C09723F23D6914645B4DF2E', null, 'fff', '0', '0');
INSERT INTO `userinfo` VALUES ('33', 'ffff', 'ccccadaf', '1397030018', '1397029139', '8AB9FC244E14885784F4F5D6C0FC4315', null, 'fff', '0', '0');
INSERT INTO `userinfo` VALUES ('34', 'ffff', 'sdf546464', '1397030085', '1397030084', '56602A606D6CDE6CC4FDAE661EC24B17', null, 'fff', '0', '0');
INSERT INTO `userinfo` VALUES ('35', 'ffff', 'ff5ff', '1397030937', '1397030246', 'D94D43E6A62DC4111FBAFE86C974B8DE', null, 'ffff', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='竞技场相关表';

-- ----------------------------
-- Records of userjjc
-- ----------------------------
INSERT INTO `userjjc` VALUES ('1', '14', '15', '1068260', '100', '1', '0', '0', '1', '1200');
INSERT INTO `userjjc` VALUES ('2', '2', '15', '955', '5100', '2', '2', '5', '9', '0');
INSERT INTO `userjjc` VALUES ('3', '3', '15', '0', '0', '0', '0', '0', '0', '700');
INSERT INTO `userjjc` VALUES ('4', '4', '15', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('5', '5', '15', '0', '0', '0', '0', '0', '0', '600');
INSERT INTO `userjjc` VALUES ('6', '6', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('7', '7', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('8', '8', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('9', '23', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('10', '10', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('11', '20', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('12', '16', '15', '35', '700', '2', '3', '0', '5', '0');
INSERT INTO `userjjc` VALUES ('13', '13', '15', '10', '500', '1', '0', '0', '1', '0');
INSERT INTO `userjjc` VALUES ('14', '1', '15', '50', '16200', '2', '6', '0', '8', '0');
INSERT INTO `userjjc` VALUES ('15', '21', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('16', '12', '15', '40', '1900', '4', '0', '0', '4', '0');
INSERT INTO `userjjc` VALUES ('17', '17', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('18', '24', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('19', '19', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('20', '11', '15', '10', '49000', '1', '0', '0', '1', '0');
INSERT INTO `userjjc` VALUES ('21', '9', '15', '35', '4200', '3', '1', '0', '4', '0');
INSERT INTO `userjjc` VALUES ('22', '22', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('23', '15', '15', '10', '400', '1', '0', '0', '1', '0');
INSERT INTO `userjjc` VALUES ('24', '18', '15', '15', '2900', '1', '0', '1', '2', '0');
INSERT INTO `userjjc` VALUES ('25', '25', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('26', '26', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('27', '27', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('28', '28', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('29', '29', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('30', '30', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('31', '31', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('32', '32', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('33', '33', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('34', '34', '15', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userjjc` VALUES ('35', '35', '15', '0', '0', '0', '0', '0', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userprop
-- ----------------------------
INSERT INTO `userprop` VALUES ('1', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '2', '0', '0', '2');
INSERT INTO `userprop` VALUES ('2', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('3', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('4', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('5', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('6', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('7', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('8', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('9', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('10', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('11', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('12', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('13', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('14', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('15', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('16', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('17', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('18', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('19', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('20', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('21', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('22', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('23', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('24', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('25', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('26', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('27', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('28', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('29', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('30', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('31', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('32', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('33', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('34', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `userprop` VALUES ('35', '1', '0', '0', '0', '1', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertask
-- ----------------------------
INSERT INTO `usertask` VALUES ('1', '4', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('2', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('3', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('4', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('5', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('6', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('7', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('8', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('9', '2', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('10', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('11', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('12', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('13', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('14', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('15', '2', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('16', '2', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('17', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('18', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('19', '2', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('20', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('21', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('22', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('23', '2', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('24', '4', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('25', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('26', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('27', '2', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('28', '2', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('29', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('30', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('31', '4', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('32', '3', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('33', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('34', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `usertask` VALUES ('35', '2', '0', '0', '0', '0', '0', '0');

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
