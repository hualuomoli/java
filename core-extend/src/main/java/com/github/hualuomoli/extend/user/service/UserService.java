package com.github.hualuomoli.extend.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.extend.base.entity.BaseMenu;
import com.github.hualuomoli.extend.base.entity.BaseRoleMenu;
import com.github.hualuomoli.extend.base.entity.BaseUserRole;
import com.github.hualuomoli.extend.base.service.BaseMenuService;
import com.github.hualuomoli.extend.base.service.BaseRoleMenuService;
import com.github.hualuomoli.extend.base.service.BaseUserRoleService;
import com.github.hualuomoli.extend.constant.DataType;
import com.google.common.collect.Sets;

@Service(value = "com.github.hualuomoli.extend.user.service.UserService")
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private BaseUserRoleService baseUserRoleService;
	@Autowired
	private BaseRoleMenuService baseRoleMenuService;
	@Autowired
	private BaseMenuService baseMenuService;

	// 查询用户具有的角色
	public HashSet<String> getUserRoles(String username) {

		HashSet<String> roles = Sets.newHashSet();

		// query
		BaseUserRole baseUserRole = new BaseUserRole();
		baseUserRole.setUsername(username);

		List<BaseUserRole> list = baseUserRoleService.findList(baseUserRole);

		for (BaseUserRole bur : list) {
			roles.add(bur.getRoleCode());
		}

		return roles;
	}

	// 查询用户具有的权限
	public HashSet<String> getUserPermission(String username) {

		HashSet<String> permission = Sets.newHashSet();

		// 用户的角色
		BaseUserRole baseUserRole = new BaseUserRole();
		baseUserRole.setUsername(username);
		List<BaseUserRole> baseUserRoles = baseUserRoleService.findList(baseUserRole);
		// 没有角色
		if (baseUserRoles == null || baseUserRoles.size() == 0) {
			return permission;
		}

		// 角色菜单
		Set<String> roleCodes = Sets.newHashSet();
		for (BaseUserRole bur : baseUserRoles) {
			roleCodes.add(bur.getRoleCode());
		}
		BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
		baseRoleMenu.setRoleCodeArray(roleCodes.toArray(new String[] {}));
		List<BaseRoleMenu> baseRoleMenus = baseRoleMenuService.findList(baseRoleMenu);
		if (baseRoleMenus == null || baseRoleMenus.size() == 0) {
			return permission;
		}

		// 菜单
		Set<String> menuCodes = Sets.newHashSet();
		for (BaseRoleMenu brm : baseRoleMenus) {
			menuCodes.add(brm.getMenuCode());
		}

		// 查询菜单
		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setMenuCodeArray(menuCodes.toArray(new String[] {}));
		baseMenu.setMenuType(DataType.MENU_TYPE_PERMISSION.getValue());
		List<BaseMenu> baseMenus = baseMenuService.findList(baseMenu);

		// add
		for (BaseMenu bm : baseMenus) {
			permission.add(bm.getPermission());
		}

		return permission;
	}

}
