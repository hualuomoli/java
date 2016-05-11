/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.182
Source Server Version : 50613
Source Host           : 192.168.1.182:3306
Source Database       : testme

Target Server Type    : MYSQL
Target Server Version : 50613
File Encoding         : 65001

Date: 2016-05-11 15:54:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for creator_demo
-- ----------------------------
DROP TABLE IF EXISTS `creator_demo`;
CREATE TABLE `creator_demo` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `sex` char(1) NOT NULL,
  `age` int(3) DEFAULT NULL,
  `salary` double(10,2) DEFAULT NULL,
  `birth_day` date DEFAULT NULL,
  `seconds` bigint(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试';
