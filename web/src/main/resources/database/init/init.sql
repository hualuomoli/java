CREATE TABLE `demo` (
  `id` varchar(64) NOT NULL,
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `status` varchar(3) NOT NULL DEFAULT '1',
  `remark` varchar(2000) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `address` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='demo';

-- ----------------------------
-- Records of demo
-- ----------------------------
INSERT INTO `demo` VALUES ('1', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('10', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('11', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('12', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('13', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('14', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('2', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('3', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('4', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('5', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('6', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('7', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('8', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
INSERT INTO `demo` VALUES ('9', 'sys', '2016-02-26 15:19:12', 'system', '2016-02-26 17:46:54', '1', '修改备注', 'name', 'M', '28', '山东省青岛市市北区合肥路666号');
