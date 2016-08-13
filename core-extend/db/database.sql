DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
	`id` varchar(32) COMMENT '主键',
	`menu_code` varchar(32),
	`menu_name` varchar(32),
	`parent_code` varchar(32),
	`full_name` varchar(32),
	`menu_type` integer(11) COMMENT '菜单类型 1=菜单,2=权限',
	`icon` varchar(200) COMMENT '图标',
	`router_state` varchar(100) COMMENT '菜单路由状态',
	`permission` varchar(32) COMMENT '权限字符串',
	`menu_sort` integer(11),
	`menu_level` integer(11),
	`create_by` varchar(32) NOT NULL COMMENT '创建人',
	`create_date` timestamp NOT NULL COMMENT '创建时间',
	`update_by` varchar(32) NOT NULL COMMENT '修改人',
	`update_date` timestamp NOT NULL COMMENT '修改时间',
	`status` integer(11) NOT NULL COMMENT '数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
	`id` varchar(32) COMMENT '主键',
	`role_code` varchar(32) COMMENT '角色编码',
	`role_name` varchar(100) COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
	`id` varchar(32) COMMENT '主键',
	`username` varchar(32) COMMENT '用户名',
	`role_code` varchar(32) COMMENT '角色编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色的关系';

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
	`id` varchar(32) COMMENT '主键',
	`role_code` varchar(32) COMMENT '角色编码',
	`menu_code` varchar(32) COMMENT '菜单编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色具有的菜单和权限';

DROP TABLE IF EXISTS `sys_upload_file`;
CREATE TABLE `sys_upload_file` (
	`id` varchar(32) COMMENT '主键',
	`fieldname` varchar(100) COMMENT '上传表单名',
	`original_filename` varchar(100) COMMENT '文件原始名称',
	`size` integer(18) COMMENT '文件大小',
	`mimetype` varchar(32) COMMENT '文件协议',
	`file_md5` varchar(200) COMMENT '文件MD5',
	`file_fullname` varchar(100) COMMENT '文件名(含后缀)',
	`filename` varchar(100) COMMENT '文件名(不含后缀)',
	`file_suffix` varchar(20) COMMENT '文件扩展名',
	`file_absolutepath` varchar(200) COMMENT '文件绝对路径',
	`url` varchar(200) COMMENT '文件访问URL',
	`server_url` varchar(100) COMMENT '服务器URL',
	`relative_url` varchar(100) COMMENT '相对服务器的URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

