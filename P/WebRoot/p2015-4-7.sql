/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : p

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2015-04-07 15:58:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_campus`
-- ----------------------------
DROP TABLE IF EXISTS `t_campus`;
CREATE TABLE `t_campus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campusCode` varchar(32) DEFAULT NULL,
  `campusName` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_campus
-- ----------------------------
INSERT INTO `t_campus` VALUES ('1', 'NEU', '东北大学');

-- ----------------------------
-- Table structure for `t_duration`
-- ----------------------------
DROP TABLE IF EXISTS `t_duration`;
CREATE TABLE `t_duration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `endDate` varchar(32) DEFAULT NULL,
  `startDate` varchar(32) DEFAULT NULL,
  `term_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iy8br1aykud09o5y55riltdav` (`term_id`),
  CONSTRAINT `FK_iy8br1aykud09o5y55riltdav` FOREIGN KEY (`term_id`) REFERENCES `t_term` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_duration
-- ----------------------------
INSERT INTO `t_duration` VALUES ('1', '2015-4-9', '2015-4-2', '1');
INSERT INTO `t_duration` VALUES ('2', '2015-5-1', '2015-4-3', '2');

-- ----------------------------
-- Table structure for `t_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupCode` varchar(32) DEFAULT NULL,
  `groupName` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_group
-- ----------------------------
INSERT INTO `t_group` VALUES ('1', 'ADMIN', '管理员');
INSERT INTO `t_group` VALUES ('2', 'PAY', '财务人员');

-- ----------------------------
-- Table structure for `t_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_level`;
CREATE TABLE `t_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `levelCode` varchar(32) DEFAULT NULL,
  `levelName` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_level
-- ----------------------------
INSERT INTO `t_level` VALUES ('1', 'ZSB', '专升本');

-- ----------------------------
-- Table structure for `t_major`
-- ----------------------------
DROP TABLE IF EXISTS `t_major`;
CREATE TABLE `t_major` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `majorCode` varchar(32) DEFAULT NULL,
  `majorName` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_major
-- ----------------------------
INSERT INTO `t_major` VALUES ('1', '081511', '计算机技术');

-- ----------------------------
-- Table structure for `t_percent`
-- ----------------------------
DROP TABLE IF EXISTS `t_percent`;
CREATE TABLE `t_percent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `maxNum` int(11) DEFAULT NULL,
  `minNum` int(11) DEFAULT NULL,
  `percent` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_percent
-- ----------------------------
INSERT INTO `t_percent` VALUES ('1', '30', '0', '0.38');
INSERT INTO `t_percent` VALUES ('2', '50', '31', '0.39');

-- ----------------------------
-- Table structure for `t_price`
-- ----------------------------
DROP TABLE IF EXISTS `t_price`;
CREATE TABLE `t_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `campus_id` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `major_id` int(11) DEFAULT NULL,
  `term_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8rx8xply3amr5v4qodseeb0nx` (`campus_id`),
  KEY `FK_sauutowwgo7ln45hhrdf9j0b9` (`level_id`),
  KEY `FK_4y9mgyn436cv7mo4ubauwxmt2` (`major_id`),
  KEY `FK_o9gf2i8vb7mcwl6engym5j6xg` (`term_id`),
  CONSTRAINT `FK_o9gf2i8vb7mcwl6engym5j6xg` FOREIGN KEY (`term_id`) REFERENCES `t_term` (`id`),
  CONSTRAINT `FK_4y9mgyn436cv7mo4ubauwxmt2` FOREIGN KEY (`major_id`) REFERENCES `t_major` (`id`),
  CONSTRAINT `FK_8rx8xply3amr5v4qodseeb0nx` FOREIGN KEY (`campus_id`) REFERENCES `t_campus` (`id`),
  CONSTRAINT `FK_sauutowwgo7ln45hhrdf9j0b9` FOREIGN KEY (`level_id`) REFERENCES `t_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_price
-- ----------------------------
INSERT INTO `t_price` VALUES ('1', '60', '1', '1', '1', '1');
INSERT INTO `t_price` VALUES ('2', '90', '1', '1', '1', '1');

-- ----------------------------
-- Table structure for `t_standard`
-- ----------------------------
DROP TABLE IF EXISTS `t_standard`;
CREATE TABLE `t_standard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `credit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_standard
-- ----------------------------
INSERT INTO `t_standard` VALUES ('1', '38');
INSERT INTO `t_standard` VALUES ('2', '42');

-- ----------------------------
-- Table structure for `t_term`
-- ----------------------------
DROP TABLE IF EXISTS `t_term`;
CREATE TABLE `t_term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `termCode` varchar(32) DEFAULT NULL,
  `termName` varchar(32) DEFAULT NULL,
  `standard_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kcu1y4di6l8kej8536sukctp8` (`standard_id`),
  CONSTRAINT `FK_kcu1y4di6l8kej8536sukctp8` FOREIGN KEY (`standard_id`) REFERENCES `t_standard` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_term
-- ----------------------------
INSERT INTO `t_term` VALUES ('1', '1503', '1503', '1');
INSERT INTO `t_term` VALUES ('2', '1509', '1509', '2');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `campus_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8mkimlylkbfkknu8e7fcmhi6t` (`campus_id`),
  KEY `FK_e5f24mh6aryt9hsy99oydps6g` (`group_id`),
  CONSTRAINT `FK_e5f24mh6aryt9hsy99oydps6g` FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`),
  CONSTRAINT `FK_8mkimlylkbfkknu8e7fcmhi6t` FOREIGN KEY (`campus_id`) REFERENCES `t_campus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '123456', 'admin', '1', '1');
