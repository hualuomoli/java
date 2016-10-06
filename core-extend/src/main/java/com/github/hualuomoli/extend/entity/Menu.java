package com.github.hualuomoli.extend.entity;

import com.github.hualuomoli.base.annotation.entity.EntityColumn;
import com.github.hualuomoli.base.annotation.entity.EntityColumnQuery;
import com.github.hualuomoli.base.annotation.entity.EntityTable;

/**
 * 菜单
 * @author hualuomoli
 *
 */
@SuppressWarnings("serial")
@EntityTable(comment = "菜单", unique = { "menuCode" })
public class Menu extends TreeField {

	@EntityColumnQuery(inArray = true)
	private String menuCode;
	private String menuName;
	@EntityColumn(comment = "菜单类型 1=菜单,2=权限")
	private Integer menuType;
	@EntityColumn(comment = "图标", length = 200)
	private String icon;
	@EntityColumn(comment = "菜单路由状态", length = 100)
	private String routerState;
	@EntityColumn(comment = "权限字符串", length = 32)
	private String permission; // 权限字符串

	public Menu() {
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRouterState() {
		return routerState;
	}

	public void setRouterState(String routerState) {
		this.routerState = routerState;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
