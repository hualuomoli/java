package com.github.hualuomoli.demo.extend.role.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.base.service.BaseRoleService;
import com.github.hualuomoli.extend.notice.service.NoticeService;
import com.github.hualuomoli.test.AbstractContextServiceTest;

public class RoleServiceTest extends AbstractContextServiceTest {

	@Autowired
	private BaseRoleService baseRoleService;
	@Autowired
	private NoticeService noticeService;

	@Test
	public void testUpdate() {
		BaseRole baseRole = new BaseRole();
		baseRole.setId("1");
		baseRole.setRoleCode("01");
		baseRole.setRoleName("管理员");
		baseRoleService.update(baseRole);

		noticeService.notice(baseRole);

	}

}
