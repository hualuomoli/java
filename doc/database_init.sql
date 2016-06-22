DROP TABLE IF EXISTS `t_demo`;
CREATE TABLE `t_demo` (
	`id` varchar(32) COMMENT 'ID',
	`name` varchar(32),
	`sex` varchar(1),
	`salary` double(8,3) COMMENT '工资',
	`age` integer(3),
	`birth_day` date COMMENT '工资',
	`remarks` longtext,
	`user_id` varchar(32) COMMENT '用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试demo';
