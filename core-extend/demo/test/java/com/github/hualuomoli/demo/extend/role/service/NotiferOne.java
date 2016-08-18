package com.github.hualuomoli.demo.extend.role.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.notice.Notifer;

@Service(value = "com.github.hualuomoli.demo.extend.role.service.NotiferOne")
public class NotiferOne implements Notifer<BaseRole> {

	private static final Logger logger = LoggerFactory.getLogger(NotiferTwo.class);

	@Override
	public boolean support(Class<?> cls) {
		return cls == BaseRole.class;
	}

	@Override
	public void notice(BaseRole baseRole) {
		logger.debug("role notice {} {}", baseRole.getRoleCode(), baseRole.getRoleName());
	}

}
