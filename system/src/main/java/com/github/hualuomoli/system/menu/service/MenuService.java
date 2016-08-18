package com.github.hualuomoli.system.menu.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.base.constant.Status;
import com.github.hualuomoli.extend.base.entity.BaseMenu;
import com.github.hualuomoli.extend.base.service.BaseMenuService;
import com.github.hualuomoli.extend.tree.service.TreeService;
import com.github.hualuomoli.system.constant.DataType;
import com.github.hualuomoli.system.menu.web.MenuController.DeleteEntity;
import com.github.hualuomoli.system.menu.web.MenuController.GetChildrenByParentCodeEntity;
import com.github.hualuomoli.system.menu.web.MenuController.PostEntity;
import com.github.hualuomoli.system.menu.web.MenuController.PutByIdEntity;
import com.github.hualuomoli.system.menu.web.MenuController.PutByIdParentEntity;
import com.github.hualuomoli.system.menu.web.MenuController.PutSortEntity;

@Service(value = "com.github.hualuomoli.system.menu.service.MenuService")
@Transactional(readOnly = true)
public class MenuService {

	private static final Object OBJECT = new Object();

	@Autowired
	private BaseMenuService baseMenuService;
	@Autowired
	private TreeService treeService;

	/**
	 * 查询所有有效的菜单
	 */
	public List<BaseMenu> getAll() {

		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setStatus(Status.NOMAL.getValue());
		baseMenu.setMenuType(DataType.MENU_TYPE_MENU.getValue());

		return baseMenuService.findList(baseMenu);

	}

	/**
	 * 查询子菜单/权限
	 */
	public List<BaseMenu> getChildren(GetChildrenByParentCodeEntity getChildrenByParentCodeEntity) {
		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setStatus(Status.NOMAL.getValue());

		if (StringUtils.isBlank(getChildrenByParentCodeEntity.getParentCode())) {
			// 顶级菜单
			baseMenu.setParentCode(DataType.MENU_ROOT_VALUE.getName());
		} else {
			// 其他
			baseMenu.setParentCode(getChildrenByParentCodeEntity.getParentCode());
		}

		return baseMenuService.findList(baseMenu);
	}

	/**
	 * 新增菜单或权限
	 */
	@Transactional(readOnly = false)
	public void post(PostEntity postEntity) {

		synchronized (OBJECT) {

			String parentCode = postEntity.getParentCode();

			if (StringUtils.isBlank(parentCode)) {
				parentCode = DataType.MENU_ROOT_VALUE.getName();
			}

			// 添加
			BaseMenu baseMenu = new BaseMenu();
			baseMenu.setMenuName(postEntity.getMenuName());
			baseMenu.setMenuType(postEntity.getMenuType());
			baseMenu.setIcon(postEntity.getIcon());
			baseMenu.setRouterState(postEntity.getRouterState());
			baseMenu.setPermission(postEntity.getPermission());
			baseMenu.setParentCode(StringUtils.isBlank(postEntity.getParentCode()) ? DataType.MENU_ROOT_VALUE.getName() : postEntity.getParentCode());
			treeService.addTreeMessage(baseMenu, parentCode, baseMenuService);

			baseMenuService.insert(baseMenu);
		}

	}

	/**
	 * 修改基本信息
	 */
	@Transactional(readOnly = false)
	public void putById(PutByIdEntity putByIdEntity) {
		synchronized (OBJECT) {
			// 添加
			BaseMenu baseMenu = new BaseMenu();
			baseMenu.setId(putByIdEntity.getId());
			baseMenu.setMenuName(putByIdEntity.getMenuName());
			baseMenu.setIcon(putByIdEntity.getIcon());
			baseMenu.setRouterState(putByIdEntity.getRouterState());
			baseMenu.setPermission(putByIdEntity.getPermission());

			baseMenuService.insert(baseMenu);
		}
	}

	/**
	 * 修改父节点
	 */
	@Transactional(readOnly = false)
	public void putByIdParent(PutByIdParentEntity putByIdParentEntity) {
		BaseMenu baseMenu = new BaseMenu();
		baseMenu.setId(putByIdParentEntity.getId());
		treeService.updateParent(baseMenu, putByIdParentEntity.getParentCode(), baseMenuService);
	}

	/**
	 * 修改排序
	 */
	@Transactional(readOnly = false)
	public void putSort(PutSortEntity putSortEntity) {
		synchronized (OBJECT) {
			treeService.updateSort(BaseMenu.class, putSortEntity.getSrcId(), putSortEntity.getDestId(), baseMenuService);
		}

	}

	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	public void delete(DeleteEntity deleteEntity) {
		baseMenuService.delete(deleteEntity.getId());
	}

}
