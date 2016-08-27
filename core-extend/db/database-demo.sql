DROP TABLE IF EXISTS `test_product`;
CREATE TABLE `test_product` (
	`id` varchar(32) COMMENT '主键',
	`name` varchar(32),
	`description` varchar(32),
	`price` integer(11) COMMENT '价格',
	`price_str` varchar(32) COMMENT '价格字符串',
	`amount` integer(11) COMMENT '总金额',
	`create_by` varchar(32) NOT NULL COMMENT '创建人',
	`create_date` timestamp NOT NULL COMMENT '创建时间',
	`update_by` varchar(32) NOT NULL COMMENT '修改人',
	`update_date` timestamp NOT NULL COMMENT '修改时间',
	`status` integer(11) NOT NULL COMMENT '数据状态',
	`status_name` varchar(32) NOT NULL COMMENT '数据状态名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品';

