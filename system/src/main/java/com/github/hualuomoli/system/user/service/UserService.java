package com.github.hualuomoli.system.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.extend.base.entity.BaseMenu;
import com.github.hualuomoli.extend.base.entity.BaseRole;
import com.github.hualuomoli.extend.base.entity.BaseRoleMenu;
import com.github.hualuomoli.extend.base.entity.BaseUserRole;
import com.github.hualuomoli.extend.base.service.BaseMenuService;
import com.github.hualuomoli.extend.base.service.BaseRoleMenuService;
import com.github.hualuomoli.extend.base.service.BaseRoleService;
import com.github.hualuomoli.extend.base.service.BaseUserRoleService;
import com.github.hualuomoli.login.service.LoginUserService;
import com.google.common.collect.Lists;

@Service(value = "com.github.hualuomoli.system.user.service.UserService")
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private BaseUserRoleService baseUserRoleService;
	@Autowired
	private BaseRoleService baseRoleService;
	@Autowired
	private BaseRoleMenuService baseRoleMenuService;
	@Autowired
	private BaseMenuService baseMenuService;

	/**
	 * 查询用户的角色
	 */
	@Transactional(readOnly = false)
	public List<BaseRole> getRole() {
		String username = loginUserService.getUsername();
		BaseUserRole baseUserRole = new BaseUserRole();
		baseUserRole.setUsername(username);

		List<BaseUserRole> lis = baseUserRoleService.findList(baseUserRole);

		List<String> codeList = Lists.newArrayList();
		for (BaseUserRole bur : lis) {
			codeList.add(bur.getRoleCode());
		}

		BaseRole baseRole = new BaseRole();
		baseRole.setRoleCodeArray(codeList.toArray(new String[] {}));

		return baseRoleService.findList(baseRole);

	}

	/**
	 * 用户用户的菜单
	 */
	@Transactional(readOnly = false)
	public List<BaseMenu> getMenu() {
		String username = loginUserService.getUsername();

		BaseUserRole baseUserRole = new BaseUserRole();
		baseUserRole.setUsername(username);

		// user - role
		List<BaseUserRole> userRoleList = baseUserRoleService.findList(baseUserRole);
		if (userRoleList == null || userRoleList.size() == 0) {
			return Lists.newArrayList();
		}

		List<String> codeList = Lists.newArrayList();
		for (BaseUserRole bur : userRoleList) {
			codeList.add(bur.getRoleCode());
		}

		// role - menu
		BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
		baseRoleMenu.setRoleCodeArray(codeList.toArray(new String[] {}));
		List<BaseRoleMenu> roleMenuList = baseRoleMenuService.findList(baseRoleMenu);
		codeList = Lists.newArrayList();
		for (BaseRoleMenu brm : roleMenuList) {
			codeList.add(brm.getMenuCode());
		}

		// menu
		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setMenuCodeArray(codeList.toArray(new String[] {}));
		return baseMenuService.findList(baseMenu);
	}

}
