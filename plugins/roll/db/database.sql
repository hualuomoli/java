DROP TABLE IF EXISTS `pr_poll_data`;
CREATE TABLE `pr_poll_data` (
	`id` varchar(32) COMMENT '主键',
	`data_id` varchar(32) COMMENT '数据ID',
	`data_type` integer(11) COMMENT '数据类型 1=字符串,2=字节,3=序列化数据',
	`string_data` varchar(32) COMMENT '字符串数据',
	`byte_data` blob COMMENT '字节数据',
	`dealer_class_name` varchar(200) NOT NULL COMMENT '处理者类名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮询数据';

DROP TABLE IF EXISTS `pr_poll_frequency`;
CREATE TABLE `pr_poll_frequency` (
	`id` varchar(32) COMMENT '主键',
	`data_id` varchar(32) COMMENT '数据',
	`frequency` varchar(200) NOT NULL COMMENT '调度频度 ,使用逗号(,)分割(s=秒,m=分钟,h=小时,d=天)',
	`remain_frequency` varchar(200) NOT NULL COMMENT '剩余调度频度',
	`execute_time` timestamp NOT NULL COMMENT '下次执行时间',
	`priority` integer(11) COMMENT '优先级,值越大越优先',
	`lock_string` varchar(32) COMMENT '锁定数据字符串,用于锁定后获取锁定的数据,未锁定使用UNLOCK',
	`un_lock_time` timestamp COMMENT '自动解锁时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮询频度';

DROP TABLE IF EXISTS `pr_poll_success_his`;
CREATE TABLE `pr_poll_success_his` (
	`id` varchar(32) COMMENT '主键',
	`data_id` varchar(32) COMMENT '数据ID',
	`data_type` integer(11) COMMENT '数据类型 1=字符串,2=字节,3=序列化数据',
	`string_data` varchar(32) COMMENT '字符串数据',
	`byte_data` blob COMMENT '字节数据',
	`dealer_class_name` varchar(200) NOT NULL COMMENT '处理者类名',
	`frequency` varchar(200) NOT NULL COMMENT '调度频度 ,使用逗号(,)分割(s=秒,m=分钟,h=小时,d=天)',
	`priority` integer(11) COMMENT '优先级,值越大越优先',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮询成功历史';

DROP TABLE IF EXISTS `pr_poll_error_his`;
CREATE TABLE `pr_poll_error_his` (
	`id` varchar(32) COMMENT '主键',
	`data_id` varchar(32) COMMENT '数据ID',
	`data_type` integer(11) COMMENT '数据类型 1=字符串,2=字节,3=序列化数据',
	`string_data` varchar(32) COMMENT '字符串数据',
	`byte_data` blob COMMENT '字节数据',
	`dealer_class_name` varchar(200) NOT NULL COMMENT '处理者类名',
	`frequency` varchar(200) NOT NULL COMMENT '调度频度 ,使用逗号(,)分割(s=秒,m=分钟,h=小时,d=天)',
	`priority` integer(11) COMMENT '优先级,值越大越优先',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮询失败历史';

