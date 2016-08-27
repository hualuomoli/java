DROP TABLE IF EXISTS `test_demo`;
CREATE TABLE `test_demo` (
	`id` varchar(32) COMMENT '主键',
	`name` varchar(32),
	`sex` char(1) COMMENT '性别',
	`salary` double(8,3) COMMENT '工资',
	`age` integer(11) COMMENT '年龄',
	`birth_day` date COMMENT '生日',
	`remarks` longtext COMMENT '备注',
	`user_username` varchar(20),
	`region_id` varchar(32),
	`create_by` varchar(32) NOT NULL COMMENT '创建人',
	`create_date` timestamp NOT NULL COMMENT '创建时间',
	`update_by` varchar(32) NOT NULL COMMENT '修改人',
	`update_date` timestamp NOT NULL COMMENT '修改时间',
	`status` integer(11) NOT NULL COMMENT '数据状态',
	`status_name` varchar(32) NOT NULL COMMENT '数据状态名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试demo';

DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user` (
	`id` varchar(32),
	`username` varchar(32) COMMENT '用户名',
	`nickname` varchar(32),
	`number` integer(11),
	`create_by` varchar(32) NOT NULL COMMENT '创建人',
	`create_date` timestamp NOT NULL COMMENT '创建时间',
	`update_by` varchar(32) NOT NULL COMMENT '修改人',
	`update_date` timestamp NOT NULL COMMENT '修改时间',
	`status` integer(11) NOT NULL COMMENT '数据状态',
	`status_name` varchar(32) NOT NULL COMMENT '数据状态名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

DROP TABLE IF EXISTS `test_region`;
CREATE TABLE `test_region` (
	`id` varchar(32) COMMENT '主键',
	`code` varchar(32),
	`name` varchar(32),
	`type` integer(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区';

