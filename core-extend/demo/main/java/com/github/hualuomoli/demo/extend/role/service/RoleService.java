package com.github.hualuomoli.demo.extend.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.demo.extend.base.entity.BaseRole;
import com.github.hualuomoli.demo.extend.base.service.BaseRoleService;
import com.github.hualuomoli.extend.annotation.persistent.BasicDataNotice;

@Service(value = "com.github.hualuomoli.demo.extend.role.service.RoleService")
@Transactional(readOnly = true)
public class RoleService {

	@Autowired
	BaseRoleService baseRoleService;

	@Transactional(readOnly = false)
	public void update(@BasicDataNotice BaseRole baseRole) {
		baseRoleService.update(baseRole);
	}

}
