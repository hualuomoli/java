DROP TABLE IF EXISTS `t_demo`;
CREATE TABLE `t_demo` (
	`id` varchar(32) COMMENT '主键',
	`version` integer(11) COMMENT '数据版本号',
	`name` varchar(32),
	`sex` char(1),
	`salary` double(8,3) COMMENT '工资',
	`age` integer(3) DEFAULT 20 COMMENT '年龄',
	`birth_day` date COMMENT '生日',
	`remarks` longtext,
	`user_username` varchar(20),
	`region_id` varchar(32),
	`create_by` varchar(32) NOT NULL COMMENT '创建人',
	`create_date` timestamp NOT NULL COMMENT '创建时间',
	`update_by` varchar(32) NOT NULL COMMENT '修改人',
	`update_date` timestamp NOT NULL COMMENT '修改时间',
	`status` integer(11) NOT NULL COMMENT '数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试demo';

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
	`id` varchar(32),
	`version` integer(11) COMMENT '数据版本号',
	`username` varchar(20),
	`nickname` varchar(32),
	`number` integer(11),
	`create_by` varchar(32) NOT NULL COMMENT '创建人',
	`create_date` timestamp NOT NULL COMMENT '创建时间',
	`update_by` varchar(32) NOT NULL COMMENT '修改人',
	`update_date` timestamp NOT NULL COMMENT '修改时间',
	`status` integer(11) NOT NULL COMMENT '数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表';

DROP TABLE IF EXISTS `t_region`;
CREATE TABLE `t_region` (
	`id` varchar(32) COMMENT '主键',
	`version` integer(11) COMMENT '数据版本号',
	`code` varchar(32),
	`name` varchar(32),
	`type` integer(11),
	`create_by` varchar(32) NOT NULL COMMENT '创建人',
	`create_date` timestamp NOT NULL COMMENT '创建时间',
	`update_by` varchar(32) NOT NULL COMMENT '修改人',
	`update_date` timestamp NOT NULL COMMENT '修改时间',
	`status` integer(11) NOT NULL COMMENT '数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表';

