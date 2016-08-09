DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
	`id` varchar(32) COMMENT '主键',
	`role_code` varchar(32) COMMENT '角色编码',
	`role_name` varchar(100) COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

