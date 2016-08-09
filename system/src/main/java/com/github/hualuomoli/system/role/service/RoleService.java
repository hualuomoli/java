package com.github.hualuomoli.system.role.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.exception.CodeException;
import com.github.hualuomoli.system.base.entity.BaseMenu;
import com.github.hualuomoli.system.base.entity.BaseRole;
import com.github.hualuomoli.system.base.entity.BaseRoleMenu;
import com.github.hualuomoli.system.base.service.BaseMenuService;
import com.github.hualuomoli.system.base.service.BaseRoleMenuService;
import com.github.hualuomoli.system.base.service.BaseRoleService;
import com.github.hualuomoli.system.constant.Code;
import com.github.hualuomoli.system.role.web.RoleController.DeleteEntity;
import com.github.hualuomoli.system.role.web.RoleController.GetMenusEntity;
import com.github.hualuomoli.system.role.web.RoleController.PostEntity;
import com.github.hualuomoli.system.role.web.RoleController.PutEntity;
import com.github.hualuomoli.system.role.web.RoleController.PutMenusEntity;
import com.google.common.collect.Lists;

@Service(value = "com.github.hualuomoli.system.role.service.RoleService")
@Transactional(readOnly = true)
public class RoleService {

	@Autowired
	private BaseRoleService baseRoleService;
	@Autowired
	private BaseMenuService baseMenuService;
	@Autowired
	private BaseRoleMenuService baseRoleMenuService;

	/**
	 * 查询角色
	 */
	public List<BaseRole> get() {

		BaseRole baseRole = new BaseRole();

		return baseRoleService.findList(baseRole);
	}

	/**
	 * 添加角色
	 */
	@Transactional(readOnly = false)
	public void post(PostEntity postEntity) {

		BaseRole baseRole = baseRoleService.getUnique(postEntity.getRoleCode());
		if (baseRole != null) {
			throw new CodeException(Code.ROLE_ALREADY_EXISTS);
		}

		baseRole = new BaseRole();
		baseRole.setRoleCode(postEntity.getRoleCode());
		baseRole.setRoleName(postEntity.getRoleName());

		baseRoleService.insert(baseRole);

	}

	/**
	 * 修改角色
	 */
	@Transactional(readOnly = false)
	public void put(PutEntity putEntity) {
		BaseRole baseRole = new BaseRole();
		baseRole.setId(putEntity.getId());
		baseRole.setRoleName(putEntity.getRoleName());

		baseRoleService.update(baseRole);
	}

	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	public void delete(DeleteEntity deleteEntity) {
		BaseRole baseRole = baseRoleService.get(deleteEntity.getId());
		baseRoleService.delete(deleteEntity.getId());
		// 删除角色的菜单
		BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
		baseRoleMenu.setRoleCode(baseRole.getRoleCode());
		List<BaseRoleMenu> list = baseRoleMenuService.findList(baseRoleMenu);
		List<String> ids = Lists.newArrayList();
		for (BaseRoleMenu brm : list) {
			ids.add(brm.getId());
		}
		baseRoleMenuService.deleteByIds(ids);
	}

	/**
	 * 查询角色下的菜单
	 */
	public List<BaseMenu> getMenus(GetMenusEntity getMenusEntity) {

		BaseRole baseRole = baseRoleService.get(getMenusEntity.getId());
		if (baseRole == null) {
			throw new CodeException(Code.ROLE_NOT_FOUND);
		}

		BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
		baseRoleMenu.setRoleCode(baseRole.getRoleCode());

		List<BaseRoleMenu> list = baseRoleMenuService.findList(baseRoleMenu);
		if (list == null || list.size() == 0) {
			return Lists.newArrayList();
		}

		List<String> codeList = Lists.newArrayList();
		for (BaseRoleMenu brm : list) {
			codeList.add(brm.getMenuCode());
		}

		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setMenuCodeArray(codeList.toArray(new String[] {}));
		return baseMenuService.findList(baseMenu);
	}

	/**
	 * 设置角色的菜单
	 */
	@Transactional(readOnly = false)
	public void putMenus(PutMenusEntity putMenusEntity) {
		BaseRole baseRole = baseRoleService.get(putMenusEntity.getId());
		if (baseRole == null) {
			throw new CodeException(Code.ROLE_NOT_FOUND);
		}

		BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
		baseRoleMenu.setRoleCode(baseRole.getRoleCode());

		List<BaseRoleMenu> list = baseRoleMenuService.findList(baseRoleMenu);
		// 删除原来的
		if (list != null && list.size() > 0) {
			List<String> ids = Lists.newArrayList();
			for (BaseRoleMenu brm : list) {
				ids.add(brm.getId());
			}
			baseRoleMenuService.deleteByIds(ids);
		}

		if (StringUtils.isBlank(putMenusEntity.getMenus())) {
			return;
		}

		// 添加新的
		List<BaseRoleMenu> roleMenus = Lists.newArrayList();
		String[] menus = putMenusEntity.getMenus().split("[,]");
		for (String menuCode : menus) {
			BaseRoleMenu brm = new BaseRoleMenu();
			brm.setMenuCode(menuCode);
			brm.setRoleCode(baseRoleMenu.getRoleCode());
		}

		baseRoleMenuService.batchInsert(roleMenus);

	}

}
