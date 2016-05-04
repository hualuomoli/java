DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo` (
	`id` varchar(32) NOT NULL COMMENT '主键',
	`version` int(11) NOT NULL DEFAULT 1 COMMENT '数据版本',
	`name` varchar(32) NOT NULL COMMENT '名称',
	`age` int(3) COMMENT '年龄',
	`seconds` bigint(17) COMMENT '测试字段',
	`salary` decimal(7,3) COMMENT '工资',
	`sex` varchar(32) NOT NULL DEFAULT '1' COMMENT '性别',
	`birth_day` TIMESTAMP COMMENT '生日',
	`create_by` varchar(12) NOT NULL COMMENT '创建人',
	`create_date` TIMESTAMP NOT NULL COMMENT '创建时间',
	`update_by` varchar(12) NOT NULL COMMENT '修改人',
	`update_date` TIMESTAMP NOT NULL COMMENT '修改时间',
	`status` int(1) NOT NULL DEFAULT 1 COMMENT '数据状态 0无效,1有效,2删除',
	`remark` varchar(2000) COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='例子';
